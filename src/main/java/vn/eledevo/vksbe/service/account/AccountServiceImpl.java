package vn.eledevo.vksbe.service.account;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.eledevo.vksbe.constant.DepartmentCode;
import vn.eledevo.vksbe.constant.ErrorCodes.AccountErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.constant.ResponseMessage;
import vn.eledevo.vksbe.constant.Role;
import vn.eledevo.vksbe.constant.Status;
import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.model.account.AccountQueryToFilter;
import vn.eledevo.vksbe.dto.model.account.UserInfo;
import vn.eledevo.vksbe.dto.request.AccountActive;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.request.PinChangeRequest;
import vn.eledevo.vksbe.dto.request.account.AccountCreateRequest;
import vn.eledevo.vksbe.dto.request.account.AccountUpdateRequest;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.ResultList;
import vn.eledevo.vksbe.dto.response.ResultUrl;
import vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter;
import vn.eledevo.vksbe.dto.response.account.AccountSwapResponse;
import vn.eledevo.vksbe.dto.response.account.ActivedAccountResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.dto.response.computer.ConnectComputerResponse;
import vn.eledevo.vksbe.dto.response.usb.UsbConnectedResponse;
import vn.eledevo.vksbe.entity.*;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.mapper.AccountMapper;
import vn.eledevo.vksbe.repository.*;
import vn.eledevo.vksbe.utils.Const;
import vn.eledevo.vksbe.utils.SecurityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static vn.eledevo.vksbe.constant.ErrorCode.*;
import static vn.eledevo.vksbe.constant.ErrorCodes.AccountErrorCode.ACCOUNT_NOT_LINKED_TO_USB;
import static vn.eledevo.vksbe.constant.FileConst.*;
import static vn.eledevo.vksbe.constant.ResponseMessage.*;
import static vn.eledevo.vksbe.constant.RoleCodes.*;
import static vn.eledevo.vksbe.utils.FileUtils.*;
import static vn.eledevo.vksbe.utils.SecurityUtils.getUserName;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    TokenRepository tokenRepository;
    AccountMapper accountMapper;
    PasswordEncoder passwordEncoder;
    ComputerRepository computerRepository;
    UsbRepository usbRepository;
    RoleRepository roleRepository;
    DepartmentRepository departmentRepository;
    OrganizationRepository organizationRepository;
    ProfileRepository profileRepository;

    @Value("${file.upload-dir}")
    @NonFinal
    String uploadDir;

    @Value("${app.host}")
    @NonFinal
    private String appHost;

    private Accounts validAccount(Long id) throws ApiException {
        return accountRepository.findById(id).orElseThrow(() -> new ApiException(AccountErrorCode.ACCOUNT_NOT_FOUND));
    }

    /**
     * Đặt lại mật khẩu cho tài khoản được chỉ định.
     * <p>
     * Chức năng:
     * - Đặt mật khẩu mới giống với tên đăng nhập (username)
     * - Xóa mã PIN
     * - Đặt lại các điều kiện đăng nhập (isConditionLogin1 và isConditionLogin2) về false
     * - Cập nhật thời gian và người thực hiện thay đổi
     * - Xóa tất cả token liên quan đến tài khoản
     *
     * @param id Id của tài khoản cần đặt lại mật khẩu
     * @return AccountResponse chứa thông tin tài khoản sau khi cập nhật
     * @throws ApiException nếu có lỗi xảy ra trong quá trình xử lý
     */
    @Override
    @Transactional
    public HashMap<String, String> resetPassword(Long id) throws ApiException {
        Accounts accounts = validAccount(id);
        accounts.setPassword(passwordEncoder.encode(accounts.getUsername()));
        accounts.setPin(null);
        accounts.setIsConditionLogin1(false);
        accounts.setIsConditionLogin2(false);
        accounts.setUpdatedBy(getUserName());
        accounts.setUpdatedAt(LocalDateTime.now());
        accounts.setStatus(String.valueOf(Status.INACTIVE));
        accountRepository.save(accounts);
        tokenRepository.deleteByAccounts_Id(id);
        return new HashMap<>();
    }

    private Boolean isBoss(Accounts accSecurity) {
        return switch (accSecurity.getRoles().getCode()) {
            case IT_ADMIN, VIEN_TRUONG, VIEN_PHO -> true;
            default -> false;
        };
    }

    @Override
    @Transactional
    public ResponseFilter<AccountResponseByFilter> getListAccountByFilter(
            AccountRequest accountRequest, Integer currentPage, Integer limit) throws ApiException {
        String loginAccountName = getUserName();
        Accounts accSecurity = accountRepository.findAccountsByUsername(loginAccountName);
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (limit < 1) {
            limit = 10;
        }
        if (accountRequest.getFromDate() == null) {
            accountRequest.setFromDate(LocalDate.of(1900, 1, 1));
        }
        if (accountRequest.getToDate() == null) {
            accountRequest.setToDate(LocalDate.now());
        }
        if (accountRequest.getFromDate().isAfter(accountRequest.getToDate())) {
            throw new ApiException(CHECK_FROM_DATE);
        }
        if (Boolean.FALSE.equals(isBoss(accSecurity))) {
            accountRequest.setDepartmentId(accSecurity.getDepartments().getId());
        }
        Pageable pageable = PageRequest.of(currentPage - 1, limit);
        Page<AccountQueryToFilter> page =
                accountRepository.getAccountList(accountRequest, isBoss(accSecurity), pageable);
        Page<AccountResponseByFilter> filters = page.map(account -> {
            checkRoleToShowLockOrUnlockButton(account, accSecurity);
            return AccountResponseByFilter.builder()
                    .id(account.getId())
                    .username(account.getUsername())
                    .fullName(account.getFullName())
                    .roleName(account.getRoleName())
                    .departmentName(account.getDepartmentName())
                    .organizationName(account.getOrganizationName())
                    .status(account.getStatus())
                    .createdAt(account.getCreatedAt())
                    .updatedAt(account.getUpdatedAt())
                    .isShowUnlockButton(account.getIsShowUnlockButton())
                    .isEnabledUnlockButton(account.getIsEnabledUnlockButton())
                    .isShowLockButton(account.getIsShowLockButton())
                    .isEnabledLockButton(account.getIsEnabledLockButton())
                    .build();
        });
        return new ResponseFilter<>(
                filters.getContent(),
                (int) filters.getTotalElements(),
                filters.getSize(),
                filters.getNumber(),
                filters.getTotalPages());
    }

    private void checkRoleToShowLockOrUnlockButton(AccountQueryToFilter account, Accounts accSecurity) {
        Role viewedAccRole = Role.valueOf(account.getRoleCode());
        Role loginAccRole = Role.valueOf(accSecurity.getRoles().getCode());
        if (account.getStatus().equals(Status.ACTIVE.name())
                && priorityRoles(loginAccRole) > priorityRoles(viewedAccRole)) {
            account.setIsShowLockButton(true);
            account.setIsEnabledLockButton(true);
        }
        if (Boolean.TRUE.equals((account.getStatus().equals(Status.INACTIVE.name())
                                || account.getStatus().equals(Status.INITIAL.name()))
                        && priorityRoles(loginAccRole) > priorityRoles(viewedAccRole)
                        && account.getIsConnectComputer())
                && Boolean.TRUE.equals(account.getIsConnectUsb())) {
            account.setIsShowUnlockButton(true);
            account.setIsEnabledUnlockButton(true);
        }
        if ((loginAccRole.equals(Role.TRUONG_PHONG) || loginAccRole.equals(Role.PHO_PHONG))
                && accSecurity.getDepartments().getId().equals(account.getDepartmentId())
                && account.getStatus().equals(Status.ACTIVE.name())
                && priorityRoles(loginAccRole) > priorityRoles(viewedAccRole)) {
            account.setIsShowLockButton(true);
            account.setIsEnabledLockButton(true);
        }

        if ((loginAccRole.equals(Role.TRUONG_PHONG) || loginAccRole.equals(Role.PHO_PHONG))
                && (account.getStatus().equals(Status.INACTIVE.name())
                        || account.getStatus().equals(Status.INITIAL.name())
                                && priorityRoles(loginAccRole) > priorityRoles(viewedAccRole)
                                && account.getIsConnectComputer()
                                && account.getIsConnectUsb())) {
            account.setIsShowUnlockButton(true);
            account.setIsEnabledUnlockButton(true);
        }
    }

    @Override
    public AccountDetailResponse getAccountDetail(Long accountId) throws ApiException {
        String loginAccountName = getUserName();
        Accounts accSecurity = accountRepository.findAccountsByUsername(loginAccountName);
        Accounts account = validAccount(accountId);
        Organizations organizations = organizationRepository.findById(1L).orElseThrow();
        AccountDetailResponse detailResponse = AccountDetailResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .fullName(account.getProfile().getFullName())
                .gender(account.getProfile().getGender())
                .status(account.getStatus())
                .phoneNumber(account.getProfile().getPhoneNumber())
                .avatar(account.getProfile().getAvatar())
                .roleId(account.getRoles().getId())
                .roleName(account.getRoles().getName())
                .departmentId(account.getDepartments().getId())
                .departmentName(account.getDepartments().getName())
                .organizationId(organizations.getId())
                .organizationName(organizations.getName())
                .isEnabledEditButton(false)
                .isShowEditButton(false)
                .isEnabledLockButton(false)
                .isShowLockButton(false)
                .isEnabledResetPasswordButton(false)
                .isShowResetPasswordButton(false)
                .isEnabledActivateButton(false)
                .isShowActivateButton(false)
                .build();
        validateRoleForViewButton(detailResponse, accSecurity, account);
        return detailResponse;
    }

    @Override
    public ResultList<ComputerResponse> getComputersByIdAccount(Long accountId) throws ApiException {
        if (!accountRepository.existsById(accountId)) {
            throw new ApiException(AccountErrorCode.ACCOUNT_NOT_FOUND);
        }
        List<Computers> res = computerRepository.findByAccounts_Id(accountId);
        List<ComputerResponse> list = res.stream()
                .map(computers -> ComputerResponse.builder()
                        .id(computers.getId())
                        .name(computers.getName())
                        .code(computers.getCode())
                        .type(computers.getType())
                        .build())
                .toList();
        return new ResultList<>(list);
    }

    @Override
    public HashMap<String, String> inactivateAccount(Long idAccount) throws ApiException {
        String userName = SecurityUtils.getUserName();
        // account đang đăng nhập
        Optional<AccountActive> accountLogin = accountRepository.findByUsernameActive(userName);

        // check account đang đăng nhập có tồn tại không
        if (accountLogin.isEmpty()) {
            throw new ApiException(AccountErrorCode.ACCOUNT_NOT_FOUND);
        }

        Accounts lockAccount = accountRepository
                .findById(idAccount)
                .orElseThrow(() -> new ApiException(AccountErrorCode.ACCOUNT_LOCK_NOT_FOUND));

        // check id request có trùng với người khóa
        if (lockAccount.getId().equals(accountLogin.get().getId())) {
            throw new ApiException(AccountErrorCode.ACCOUNT_TO_BE_LOCKED_IS_LOGGED_IN);
        }

        if (!lockAccount.getStatus().equals(Status.ACTIVE.name())) {
            throw new ApiException(AccountErrorCode.ACCOUNT_IS_LOCK);
        }
        // Role của người đăng nhập
        String roleCode = accountLogin.get().getRoleCode();
        // Role của người cần khóa
        String lockAccountRole = lockAccount.getRoles().getCode();
        boolean sameDepartment = accountLogin
                .get()
                .getDepartmentId()
                .equals(lockAccount.getDepartments().getId());

        switch (roleCode) {
            case VIEN_TRUONG, VIEN_PHO, IT_ADMIN -> handleLeader(Role.valueOf(roleCode), Role.valueOf(lockAccountRole));
            case TRUONG_PHONG, PHO_PHONG -> handleMember(
                    Role.valueOf(roleCode), Role.valueOf(lockAccountRole), sameDepartment);
            default -> throw new ApiException(AccountErrorCode.POSITION_NOT_FOUND);
        }
        lockAccount.setStatus(Status.INACTIVE.name());
        accountRepository.save(lockAccount);
        tokenRepository.deleteByAccounts_Id(idAccount);
        return new HashMap<>();
    }

    private Void handleLeader(Role roleLogin, Role lockAccountRole) throws ApiException {
        if (priorityRoles(roleLogin) <= priorityRoles(lockAccountRole)) {
            throw new ApiException(AccountErrorCode.NOT_ENOUGH_PERMISSION);
        }
        return null;
    }

    private Void handleMember(Role roleLogin, Role lockAccountRole, boolean sameDepartment) throws ApiException {
        if ((priorityRoles(roleLogin) > priorityRoles(lockAccountRole)) && sameDepartment) {
            return null;
        }
        throw new ApiException(AccountErrorCode.NOT_ENOUGH_PERMISSION);
    }

    @Override
    @Transactional
    public HashMap<String, String> removeConnectComputer(Long accountId, Long computerId) throws ApiException {
        Accounts accounts =
                accountRepository.findById(accountId).orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
        Computers computers =
                computerRepository.findById(computerId).orElseThrow(() -> new ApiException(COMPUTER_NOT_FOUND));
        if (!computers.getAccounts().getId().equals(accountId)) {
            throw new ApiException(COMPUTER_NOT_CONNECT_TO_ACCOUNT);
        }
        computers.setAccounts(null);
        computers.setStatus(Status.DISCONNECTED.name());
        computerRepository.save(computers);
        int soThietBiKetNoi = accounts.getComputers().size();
        if (Objects.equals(soThietBiKetNoi, 1)) {
            accounts.setIsConnectComputer(false);
            accounts.setStatus(Status.ACTIVE.name());
            accountRepository.save(accounts);
        }
        // gỡ usb token
        Optional<Usbs> usb = usbRepository.findByAccounts_Id(accountId);
        if (usb.isPresent()) {
            removeUSB(accountId, usb.get().getId());
        }
        return new HashMap<>();
    }

    @Override
    public ResultList<UsbConnectedResponse> getUsbInfo(Long id) throws ApiException {
        validAccount(id);
        return new ResultList<>(usbRepository.findUsbConnectedByAccountId(id));
    }

    @Override
    public ResultList<ConnectComputerResponse> connectComputers(Long id, Set<Long> computerIds) throws ApiException {
        Accounts accounts = validAccount(id);
        List<ConnectComputerResponse> computerResponses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(computerIds)) {
            List<Computers> computers = computerRepository.findByIdIn(computerIds);
            List<Computers> connectedComputers = new ArrayList<>();
            Map<Long, Computers> computersMap = computers.stream().collect(Collectors.toMap(Computers::getId, c -> c));
            if (CollectionUtils.isEmpty(computers)) {
                throw new ApiException(COMPUTER_NOT_FOUND);
            }
            for (Long computerId : computerIds) {
                if (computersMap.get(computerId) != null) {
                    String nameUpdate = SecurityUtils.getUserName();
                    var computer = computersMap.get(computerId);
                    if (computer.getStatus().equals(Const.CONNECTED)) {
                        computerResponses.add(ConnectComputerResponse.builder()
                                .id(computer.getId())
                                .name(computer.getName())
                                .computerCode(computer.getCode())
                                .isConnected(false)
                                .reason(COMPUTER_CONNECTED_WITH_ANOTHER_ACCOUNT)
                                .build());
                    } else {
                        computerResponses.add(ConnectComputerResponse.builder()
                                .id(computer.getId())
                                .name(computer.getName())
                                .computerCode(computer.getCode())
                                .isConnected(true)
                                .reason(COMPUTER_CONNECTED_SUCCESS)
                                .build());
                        computer.setAccounts(accounts);
                        computer.setStatus(Const.CONNECTED);
                        computer.setUpdatedAt(LocalDateTime.now());
                        computer.setUpdatedBy(nameUpdate);
                        connectedComputers.add(computer);
                        Optional<Usbs> usb = usbRepository.findByAccounts_Id(id);
                        if (usb.isPresent()) {
                            removeUSB(id, usb.get().getId());
                        }
                    }
                } else {
                    computerResponses.add(ConnectComputerResponse.builder()
                            .id(computerId)
                            .name(null)
                            .computerCode(null)
                            .isConnected(false)
                            .reason(COMPUTER_NOT_FOUND_SYSTEM)
                            .build());
                }
            }
            if (!connectedComputers.isEmpty()) {
                computerRepository.saveAll(connectedComputers);
            }
        }

        return new ResultList<>(computerResponses);
    }

    @Override
    @Transactional
    public HashMap<String, String> removeConnectUSB(Long accountID, Long usbID) throws ApiException {
        removeUSB(accountID, usbID);
        return new HashMap<>();
    }

    @Override
    public ActivedAccountResponse activeAccount(Long id) throws ApiException {
        Accounts activedAcc = validAccount(id);
        Accounts loginAcc = validAccount(SecurityUtils.getUserId());
        if (activedAcc.getStatus().equals(Const.ACTIVE)) {
            throw new ApiException(AccountErrorCode.ACCOUNT_ALREADY_ACTIVATED);
        }

        if (Boolean.FALSE.equals(activedAcc.getIsConnectComputer())) {
            throw new ApiException(AccountErrorCode.ACCOUNT_NOT_LINKED_TO_COMPUTER);
        }

        if (Boolean.FALSE.equals(activedAcc.getIsConnectUsb())) {
            throw new ApiException(AccountErrorCode.ACCOUNT_NOT_LINKED_TO_USB);
        }

        Role loginAccRole = Role.valueOf(loginAcc.getRoles().getCode());
        Role activedAccRole = Role.valueOf(activedAcc.getRoles().getCode());

        if (priorityRoles(loginAccRole) <= priorityRoles(activedAccRole)) {
            throw new ApiException(AccountErrorCode.NOT_ENOUGH_PERMISSION);
        }
        boolean isSameDepartment = Objects.equals(
                activedAcc.getDepartments().getId(), loginAcc.getDepartments().getId());
        if (activedAccRole.equals(Role.VIEN_TRUONG) || (activedAccRole.equals(Role.TRUONG_PHONG) && isSameDepartment)) {
            AccountSwapResponse old = accountRepository.getOldPositionAccInfo(
                    activedAcc.getDepartments().getId());
            if (old.getId() != null) {
                return ActivedAccountResponse.builder().oldPositionAccInfo(old).build();
            } else {
                activedAcc.setStatus(Const.ACTIVE);
                activedAcc.setUpdatedAt(LocalDateTime.now());
                activedAcc.setUpdatedBy(SecurityUtils.getUserName());
                accountRepository.save(activedAcc);
                return new ActivedAccountResponse();
            }
        }
        throw new ApiException(AccountErrorCode.DEPARTMENT_CONFLICT);
    }

    @Override
    public AccountSwapResponse swapStatus(Long newAccountId, Long oldAccountId) throws ApiException {
        return swap(newAccountId, oldAccountId);
    }

    private AccountSwapResponse swap(Long newAccountId, Long oldAccountId) throws ApiException {
        Accounts existingAccount =
                accountRepository.findById(newAccountId).orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
        if (!existingAccount.getRoles().getCode().equals(Role.VIEN_TRUONG.name())
                && !existingAccount.getRoles().getCode().equals(Role.TRUONG_PHONG.name())) {
            throw new ApiException(ROLE_NOT_TRUE);
        }

        Long departmentId = existingAccount.getDepartments().getId();
        String roleCode = existingAccount.getRoles().getCode();
        Optional<Accounts> accountLeadOptional = accountRepository.findByDepartment(departmentId, roleCode, "ACTIVE");
        if (accountLeadOptional.isEmpty()) {
            throw new ApiException(LEADER_NOT_FOUND);
        }
        Accounts accountLead = accountLeadOptional.get();
        if (!accountLead.getId().equals(oldAccountId)) {
            return AccountSwapResponse.builder()
                    .id(accountLead.getId())
                    .fullname(accountLead.getProfile().getFullName())
                    .build();
        }

        accountLead.setStatus("INACTIVE");
        existingAccount.setStatus("ACTIVE");
        accountRepository.save(existingAccount);
        accountRepository.save(accountLead);
        return AccountSwapResponse.builder().build();
    }

    @Override
    @Transactional
    public AccountResponse createAccountInfo(AccountCreateRequest request) throws ValidationException, ApiException {
        Accounts curLoginAcc = SecurityUtils.getUser();

        if (!isAllowedToCreateAccount(curLoginAcc)) {
            throw new ApiException(AccountErrorCode.NOT_ENOUGH_PERMISSION);
        }

        Map<String, String> errors = validateAccountCreateRequest(request);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Profiles profile = createProfile(request);
        Accounts account = createAccount(request, profile);
        profile.setAccounts(account);
        profileRepository.save(profile);
        Accounts savedAccount = accountRepository.save(account);
        return AccountResponse.builder().id(savedAccount.getId()).build();
    }

    @Override
    public ResultUrl uploadAvatar(MultipartFile file) throws ApiException, IOException {
        validateAvatarFile(file);

        Path uploadPath = Files.createDirectories(Paths.get(uploadDir));
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return new ResultUrl(appHost + AVATAR_URI + uniqueFileName);
    }

    @Override
    public byte[] downloadAvatar(String fileName) throws ApiException, IOException {
        if (fileName == null || fileName.isEmpty()) {
            throw new ApiException(AVATAR_NOT_FOUND);
        }
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        return Files.readAllBytes(filePath);
    }

    @Override
    @Transactional
    public AccountSwapResponse updateAccountInfo(Long updatedAccId, AccountUpdateRequest req) throws ApiException {
        Accounts accountLogin = SecurityUtils.getUser();
        Accounts accountUpdate = validAccount(updatedAccId);

        int priorityRoleUpdate = priorityRoles(Role.valueOf(accountUpdate.getRoles().getCode()));
        int priorityRoleLogin = priorityRoles(Role.valueOf(accountLogin.getRoles().getCode()));

        if (priorityRoleLogin <= priorityRoleUpdate){
            throw new ApiException(AccountErrorCode.NOT_ENOUGH_PERMISSION);
        }

        Roles updateAccRole = roleRepository.findById(req.getRoleId()).orElseThrow();
        if (!updateAccRole.getCode().equals(Role.VIEN_TRUONG.name())
                && !updateAccRole.getCode().equals(Role.TRUONG_PHONG.name())) {
            accountToUpdate(req, updatedAccId, updateAccRole);
            return AccountSwapResponse.builder().build();
        }
        AccountSwapResponse oldPositionAccInfo = accountRepository.getOldPositionAccInfo(req.getDepartmentId());
        if (Objects.equals(oldPositionAccInfo, null)) {
            accountToUpdate(req, updatedAccId, updateAccRole);
            return AccountSwapResponse.builder().build();
        }
        if (!oldPositionAccInfo.getId().equals(req.getSwappedAccId())) {
            return oldPositionAccInfo;
        }
        Accounts accountLead = accountRepository.findById(req.getSwappedAccId()).orElseThrow();
        accountLead.setStatus(Status.INACTIVE.name());
        Accounts account = accountToUpdate(req, updatedAccId, updateAccRole);
        account.setStatus(Status.ACTIVE.name());
        return AccountSwapResponse.builder().build();
    }

    @Override
    public UserInfo userInfo() throws ApiException {
        Long userId = SecurityUtils.getUserId();
        Optional<UserInfo> userDetail = accountRepository.findAccountProfileById(userId);
        if (userDetail.isEmpty()) {
            throw new ApiException(AccountErrorCode.ACCOUNT_ALREADY_EXISTS);
        }
        return userDetail.get();
    }

    @Override
    public HashMap<String, String> changePinUserLogin(PinChangeRequest pinRequest) throws ApiException {
        String userName = getUserName();
        Accounts accountRequest = accountRepository
                .findAccountInSystem(userName)
                .orElseThrow(() -> new ApiException(AccountErrorCode.ACCOUNT_NOT_FOUND));
        if (!accountRequest.getStatus().equals(Status.ACTIVE.name())) {
            throw new ApiException(AccountErrorCode.ACCOUNT_INACTIVE);
        }
        if (Boolean.FALSE.equals(accountRequest.getIsConditionLogin1())) {
            throw new ApiException(AccountErrorCode.CHANGE_PIN_LOGIN);
        }
        if (!passwordEncoder.matches(pinRequest.getOldPinCode(), accountRequest.getPin())) {
            throw new ApiException(AccountErrorCode.OLD_PIN_INCORRECT);
        }
        if (pinRequest.getOldPinCode().equals(pinRequest.getNewPinCode())) {
            throw new ApiException(AccountErrorCode.NEW_PIN_SAME_AS_OLD);
        }
        if (!pinRequest.getNewPinCode().equals(pinRequest.getConfirmPinCode())) {
            throw new ApiException(AccountErrorCode.CONFIRM_PIN_MISMATCH);
        }
        accountRequest.setPin(passwordEncoder.encode(pinRequest.getConfirmPinCode()));
        accountRepository.save(accountRequest);
        return new HashMap<>();
    }

    private void clearPathImg(String path) throws ApiException {
        Path filePath = Paths.get(getPathImg(path));
        try {
            if (Files.exists(filePath)) {
                // Xóa tệp nếu tồn tại
                Files.delete(filePath);
            }
        } catch (IOException e) {
            throw new ApiException(SystemErrorCode.INTERNAL_SERVER);
        }
    }

    private String getPathImg(String url) {
        return uploadDir + "/" + url.substring((appHost + AVATAR_URI).length());
    }

    private AccountResponse removeUSB(Long accountID, Long usbID) throws ApiException {
        Accounts accounts = accountRepository
                .findById(accountID)
                .orElseThrow(() -> new ApiException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        if (!Objects.equals(accounts.getUsb().getId(), usbID)) {
            throw new ApiException(ACCOUNT_NOT_LINKED_TO_USB);
        }
        accounts.setUsb(null);
        accounts.setIsConnectUsb(false);
        Accounts accRemove = accountRepository.save(accounts);
        return accountMapper.toResponse(accRemove);
    }

    private void validateRoleForViewButton(
            AccountDetailResponse detailResponse, Accounts accSecurity, Accounts account) {
        Role loginAcc = Role.valueOf(accSecurity.getRoles().getCode());
        Role detailedAcc = Role.valueOf(account.getRoles().getCode());

        if (loginAcc.equals(Role.IT_ADMIN)) {
            detailResponse.setIsEnabledEditButton(true);
            detailResponse.setIsShowEditButton(true);
            detailResponse.setIsEnabledResetPasswordButton(true);
            detailResponse.setIsShowResetPasswordButton(true);
        }

        if (loginAcc.equals(Role.IT_ADMIN) || priorityRoles(loginAcc) > priorityRoles(detailedAcc)) {
            detailResponse.setIsEnabledLockButton(true);
        }
        if (account.getStatus().equals(Status.ACTIVE.name()) && priorityRoles(loginAcc) > priorityRoles(detailedAcc)) {
            detailResponse.setIsShowLockButton(true);
        }
        if ((loginAcc.equals(Role.IT_ADMIN) || priorityRoles(loginAcc) > priorityRoles(detailedAcc))
                && account.getIsConnectUsb().equals(Boolean.TRUE)
                && account.getIsConnectComputer().equals(Boolean.TRUE)) {
            detailResponse.setIsEnabledActivateButton(true);
        }
        if (!account.getStatus().equals(Status.ACTIVE.name()) && priorityRoles(loginAcc) > priorityRoles(detailedAcc)) {
            detailResponse.setIsShowActivateButton(true);
        }
        if ((loginAcc.equals(Role.TRUONG_PHONG) || loginAcc.equals(Role.PHO_PHONG))
                && priorityRoles(loginAcc) > priorityRoles(detailedAcc)
                && accSecurity
                        .getDepartments()
                        .getId()
                        .equals(account.getDepartments().getId())
                && account.getStatus().equals(Status.ACTIVE.name())) {
            detailResponse.setIsShowLockButton(true);
            detailResponse.setIsEnabledLockButton(true);
        } else {
            detailResponse.setIsShowLockButton(false);
            detailResponse.setIsEnabledLockButton(false);
        }
    }

    private boolean isAllowedToCreateAccount(Accounts curLoginAcc) {
        List<String> roleAccepts = List.of(VIEN_TRUONG, VIEN_PHO);
        if (roleAccepts.contains(curLoginAcc.getRoles().getCode())) {
            return false;
        }

        Departments curLoginDepartment = departmentRepository.findByAccounts_Id(curLoginAcc.getId());
        return Objects.equals(curLoginDepartment.getCode(), DepartmentCode.PB_KY_THUAT.name());
    }

    private Map<String, String> validateAccountCreateRequest(AccountCreateRequest request) {
        Map<String, String> errors = new HashMap<>();

        validateUsername(request.getUsername(), errors);
        validateRole(request.getRoleId(), request.getRoleName(), errors);
        validateDepartment(request.getDepartmentId(), request.getDepartmentName(), errors);
        validateOrganization(request.getOrganizationId(), request.getOrganizationName(), errors);
        validateAvatar(request.getAvatar(), errors);

        return errors;
    }

    private void validateUsername(String username, Map<String, String> errors) {
        if (accountRepository.existsByUsername(username)) {
            errors.put("username", ResponseMessage.USERNAME_IS_EXIST);
        }
    }

    private void validateRole(Long roleId, String roleName, Map<String, String> errors) {
        Optional<Roles> rolesOptional = roleRepository.findById(roleId);

        if (rolesOptional.isEmpty()) {
            errors.put("roleId", ResponseMessage.ROLE_NOT_EXIST);
        } else {
            Roles role = rolesOptional.get();
            if (!Objects.equals(role.getName(), roleName)) {
                errors.put("roleName", ResponseMessage.OUTDATED_DATA);
            }
        }
    }

    private void validateDepartment(Long departmentId, String departmentName, Map<String, String> errors) {
        Optional<Departments> departmentsOptional = departmentRepository.findById(departmentId);

        if (departmentsOptional.isEmpty()) {
            errors.put("departmentId", ResponseMessage.DEPARTMENT_NOT_EXIST);
        } else {
            Departments department = departmentsOptional.get();
            if (!Objects.equals(department.getName(), departmentName)) {
                errors.put("departmentName", ResponseMessage.OUTDATED_DATA);
            }
        }
    }

    private void validateOrganization(Long organizationId, String organizationName, Map<String, String> errors) {
        Optional<Organizations> organizationsOptional = organizationRepository.findById(organizationId);

        if (organizationsOptional.isEmpty()) {
            errors.put("organizationId", ResponseMessage.ORGANIZATION_NOT_EXIST);
        } else {
            Organizations organization = organizationsOptional.get();
            if (!Objects.equals(organization.getName(), organizationName)) {
                errors.put("organizationName", ResponseMessage.OUTDATED_DATA);
            }
        }
    }

    private Profiles createProfile(AccountCreateRequest request) {
        return Profiles.builder()
                .fullName(request.getFullName())
                .avatar(request.getAvatar())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .build();
    }

    private Accounts createAccount(AccountCreateRequest request, Profiles profile) {
        return Accounts.builder()
                .username(request.getUsername())
                .roles(roleRepository.findById(request.getRoleId()).orElseThrow())
                .departments(
                        departmentRepository.findById(request.getDepartmentId()).orElseThrow())
                .password(passwordEncoder.encode(request.getUsername()))
                .isConditionLogin1(false)
                .isConditionLogin2(false)
                .isConnectComputer(false)
                .isConnectUsb(false)
                .status(Status.INITIAL.name())
                .profile(profile)
                .build();
    }

    public void validateAvatar(String avatarUrl, Map<String, String> errors) {
        if (StringUtils.isBlank(avatarUrl)) {
            return;
        }
        String keyError = "avatar";

        try {
            URI uri = new URI(avatarUrl);

            String scheme = uri.getScheme();
            if (!"http".equals(scheme) && !"https".equals(scheme)) {
                errors.put(keyError, AVATAR_URL_INVALID);
                return;
            }

            String host = uri.getHost();
            if (host == null || !appHost.contains(host)) {
                errors.put(keyError, AVATAR_URL_INVALID);
                return;
            }

            String path = uri.getPath();
            if (path == null || !path.startsWith(AVATAR_URI)) {
                errors.put(keyError, AVATAR_URL_INVALID);
                return;
            }

            if (!isPathAllowedExtension(path, AVATAR_ALLOWED_EXTENSIONS)) {
                errors.put(keyError, AVATAR_URL_INVALID);
            }

        } catch (URISyntaxException e) {
            errors.put("avatar", AVATAR_URL_INVALID);
        }
    }

    private Accounts accountToUpdate(AccountUpdateRequest req, Long updatedAccId, Roles updateAccRole)
            throws ApiException {
        Profiles profile = profileRepository.findByAccounts_Id(updatedAccId);
        Accounts updatedAcc = accountRepository.findById(updatedAccId).orElseThrow();

        profile.setFullName(req.getFullName());
        profile.setPhoneNumber(req.getPhoneNumber());
        profile.setGender(req.getGender());
        if (Objects.nonNull(req.getAvatar()) && !req.getAvatar().equals(profile.getAvatar())) {
            clearPathImg(profile.getAvatar());
            String pathFile = getPathImg(req.getAvatar());
            if (!Files.exists(Paths.get(pathFile))) {
                throw new ApiException(AccountErrorCode.PATH_AVATAR_NOT_FOUND);
            }
            profile.setAvatar(req.getAvatar());
        }
        Profiles profileSave = profileRepository.save(profile);

        updatedAcc.setProfile(profileSave);
        updatedAcc.setRoles(updateAccRole);
        updatedAcc.setDepartments(
                departmentRepository.findById(req.getDepartmentId()).orElseThrow());
        return accountRepository.save(updatedAcc);
    }

    private void validateAvatarFile(MultipartFile file) throws ApiException {
        if (file == null || file.isEmpty()) {
            return;
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!isAllowedExtension(fileExtension, AVATAR_ALLOWED_EXTENSIONS)) {
            String msg = MessageFormat.format(
                    AccountErrorCode.AVATAR_INVALID_FORMAT.getMessage(), String.join(", ", AVATAR_ALLOWED_EXTENSIONS));
            throw new ApiException(AccountErrorCode.AVATAR_INVALID_FORMAT, msg);
        }

        if (file.getSize() > MAX_AVATAR_SIZE * BYTES_IN_MB) {
            String msg = MessageFormat.format(AccountErrorCode.AVATAR_SIZE_EXCEEDS_LIMIT.getMessage(), MAX_AVATAR_SIZE);
            throw new ApiException(AccountErrorCode.AVATAR_SIZE_EXCEEDS_LIMIT, msg);
        }
    }

    private int priorityRoles(Role role) {
        return switch (role) {
            case IT_ADMIN -> 11;
            case VIEN_TRUONG -> 10;
            case VIEN_PHO -> 9;
            case TRUONG_PHONG -> 8;
            case PHO_PHONG -> 7;
            default -> 0;
        };
    }
}

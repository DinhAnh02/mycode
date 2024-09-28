package vn.eledevo.vksbe.service.account;

import static vn.eledevo.vksbe.constant.ErrorCode.*;
import static vn.eledevo.vksbe.constant.FileConst.*;
import static vn.eledevo.vksbe.constant.ResponseMessage.*;
import static vn.eledevo.vksbe.constant.RoleCodes.*;
import static vn.eledevo.vksbe.utils.FileUtils.*;
import static vn.eledevo.vksbe.utils.SecurityUtils.getUserName;

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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import vn.eledevo.vksbe.constant.*;
import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.model.account.OldPositionAccInfo;
import vn.eledevo.vksbe.dto.request.AccountInactive;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.request.account.AccountCreateRequest;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter;
import vn.eledevo.vksbe.dto.response.account.AccountSwapResponse;
import vn.eledevo.vksbe.dto.response.account.ActivedAccountResponse;
import vn.eledevo.vksbe.dto.response.account.ObjectSwapResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.dto.response.computer.ConnectComputerResponse;
import vn.eledevo.vksbe.dto.response.usb.UsbResponse;
import vn.eledevo.vksbe.entity.*;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.mapper.AccountMapper;
import vn.eledevo.vksbe.mapper.ComputerMapper;
import vn.eledevo.vksbe.mapper.UsbMapper;
import vn.eledevo.vksbe.repository.*;
import vn.eledevo.vksbe.utils.Const;
import vn.eledevo.vksbe.utils.SecurityUtils;

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
    ComputerMapper computerMapper;
    UsbRepository usbRepository;
    UsbMapper usbMapper;
    RoleRepository roleRepository;
    DepartmentRepository departmentRepository;
    OrganizationRepository organizationRepository;

    @Value("${file.upload-dir}")
    @NonFinal
    String uploadDir;

    @Value("${app.host}")
    @NonFinal
    private String appHost;

    private Accounts validAccount(Long id) throws ApiException {
        return accountRepository.findById(id).orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
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
    public String resetPassword(Long id) throws ApiException {
        Accounts accounts = validAccount(id);
        accounts.setPassword(passwordEncoder.encode(accounts.getUsername()));
        accounts.setPin(null);
        accounts.setIsConditionLogin1(false);
        accounts.setIsConditionLogin2(false);
        accounts.setUpdateBy(getUserName());
        accounts.setUpdateAt(LocalDateTime.now());
        accountRepository.save(accounts);
        tokenRepository.deleteByAccounts_Id(id);
        return ResponseMessage.RESET_PASSWORD_SUCCESS;
    }

    private Boolean isBoss(Accounts accSecurity) {
        return switch (accSecurity.getRoles().getCode()) {
            case IT_ADMIN, VIEN_TRUONG -> true;
            case VIEN_PHO -> true;
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
        Page<AccountResponseByFilter> page =
                accountRepository.getAccountList(accountRequest, isBoss(accSecurity), pageable);

        page.getContent().forEach(account -> {
            checkRoleToShowLockOrUnlockButton(account, accSecurity);
        });
        return new ResponseFilter<>(
                page.getContent(),
                (int) page.getTotalElements(),
                page.getSize(),
                page.getNumber(),
                page.getTotalPages());
    }

    private void checkRoleToShowLockOrUnlockButton(AccountResponseByFilter account, Accounts accSecurity) {
        Role viewedAccRole = Role.valueOf(account.getRoleCode());
        Role loginAccRole = Role.valueOf(accSecurity.getRoles().getCode());
        if (account.getStatus().equals("ACTIVE") && priorityRoles(loginAccRole) > priorityRoles(viewedAccRole)) {
            account.setIsShowLockButton(true);
            account.setIsEnableLockButton(true);
        }
        if ((account.getStatus().equals("INACTIVE") || account.getStatus().equals("INITIAL"))
                && priorityRoles(loginAccRole) > priorityRoles(viewedAccRole)
                && account.getIsConnectComputer()
                && account.getIsConnectUsb()) {
            account.setIsShowUnlockButton(true);
            account.setIsEnableUnlockButton(true);
        }
        if ((loginAccRole.equals(Role.TRUONG_PHONG) || loginAccRole.equals(Role.PHO_PHONG))
                && accSecurity.getDepartments().getId().equals(account.getDepartmentId())
                && account.getStatus().equals("ACTIVE")
                && priorityRoles(loginAccRole) > priorityRoles(viewedAccRole)) {
            account.setIsShowLockButton(true);
            account.setIsEnableLockButton(true);
        }

        if ((loginAccRole.equals(Role.TRUONG_PHONG) || loginAccRole.equals(Role.PHO_PHONG))
                && (account.getStatus().equals("INACTIVE")
                        || account.getStatus().equals("INITIAL")
                                && priorityRoles(loginAccRole) > priorityRoles(viewedAccRole)
                                && account.getIsConnectComputer()
                                && account.getIsConnectUsb())) {
            account.setIsShowUnlockButton(true);
            account.setIsEnableUnlockButton(true);
        }
    }

    @Override
    public AccountDetailResponse getAccountDetail(Long accountId) throws ApiException {
        String loginAccountName = getUserName();
        Accounts accSecurity = accountRepository.findAccountsByUsername(loginAccountName);
        Accounts account = validAccount(accountId);
        Organizations organizations = organizationRepository.findById(1L).get();
        AccountDetailResponse detailResponse = AccountDetailResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .fullName(account.getProfile().getFullName())
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
                .isEnableResetPasswordButton(false)
                .isShowResetPasswordButton(false)
                .isEnabledActivateButton(false)
                .isShowActivateButton(false)
                .build();
        validateRoleForViewButton(detailResponse, accSecurity, account);
        return detailResponse;
    }

    @Override
    public List<ComputerResponse> getComputersByIdAccount(Long accountId) throws ApiException {
        if (!accountRepository.existsById(accountId)) {
            throw new ApiException(ACCOUNT_NOT_FOUND);
        }
        List<Computers> res = computerRepository.findByAccounts_Id(accountId);
        return computerMapper.toListResponse(res);
    }

    @Override
    public String inactivateAccount(Long idAccount) throws ApiException {
        String userName = SecurityUtils.getUserName();
        Optional<AccountInactive> accountRequest = accountRepository.findByUsernameActive(userName);

        // check account có tồn tại không
        if (accountRequest.isEmpty()) {
            throw new ApiException(ACCOUNT_NOT_FOUND);
        }

        Accounts existingAccount =
                accountRepository.findById(idAccount).orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));

        // check id request có trùng với người khóa
        if (existingAccount.getId().equals(accountRequest.get().getId())) {
            throw new ApiException(DUPLICATE_ACCOUNT);
        }

        if (!existingAccount.getStatus().equals(Status.ACTIVE.name())) {
            throw new ApiException(UNCATEGORIZED_EXCEPTION);
        }

        String roleCode = accountRequest.get().getRoleCode();
        String existingRoleCode = existingAccount.getRoles().getCode();
        boolean sameDepartment = accountRequest
                .get()
                .getDepartmentId()
                .equals(existingAccount.getDepartments().getId());

        switch (roleCode) {
            case VIEN_TRUONG -> handleVienTruong(existingRoleCode);
            case VIEN_PHO -> handleVienPho(existingRoleCode);
            case TRUONG_PHONG -> handleTruongPhong(existingRoleCode, sameDepartment);
            case PHO_PHONG -> handlePhoPhong(existingRoleCode, sameDepartment);
            default -> throw new ApiException(UNCATEGORIZED_EXCEPTION);
        }
        existingAccount.setStatus(Status.INACTIVE.name());
        accountRepository.save(existingAccount);
        return ResponseMessage.LOCK_ACCOUNT_SUCCESS;
    }

    private void handleVienTruong(String existingRoleCode) throws ApiException {
        if (existingRoleCode.equals(IT_ADMIN)) {
            throw new ApiException(ACCOUNT_NOT_LOCK);
        }
    }

    private void handleVienPho(String existingRoleCode) throws ApiException {
        List<String> restrictedRoles = List.of(IT_ADMIN,VIEN_TRUONG, VIEN_PHO);
        if (restrictedRoles.contains(existingRoleCode)) {
            throw new ApiException(ACCOUNT_NOT_LOCK);
        }
    }

    private void handleTruongPhong(String existingRoleCode, boolean sameDepartment) throws ApiException {
        if ((!existingRoleCode.equals(PHO_PHONG) && !existingRoleCode.equals(KIEM_SAT_VIEN)) || !sameDepartment) {
            throw new ApiException(ACCOUNT_NOT_LOCK);
        }
    }

    private void handlePhoPhong(String existingRoleCode, boolean sameDepartment) throws ApiException {
        if (!existingRoleCode.equals(KIEM_SAT_VIEN) || !sameDepartment) {
            throw new ApiException(ACCOUNT_NOT_LOCK);
        }
    }

    @Override
    @Transactional
    public ApiResponse<String> removeConnectComputer(Long accountId, Long computerId) throws ApiException {
        try {
            Accounts accounts =
                    accountRepository.findById(accountId).orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
            Computers computers =
                    computerRepository.findById(computerId).orElseThrow(() -> new ApiException(COMPUTER_NOT_FOUND));
            String STATUS_COMPUTER_DISCONNECTED = "DISCONNECTED";
            if (!computers.getAccounts().getId().equals(accountId)) {
                throw new ApiException(COMPUTER_NOT_CONNECT_TO_ACCOUNT);
            }
            computers.setAccounts(null);
            computers.setStatus(STATUS_COMPUTER_DISCONNECTED);
            computerRepository.save(computers);
            int soThietBiKetNoi = accounts.getComputers().size();
            if (Objects.equals(soThietBiKetNoi, 1)) {
                accounts.setIsConnectComputer(false);
                accounts.setStatus("INACTIVE");
                accountRepository.save(accounts);
            }
            // gỡ usb token
            Optional<Usbs> usb = usbRepository.findByAccounts_Id(accountId);
            if (usb.isPresent()) {
                removeUSB(accountId, usb.get().getId());
            }
            return ApiResponse.ok("Gỡ liên kết máy tính với tài khoản thành công");
        } catch (Exception e) {
            throw new ApiException(UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public UsbResponse getUsbInfo(Long id) throws ApiException {
        validAccount(id);
        Optional<Usbs> usbEntities = usbRepository.findByAccounts_Id(id);
        if (usbEntities.isPresent()) {
            return usbMapper.toTarget(usbEntities.get());
        } else {
            throw new ApiException(ACCOUNT_NOT_CONNECT_USB);
        }
    }

    @Override
    public Result<?> connectComputers(Long id, Set<Long> computerIds) throws ApiException {
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
                                .code(computer.getCode())
                                .message("Thiết bị đã được kết nối với tài khoản khác")
                                .build());
                    } else {
                        computerResponses.add(ConnectComputerResponse.builder()
                                .id(computer.getId())
                                .name(computer.getName())
                                .code(computer.getCode())
                                .message("Kết nối thiết bị thành công")
                                .build());
                        computer.setAccounts(accounts);
                        computer.setStatus(Const.CONNECTED);
                        computer.setUpdateAt(LocalDateTime.now());
                        computer.setUpdateBy(nameUpdate);
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
                            .code(null)
                            .message("Không tồn tại thiết bị")
                            .build());
                }
            }
            if (!connectedComputers.isEmpty()) {
                computerRepository.saveAll(connectedComputers);
            }
        }

        return new Result<>(computerResponses, computerResponses.size());
    }

    @Override
    @Transactional
    public ApiResponse<?> removeUSB(Long accountID, Long usbID) throws ApiException {
        Accounts accounts =
                accountRepository.findById(accountID).orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));

        if (!Objects.equals(accounts.getUsb().getId(), usbID)) {
            throw new ApiException(ACCOUNT_NOT_CONNECT_USB);
        }
        accounts.setUsb(null);
        accounts.setIsConnectUsb(false);
        Accounts accRemove = accountRepository.save(accounts);
        AccountResponse accountResponse = accountMapper.toResponse(accRemove);
        return ApiResponse.ok(accountResponse);
    }

    @Override
    public ActivedAccountResponse activeAccount(Long id) throws ApiException {
        Accounts activedAcc = validAccount(id);
        Accounts loginAcc = validAccount(SecurityUtils.getUserId());
        if (activedAcc.getStatus().equals(Const.ACTIVE)) {
            throw new ApiException(ACCOUNT_STATUS_ACTIVE);
        }

        if (Boolean.FALSE.equals(activedAcc.getIsConnectComputer())) {
            throw new ApiException(ACCOUNT_NOT_CONNECT_USB);
        }

        if (Boolean.FALSE.equals(activedAcc.getIsConnectUsb())) {
            throw new ApiException(ACCOUNT_NOT_CONNECT_USB);
        }

        Role loginAccRole = Role.valueOf(loginAcc.getRoles().getCode());
        Role activedAccRole = Role.valueOf(activedAcc.getRoles().getCode());

        if (priorityRoles(loginAccRole) <= priorityRoles(activedAccRole)) {
            throw new ApiException(UNAUTHORIZED_ACTIVE_ACCOUNT);
        }
        boolean isSameDepartment = Objects.equals(activedAcc.getDepartments().getId(),loginAcc.getDepartments().getId());
        if (activedAccRole.equals(Role.VIEN_TRUONG) || (activedAccRole.equals(Role.TRUONG_PHONG) && isSameDepartment)) {
            OldPositionAccInfo old = accountRepository.getOldPositionAccInfo(
                    activedAcc.getDepartments().getId());
            if (old.getId() != null) {
                return ActivedAccountResponse.builder().oldPositionAccInfo(old).build();
            } else {
                activedAcc.setStatus(Const.ACTIVE);
                activedAcc.setUpdateAt(LocalDateTime.now());
                activedAcc.setUpdateBy(SecurityUtils.getUserName());
                accountRepository.save(activedAcc);
            }
        }
        return ActivedAccountResponse.builder().message(ACTIVE_ACCOUNT_SUCCESS).build();
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

    @Override
    public ObjectSwapResponse swapStatus(Long newAccountId, Long oldAccountId) throws ApiException {
        return swap(newAccountId, oldAccountId);
    }

    private ObjectSwapResponse swap(Long newAccountId, Long oldAccountId) throws ApiException {
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
            AccountSwapResponse accountSwapResponse = AccountSwapResponse.builder()
                    .id(accountLead.getId())
                    .fullname(accountLead.getProfile().getFullName())
                    .roleName(accountLead.getRoles().getName())
                    .status(accountLead.getStatus())
                    .build();
            return ObjectSwapResponse.builder()
                    .accountSwapResponse(accountSwapResponse)
                    .build();
        }

        accountLead.setStatus("INACTIVE");
        existingAccount.setStatus("ACTIVE");
        accountRepository.save(existingAccount);
        accountRepository.save(accountLead);
        return ObjectSwapResponse.builder().message(SWAP_ACCOUNT_SUCCESS).build();
    }

    @Override
    @Transactional
    public AccountResponse createAccountInfo(AccountCreateRequest request) throws ValidationException, ApiException {
        Accounts curLoginAcc = SecurityUtils.getUser();

        if (!isAllowedToCreateAccount(curLoginAcc)) {
            throw new ApiException(PERMISSION_DENIED);
        }

        Map<String, String> errors = validateAccountCreateRequest(request);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Profiles profile = createProfile(request);
        Accounts account = createAccount(request, profile);

        Accounts savedAccount = accountRepository.save(account);
        return accountMapper.toResponse(savedAccount);
    }

    private void validateRoleForViewButton(
            AccountDetailResponse detailResponse, Accounts accSecurity, Accounts account) {
        Role loginAcc = Role.valueOf(accSecurity.getRoles().getCode());
        Role detailedAcc = Role.valueOf(account.getRoles().getCode());

        if (loginAcc.equals(Role.IT_ADMIN)) {
            detailResponse.setIsEnabledEditButton(true);
            detailResponse.setIsShowEditButton(true);
            detailResponse.setIsEnableResetPasswordButton(true);
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

        Departments curLoginDepartment = departmentRepository.findByAccountId(curLoginAcc.getId());
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
                .build();
    }

    private Accounts createAccount(AccountCreateRequest request, Profiles profile) {
        return Accounts.builder()
                .username(request.getUsername())
                .roles(roleRepository.findById(request.getRoleId()).orElseThrow())
                .departments(
                        departmentRepository.findById(request.getDepartmentId()).orElseThrow())
                .password(passwordEncoder.encode(request.getUsername()))
                .isConditionLogin1(true)
                .isConditionLogin2(true)
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

    @Override
    public String uploadAvatar(MultipartFile file) throws ApiException, IOException {
        validateAvatarFile(file);

        Path uploadPath = Files.createDirectories(Paths.get(uploadDir));
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return appHost + AVATAR_URI + uniqueFileName;
    }

    @Override
    public byte[] downloadAvatar(String fileName) throws ApiException, IOException {
        if (fileName == null || fileName.isEmpty()) {
            throw new ApiException(AVATAR_NOT_FOUND);
        }
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        return Files.readAllBytes(filePath);
    }

    private void validateAvatarFile(MultipartFile file) throws ApiException {
        if (file == null || file.isEmpty()) {
            throw new ApiException(AVATAR_EMPTY);
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!isAllowedExtension(fileExtension, AVATAR_ALLOWED_EXTENSIONS)) {
            String msg = MessageFormat.format(
                    ErrorCode.AVATAR_EXTENSION_INVALID.getMessage(), String.join(", ", AVATAR_ALLOWED_EXTENSIONS));
            throw new ApiException(ErrorCode.AVATAR_EXTENSION_INVALID, msg);
        }

        if (file.getSize() > MAX_AVATAR_SIZE * BYTES_IN_MB) {
            String msg = MessageFormat.format(ErrorCode.AVATAR_MAX_SIZE.getMessage(), MAX_AVATAR_SIZE);
            throw new ApiException(ErrorCode.AVATAR_MAX_SIZE, msg);
        }
    }
}

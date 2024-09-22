package vn.eledevo.vksbe.service.account;

import static vn.eledevo.vksbe.constant.ErrorCode.*;
import static vn.eledevo.vksbe.constant.FileConst.*;
import static vn.eledevo.vksbe.constant.ResponseMessage.*;
import static vn.eledevo.vksbe.constant.RoleCodes.VIEN_PHO;
import static vn.eledevo.vksbe.constant.RoleCodes.VIEN_TRUONG;
import static vn.eledevo.vksbe.utils.FileUtils.getFileExtension;
import static vn.eledevo.vksbe.utils.FileUtils.isAllowedExtension;
import static vn.eledevo.vksbe.utils.SecurityUtils.getUserName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
import vn.eledevo.vksbe.constant.DepartmentCode;
import vn.eledevo.vksbe.constant.ResponseMessage;
import vn.eledevo.vksbe.constant.Role;
import vn.eledevo.vksbe.constant.RoleCodes;
import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.model.account.AccountInfo;
import vn.eledevo.vksbe.dto.request.AccountInactive;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.request.account.AccountCreateRequest;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter;
import vn.eledevo.vksbe.dto.response.account.AccountSwapResponse;
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

    @Override
    @Transactional
    public ApiResponse<Result<AccountResponseByFilter>> getListAccountByFilter(
            AccountRequest accountRequest, Integer currentPage, Integer limit) throws ApiException {
        try {
            if (accountRequest.getFromDate() == null) {
                accountRequest.setFromDate(LocalDateTime.of(1900, 1, 1, 0, 0));
            }
            if (accountRequest.getToDate() == null) {
                accountRequest.setToDate(LocalDateTime.now());
            }
            if (accountRequest.getFromDate().isAfter(accountRequest.getToDate())) {
                throw new ApiException(CHECK_FROM_DATE);
            }

            if (currentPage == null || currentPage < 1) {
                currentPage = 1;
            }
            if (limit == null) {
                limit = 10;
            }
            Pageable pageable = PageRequest.of(currentPage - 1, limit);
            Page<Object[]> page = accountRepository.getAccountList(accountRequest, pageable);
            List<AccountResponseByFilter> responseByFilters = page.getContent().stream()
                    .map(ele -> AccountResponseByFilter.builder()
                            .username((String) ele[0])
                            .fullName((String) ele[1])
                            .roleId((Long) ele[2])
                            .roleName((String) ele[3])
                            .departmentId((Long) ele[4])
                            .departmentName((String) ele[5])
                            .status((String) ele[6])
                            .createAt((LocalDateTime) ele[7])
                            .updateAt((LocalDateTime) ele[8])
                            .organizationId(accountRequest.getOrganizationId())
                            .organizationName(accountRequest.getOrganizationName())
                            .build())
                    .toList();
            Result<AccountResponseByFilter> result = new Result<>(responseByFilters, page.getTotalElements());
            return ApiResponse.ok(result);
        } catch (Exception e) {
            throw new ApiException(UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public ApiResponse<AccountDetailResponse> getAccountDetail(Long accountId) throws ApiException {
        try {
            String username = getUserName();
            AccountInfo acc = accountRepository.findByUsername(username);
            Accounts account = validAccount(accountId);

            AccountDetailResponse accountDetailResponse = AccountDetailResponse.builder()
                    .username(account.getUsername())
                    .fullName(account.getProfile().getFullName())
                    .departmentName(account.getDepartments().getName())
                    .roleName(account.getRoles().getName())
                    .status(account.getStatus())
                    .phoneNumber(account.getProfile().getPhoneNumber())
                    .avatar(account.getProfile().getAvatar())
                    .isDisplayAllButton(RoleCodes.IT_ADMIN.equals(acc.getRoleCode()))
                    .isActive("ACTIVE".equals(account.getStatus()))
                    .isReadOnly(isReadOnly(acc, account))
                    .isDisableActiveButton(isDisableActiveButton(account))
                    .build();
            return ApiResponse.ok(accountDetailResponse);
        } catch (Exception e) {
            throw new ApiException(UNCATEGORIZED_EXCEPTION, e.getMessage());
        }
    }

    /**
     * Kiểm tra điều kiện hiển thị nút kích hoạt/khoá
     * Điều kiện hiển thị la:
     * Role đăng nhập là "VIEN_TRUONG"
     * Role đăng nhập là "IT_ADMIN"
     * Role đăng nhập là "VIEN_PHO" và role xem chi tiết không phải "VIEN_TRUONG" hoặc "VIEN_PHO"
     * Cùng phòng ban, role đăng nhập là "TRUONG_PHONG" và role xem chi tiết không phải "TRUONG_PHONG"
     * Cùng phòng ban, role đăng nhập là "PHO_PHONG" và role xem chi tiết là "KIEM_SAT_VIEN"
     * --> isReadOnly = false
     *
     * @param acc     Thông tin tài khoản đang đăng nhập
     * @param account Thông tin tài khoản được xem chi tiết
     * @return boolean true: không hiển thị nút nào, false: hiển thị nút kích hoạt/khoá
     */
    private static boolean isReadOnly(AccountInfo acc, Accounts account) {
        String roleCodeLogin = acc.getRoleCode();
        String roleCodeDetail = account.getRoles().getCode();
        Long departmentLogin = acc.getDepartmentId();
        Long departmentDetail = account.getDepartments().getId();

        return !(VIEN_TRUONG.equals(roleCodeLogin)
                || RoleCodes.IT_ADMIN.equals(roleCodeLogin)
                || (VIEN_PHO.equals(roleCodeLogin)
                        && !VIEN_TRUONG.equals(roleCodeDetail)
                        && !VIEN_PHO.equals(roleCodeDetail))
                || (departmentLogin.equals(departmentDetail)
                        && RoleCodes.TRUONG_PHONG.equals(roleCodeLogin)
                        && !RoleCodes.TRUONG_PHONG.equals(roleCodeDetail))
                || (departmentLogin.equals(departmentDetail)
                        && RoleCodes.PHO_PHONG.equals(roleCodeLogin)
                        && RoleCodes.KIEM_SAT_VIEN.equals(roleCodeDetail)));
    }

    /**
     * Kiểm tra điều kiện nút kích hoạt disable hay không?
     * Điều kiện để nút kích hoạt không bị disable:
     * - isActive = false
     * - isConnectUsb = true
     * - isConnectComputer = true
     *
     * @param acc Thông tin tài khoản được xem chi tiết
     * @return boolean true: disable, false: active
     */
    private static boolean isDisableActiveButton(Accounts acc) {
        return !("INACTIVE".equals(acc.getStatus()) && acc.getIsConnectComputer() && acc.getIsConnectUsb());
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
    public ApiResponse inactivateAccount(Long idAccount) throws ApiException {
        try {

            String userName = SecurityUtils.getUserName();
            // Save account tìm được từ username
            Optional<AccountInactive> accountRequest = accountRepository.findByUsernameActive(userName);
            // Kiểm tra xem tài khoản có tồn tại không
            if (accountRequest.isEmpty()) {
                throw new ApiException(ACCOUNT_NOT_FOUND);
            }
            // Check account theo idAccount có tồn tại trong db không
            Accounts exitsingAccounts =
                    accountRepository.findById(idAccount).orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
            // Check account được lấy từ username có trùng với account muốn khóa không
            if (!exitsingAccounts.getId().equals(accountRequest.get().getId())) {
                if (exitsingAccounts.getStatus().equals("ACTIVE")) {
                    switch (accountRequest.get().getRoleCode()) {
                        case "VIEN_TRUONG":
                            if (exitsingAccounts.getRoles().getCode().equals("IT_ADMIN")) {
                                throw new ApiException(ACCOUNT_NOT_LOCK); // Viện trưởng không thể khóa IT
                            } else {
                                exitsingAccounts.setStatus("INACTIVE");
                            }
                            break;
                        case "VIEN_PHO":
                            if (exitsingAccounts.getRoles().getCode().equals("IT_ADMIN")
                                    || exitsingAccounts.getRoles().getCode().equals("VIEN_TRUONG")) {
                                throw new ApiException(ACCOUNT_NOT_LOCK); // Viện phó không thể khóa IT, VT
                            } else {
                                exitsingAccounts.setStatus("INACTIVE");
                            }
                            break;
                        case "TRUONG_PHONG":
                        case "PHO_PHONG":
                            // Trưởng phòng và phó phòng có thể khóa tài khoản có chức vụ thấp hơn
                            if (exitsingAccounts.getRoles().getCode().equals("VIEN_TRUONG")
                                    || exitsingAccounts.getRoles().getCode().equals("VIEN_PHO")
                                    || exitsingAccounts.getRoles().getCode().equals("IT_ADMIN")) {
                                throw new ApiException(ACCOUNT_NOT_LOCK); // Không được khóa viện trưởng hoặc viện phó
                            } else {
                                // Cập nhật trạng thái tài khoản
                                exitsingAccounts.setStatus("INACTIVE");
                            }
                            break;
                        default:
                            exitsingAccounts.setStatus("INACTIVE");
                            break;
                    }
                } else {
                    throw new ApiException(UNCATEGORIZED_EXCEPTION);
                }
            } else {
                throw new ApiException(DUPLICATE_ACCOUNT);
            }
            accountRepository.save(exitsingAccounts);
            return new ApiResponse<>(200, "Khóa tài khoản thành công");
        } catch (Exception e) {
            throw new ApiException(UNCATEGORIZED_EXCEPTION);
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
            // Todo gỡ usb token
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
    public List<ConnectComputerResponse> connectComputers(Long id, Set<Long> computerIds) throws ApiException {
        Accounts accounts = validAccount(id);
        if (!accounts.getStatus().equals(Const.ACTIVE)) {
            throw new ApiException(ACCOUNT_NOT_STATUS_ACTIVE);
        }

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
                                .message("Kế nối thiết bị thành công")
                                .build());
                        computer.setAccounts(accounts);
                        computer.setStatus(Const.CONNECTED);
                        computer.setUpdateAt(LocalDateTime.now());
                        computer.setUpdateBy(nameUpdate);
                        connectedComputers.add(computer);
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

        return computerResponses;
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
    public AccountResponse activeAccount(Long id) throws ApiException {
        Accounts accounts = validAccount(id);
        Accounts accSecurity = validAccount(SecurityUtils.getUserId());
        if (accounts.getStatus().equals(Const.ACTIVE)) {
            throw new ApiException(ACCOUNT_STATUS_ACTIVE);
        }

        if (!accounts.getIsConnectComputer()) {
            throw new ApiException(ACCOUNT_NOT_CONNECT_USB);
        }

        if (!accounts.getIsConnectUsb()) {
            throw new ApiException(ACCOUNT_NOT_CONNECT_USB);
        }

        Role accSecu = Role.valueOf(accSecurity.getRoles().getCode());
        Role accSave = Role.valueOf(accounts.getRoles().getCode());

        if (accSecu.equals(Role.IT_ADMIN) || priorityRoles(accSecu) <= priorityRoles(accSave)) {
            throw new ApiException(ACCOUNT_NOT_LOCK);
        }

        accounts.setStatus(Const.ACTIVE);
        accounts.setUpdateAt(LocalDateTime.now());
        accounts.setUpdateBy(SecurityUtils.getUserName());
        var res = accountRepository.save(accounts);

        return accountMapper.toResponse(res);
    }

    private int priorityRoles(Role role) {
        switch (role) {
            case VIEN_TRUONG:
                return 10;
            case VIEN_PHO:
                return 9;
            case TRUONG_PHONG:
                return 8;
            case PHO_PHONG:
                return 7;
            case KIEM_SAT_VIEN:
                return 0;
            default:
                return 0;
        }
    }

    @Override
    public ObjectSwapResponse swapStatus(Long newAccountId, Long oldAccountId) throws ApiException {
        return swap(newAccountId, oldAccountId);
    }

    private ObjectSwapResponse swap(Long newAccountId, Long oldAccountId) throws ApiException {
        Accounts existingAccount =
                accountRepository.findById(newAccountId).orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
        if (!existingAccount.getRoles().getCode().equals(Role.VIEN_TRUONG.name())
                || !existingAccount.getRoles().getCode().equals(Role.TRUONG_PHONG.name())) return null;
        Long departmentId = existingAccount.getDepartments().getId();
        String roleCode = existingAccount.getRoles().getCode();
        Optional<Accounts> accountLeadOptional = accountRepository.findByDepartment(departmentId, roleCode, "ACTIVE");
        if (accountLeadOptional.isEmpty()) return null;
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

    private boolean isAllowedToCreateAccount(Accounts curLoginAcc) {
        List<String> roleAccepts = List.of(VIEN_TRUONG, VIEN_PHO);
        if (roleAccepts.contains(curLoginAcc.getRoles().getCode())) {
            return false;
        }

        Departments curLoginDepartment = departmentRepository.findByAccountId(curLoginAcc.getId());
        return !Objects.equals(curLoginDepartment.getCode(), DepartmentCode.PB_KY_THUAT.name());
    }

    private Map<String, String> validateAccountCreateRequest(AccountCreateRequest request) {
        Map<String, String> errors = new HashMap<>();

        validateUsername(request.getUsername(), errors);
        validateRole(request.getRoleId(), request.getRoleName(), errors);
        validateDepartment(request.getDepartmentId(), request.getDepartmentName(), errors);
        validateOrganization(request.getOrganizationId(), request.getOrganizationName(), errors);
        errors.putAll(validateAvatarFile(request.getAvatar()));

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
        Profiles profile = Profiles.builder()
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .build();

        String avatarUrl = saveAvatar(request.getAvatar());
        if (Objects.nonNull(avatarUrl)) {
            profile.setAvatar(avatarUrl);
        }

        return profile;
    }

    private Accounts createAccount(AccountCreateRequest request, Profiles profile) {
        return Accounts.builder()
                .username(request.getUsername())
                .roles(roleRepository.findById(request.getRoleId()).orElseThrow())
                .departments(
                        departmentRepository.findById(request.getDepartmentId()).orElseThrow())
                .password(passwordEncoder.encode(request.getPassword()))
                .profile(profile)
                .build();
    }

    private String saveAvatar(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            Path uploadPath = Files.createDirectories(Paths.get(uploadDir));
            String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return AVATAR_URI + uniqueFileName;
        } catch (IOException e) {
            log.error("Could not save avatar", e);
            return null;
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        String fileExtension = getFileExtension(originalFileName);
        return UUID.randomUUID() + fileExtension;
    }

    private Map<String, String> validateAvatarFile(MultipartFile file) {
        Map<String, String> errors = new HashMap<>();
        if (file == null || file.isEmpty()) {
            return errors;
        }

        List<String> errorMessages = new ArrayList<>();

        if (file.getSize() > MAX_AVATAR_SIZE * BYTES_IN_MB) {
            errorMessages.add(MessageFormat.format(AVATAR_MAX_SIZE, MAX_AVATAR_SIZE));
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!isAllowedExtension(fileExtension, AVATAR_ALLOWED_EXTENSIONS)) {
            errorMessages.add(
                    MessageFormat.format(AVATAR_EXTENSION_INVALID, String.join(", ", AVATAR_ALLOWED_EXTENSIONS)));
        }

        if (!errorMessages.isEmpty()) {
            errors.put("avatar", String.join("\n", errorMessages));
        }

        return errors;
    }
}

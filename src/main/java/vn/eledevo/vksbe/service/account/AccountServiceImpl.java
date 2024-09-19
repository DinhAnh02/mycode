package vn.eledevo.vksbe.service.account;

import static vn.eledevo.vksbe.constant.ErrorCode.*;
import static vn.eledevo.vksbe.utils.SecurityUtils.getUserName;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.RoleCodes;
import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.model.account.AccountInfo;
import vn.eledevo.vksbe.dto.request.AccountInactive;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter;
import vn.eledevo.vksbe.dto.response.account.Result;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.dto.response.usb.UsbResponse;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.entity.Computers;
import vn.eledevo.vksbe.entity.Usbs;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.mapper.AccountMapper;
import vn.eledevo.vksbe.mapper.ComputerMapper;
import vn.eledevo.vksbe.mapper.UsbMapper;
import vn.eledevo.vksbe.repository.AccountRepository;
import vn.eledevo.vksbe.repository.ComputerRepository;
import vn.eledevo.vksbe.repository.TokenRepository;
import vn.eledevo.vksbe.repository.UsbRepository;
import vn.eledevo.vksbe.utils.SecurityUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    TokenRepository tokenRepository;
    AccountMapper accountMapper;
    PasswordEncoder passwordEncoder;
    ComputerRepository computerRepository;
    ComputerMapper computerMapper;
    UsbRepository usbRepository;
    UsbMapper usbMapper;

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
    public AccountResponse resetPassword(Long id) throws ApiException {
        Accounts accounts = validAccount(id);
        accounts.setPassword(passwordEncoder.encode(accounts.getUsername()));
        accounts.setPin(null);
        accounts.setIsConditionLogin1(false);
        accounts.setIsConditionLogin2(false);
        accounts.setUpdateBy(getUserName());
        accounts.setUpdateAt(LocalDateTime.now());
        Accounts res = accountRepository.save(accounts);
        tokenRepository.deleteByAccounts_Id(id);
        return accountMapper.toResponse(res);
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
     *  Role đăng nhập là "VIEN_TRUONG"
     *  Role đăng nhập là "IT_ADMIN"
     *  Role đăng nhập là "VIEN_PHO" và role xem chi tiết không phải "VIEN_TRUONG" hoặc "VIEN_PHO"
     *  Cùng phòng ban, role đăng nhập là "TRUONG_PHONG" và role xem chi tiết không phải "TRUONG_PHONG"
     *  Cùng phòng ban, role đăng nhập là "PHO_PHONG" và role xem chi tiết là "KIEM_SAT_VIEN"
     * --> isReadOnly = false
     * @param acc Thông tin tài khoản đang đăng nhập
     * @param account Thông tin tài khoản được xem chi tiết
     * @return boolean true: không hiển thị nút nào, false: hiển thị nút kích hoạt/khoá
     */
    private static boolean isReadOnly(AccountInfo acc, Accounts account) {
        String roleCodeLogin = acc.getRoleCode();
        String roleCodeDetail = account.getRoles().getCode();
        Long departmentLogin = acc.getDepartmentId();
        Long departmentDetail = account.getDepartments().getId();

        return !(RoleCodes.VIEN_TRUONG.equals(roleCodeLogin)
                || RoleCodes.IT_ADMIN.equals(roleCodeLogin)
                || (RoleCodes.VIEN_PHO.equals(roleCodeLogin)
                        && !RoleCodes.VIEN_TRUONG.equals(roleCodeDetail)
                        && !RoleCodes.VIEN_PHO.equals(roleCodeDetail))
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
    public ApiResponse<UsbResponse> getUsbInfo(Long id) throws ApiException {
        validAccount(id);
        Optional<Usbs> usbEntities = usbRepository.findByAccounts_Id(id);
        if (usbEntities.isPresent()) {
            return ApiResponse.ok(usbMapper.toTarget(usbEntities.get()));
        } else {
            throw new ApiException(ACCOUNT_NOT_CONNECT_USB);
        }
    }
}

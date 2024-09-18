package vn.eledevo.vksbe.service.account;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.model.account.AccountInfo;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter;
import vn.eledevo.vksbe.dto.response.account.Result;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.entity.Computers;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.mapper.AccountMapper;
import vn.eledevo.vksbe.mapper.ComputerMapper;
import vn.eledevo.vksbe.repository.AccountRepository;
import vn.eledevo.vksbe.repository.ComputerRepository;
import vn.eledevo.vksbe.repository.TokenRepository;

import java.time.LocalDateTime;
import java.util.List;

import static vn.eledevo.vksbe.constant.ErrorCode.*;
import static vn.eledevo.vksbe.utils.SecurityUtils.getUserName;

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
    public ApiResponse getListAccountByFilter(AccountRequest accountRequest, Integer currentPage, Integer limit)
            throws ApiException {
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
            Page<AccountResponseByFilter> page = accountRepository.getAccountList(accountRequest, pageable);
            List<AccountResponseByFilter> accountResponseByFilterList = page.getContent().stream()
                    .peek(ele -> {
                        ele.setOrganizationId(accountRequest.getOrganizationId());
                        ele.setOrganizationName(accountRequest.getOrganizationName());
                    })
                    .toList();
            Result<AccountResponseByFilter> result = new Result<>(accountResponseByFilterList, page.getTotalElements());
            return new ApiResponse<>(2000, "OK", result);
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
                    .isDisplayAllButton("IT_ADMIN".equals(acc.getRoleCode()))
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
     * Kiểm tra điều kiện hiển thị nút kích hoạt/khoá với role khác IT_ADMIN
     * Dieu kien hien thi la:
     * Role đăng nhập là "VIEN_TRUONG"
     * Role đăng nhập là "VIEN_PHO" và role xem chi tiết không phải "VIEN_TRUONG" hoặc "VIEN_PHO"
     * Cùng phòng ban, role đăng nhập là "TRUONG_PHONG" và role xem chi tiết không phải "TRUONG_PHONG"
     * Cùng phòng ban, role đăng nhập là "PHO_PHONG" và role xem chi tiết là "KIEM_SAT_VIEN"
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

        return !("VIEN_TRUONG".equals(roleCodeLogin)
                || ("VIEN_PHO".equals(roleCodeLogin)
                && !"VIEN_TRUONG".equals(roleCodeDetail)
                && !"VIEN_PHO".equals(roleCodeDetail))
                || (departmentLogin.equals(departmentDetail)
                && "TRUONG_PHONG".equals(roleCodeLogin)
                && !"TRUONG_PHONG".equals(roleCodeDetail))
                || (departmentLogin.equals(departmentDetail)
                && "PHO_PHONG".equals(roleCodeLogin)
                && "KIEM_SAT_VIEN".equals(roleCodeDetail)));
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
        List<Computers> res = computerRepository.findByAccountId(accountId);
        return computerMapper.toListResponse(res);
    }

}

package vn.eledevo.vksbe.service.account;

import static vn.eledevo.vksbe.constant.ErrorCode.ACCOUNT_NOT_FOUND;
import static vn.eledevo.vksbe.utils.SecurityUtils.getUserName;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.mapper.AccountMapper;
import vn.eledevo.vksbe.repository.AccountRepository;
import vn.eledevo.vksbe.repository.TokenRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    TokenRepository tokenRepository;
    AccountMapper accountMapper;
    PasswordEncoder passwordEncoder;

    private Accounts validAccount(Long id) throws ApiException {
        return accountRepository.findById(id).orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
    }

    /**
     * Đặt lại mật khẩu cho tài khoản được chỉ định.
     *
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
}

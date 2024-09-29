package vn.eledevo.vksbe.service.authenticate;

import static vn.eledevo.vksbe.constant.ErrorCode.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.config.security.JwtService;
import vn.eledevo.vksbe.constant.*;
import vn.eledevo.vksbe.dto.request.AuthenticationRequest;
import vn.eledevo.vksbe.dto.request.ChangePasswordRequest;
import vn.eledevo.vksbe.dto.request.TwoFactorAuthenticationRequest;
import vn.eledevo.vksbe.dto.request.account.CreateAccountTest;
import vn.eledevo.vksbe.dto.request.pinRequest;
import vn.eledevo.vksbe.dto.response.AuthenticationResponse;
import vn.eledevo.vksbe.dto.response.Token2FAResponse;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.entity.AuthTokens;
import vn.eledevo.vksbe.entity.Computers;
import vn.eledevo.vksbe.entity.Usbs;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.AccountRepository;
import vn.eledevo.vksbe.repository.ComputerRepository;
import vn.eledevo.vksbe.repository.TokenRepository;
import vn.eledevo.vksbe.repository.UsbRepository;
import vn.eledevo.vksbe.service.ChangeData;
import vn.eledevo.vksbe.utils.SecurityUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationService {
    final AccountRepository accountRepository;
    final TokenRepository tokenRepository;
    final UsbRepository usbRepository;
    final ComputerRepository computerRepository;
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;
    final AuthenticationManager authenticationManager;

    /**
     * Đăng ký người dùng mới vào hệ thống.
     *
     * @param request Đối tượng RegisterRequest chứa thông tin đăng ký
     * @return Đối tượng AuthenticationResponse chứa token truy cập và token làm mới
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ApiException {
        // Xác thực thông tin đăng nhập của người dùng
        try {
            var account = accountRepository
                    .findAccountInSystem(request.getUsername())
                    .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_EXIST));
            // Xác thực thông tin đăng nhập của người dùng
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            if (!account.getStatus().equals(Status.ACTIVE.name())) {
                throw new ApiException(ErrorCode.CHECK_ACTIVE_ACCOUNT);
            }
            Boolean isCheck = checkRoleItAdmin(account.getRoles().getCode());
            if (Boolean.FALSE.equals(isCheck)) {
                checkComputerForAccount(request.getCurrentDeviceId(), account.getId());
            }
            Optional<Usbs> universalSerialBus = usbRepository.findByAccounts_Id(account.getId());
            if (universalSerialBus.isEmpty()) {
                throw new ApiException(ACCOUNT_NOT_CONNECT_USB);
            }
            var jwtToken = jwtService.generateToken(
                    account,
                    UUID.fromString(universalSerialBus.get().getKeyUsb()),
                    account.getRoles().getCode());
            // Hủy tất cả các token hiện có của người dùng
            revokeAllUserTokens(account);
            // Lưu token truy cập mới vào cơ sở dữ liệu
            saveUserToken(account, jwtToken, TokenType.ACCESS.toString());
            // Trả về đối tượng AuthenticationResponse chứa các token
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .usbCode(universalSerialBus.get().getUsbCode())
                    .usbVendorCode(universalSerialBus.get().getUsbVendorCode())
                    .build();
        } catch (BadCredentialsException e) {
            throw new ApiException(ErrorCode.PASSWORD_FAILURE);
        }
    }

    /**
     * Lưu token truy cập cho người dùng vào cơ sở dữ liệu.
     *
     * @param account  Đối tượng User
     * @param jwtToken Token truy cập JWT
     */
    private void saveUserToken(Accounts account, String jwtToken, String type) {

        // Tạo đối tượng Token mới
        var token = AuthTokens.builder()
                .accounts(account)
                .token(jwtToken)
                .tokenType(type)
                .isExpiredTime(false)
                .build();

        // Lưu đối tượng Token vào cơ sở dữ liệu
        tokenRepository.save(token);
    }

    /**
     * Hủy tất cả các token hiện có của người dùng.
     *
     * @param account Đối tượng User
     */
    private void revokeAllUserTokens(Accounts account) {
        // Tìm tất cả các token hợp lệ của người dùng
        var validUserTokens = tokenRepository.findAllValidTokenByUser(account.getId());
        if (validUserTokens.isEmpty()) return;
        // Đánh dấu các token đó là hết hạn và bị hủy
        validUserTokens.forEach(token -> tokenRepository.deleteById(token.getId()));
    }

    public String createPin(pinRequest pinRequest) throws ApiException {
        String username = SecurityUtils.getUserName();
        Accounts account =
                accountRepository.findAccountInSystem(username).orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
        if (!account.getStatus().equals(Status.ACTIVE.name())) {
            throw new ApiException(ACCOUNT_NOT_STATUS_ACTIVE);
        }
        if (Boolean.FALSE.equals(account.getIsConditionLogin2())) {
            return null;
        }
        if (!pinRequest.getPin().equals(pinRequest.getPin2())) {
            throw new ApiException(ErrorCode.ACCOUNT_NOT_PIN);
        }
        String hashedPin = passwordEncoder.encode(pinRequest.getPin2());
        account.setPin(hashedPin);
        account.setIsConditionLogin2(Boolean.FALSE);
        accountRepository.save(account);

        return ResponseMessage.CREATE_PIN_SUCCESS;
    }

    public String changePassword(ChangePasswordRequest request) throws ApiException {
        String userName = SecurityUtils.getUserName();
        Accounts accountRequest =
                accountRepository.findAccountInSystem(userName).orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
        if (!accountRequest.getStatus().equals(Status.ACTIVE.name())) {
            throw new ApiException(ACCOUNT_NOT_STATUS_ACTIVE);
        }
        if (Boolean.FALSE.equals(accountRequest.getIsConditionLogin1())) {
            return null;
        }
        if (!passwordEncoder.matches(request.getOldPassword(), accountRequest.getPassword())) {
            throw new ApiException(OLD_PASSWORD_FAILURE);
        }
        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new ApiException(NEW_PASSWORD_FAILURE);
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ApiException(CONFIRM_PASSWORD_FAILURE);
        }
        accountRequest.setPassword(passwordEncoder.encode(request.getConfirmPassword()));
        accountRequest.setIsConditionLogin1(Boolean.FALSE);
        accountRepository.save(accountRequest);
        return ResponseMessage.CHANGE_PASSWORD_SUCCESS;
    }

    public AuthenticationResponse twoFactorAuthenticationRequest(TwoFactorAuthenticationRequest request)
            throws Exception {
        String employeeCode = SecurityUtils.getUserName();
        Token2FAResponse responseTokenUsb = ChangeData.decrypt(request.getTokenUsb(), Token2FAResponse.class);
        if (responseTokenUsb.getExpiredTime() > System.currentTimeMillis()) {
            throw new ApiException(ErrorCode.TOKEN_EXPIRE);
        }
        Optional<Accounts> accounts = accountRepository.findAccountInSystem(employeeCode);
        if (accounts.isEmpty()) {
            throw new ApiException(ErrorCode.USER_NOT_EXIST);
        }
        if (!accounts.get().getStatus().equals("ACTIVE")) {
            throw new ApiException(CHECK_ACTIVE_ACCOUNT);
        }
        Optional<Usbs> usbToken =
                usbRepository.usbByAccountAndConnect(accounts.get().getId());
        if (usbToken.isEmpty()) {
            throw new ApiException(ErrorCode.CONFLICT_USB);
        }
        if (!request.getCurrentUsbCode().equals(responseTokenUsb.getHasString().getUsbCode())) {
            throw new ApiException(ErrorCode.CHECK_USB);
        }
        if (!request.getCurrentUsbCode().equals(usbToken.get().getUsbCode())) {
            throw new ApiException(ErrorCode.CHECK_USB);
        }
        if (!request.getCurrentUsbVendorCode()
                .equals(responseTokenUsb.getHasString().getUsbVendorCode())) {
            throw new ApiException(ErrorCode.CHECK_USB);
        }
        if (!request.getCurrentUsbVendorCode().equals(usbToken.get().getUsbVendorCode())) {
            throw new ApiException(ErrorCode.CHECK_USB);
        }
        Boolean isCheck = checkRoleItAdmin(accounts.get().getRoles().getCode());
        if (Boolean.FALSE.equals(isCheck)) {
            checkComputerForAccount(request.getCurrentDeviceId(), accounts.get().getId());
        }
        var jwtToken = jwtService.generateToken(
                accounts.get(),
                UUID.fromString(usbToken.get().getKeyUsb()),
                accounts.get().getRoles().getCode());
        // Hủy tất cả các token hiện có của người dùng
        revokeAllUserTokens(accounts.get());
        // Lưu token truy cập mới vào cơ sở dữ liệu
        saveUserToken(accounts.get(), jwtToken, TokenType.ACCESS.toString());
        // Trả về đối tượng AuthenticationResponse chứa các token
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }

    private Boolean checkRoleItAdmin(String role) {
        return role.equals(Role.IT_ADMIN.toString());
    }

    private void checkComputerForAccount(String deviceCode, Long accountId) throws ApiException {
        Boolean isValid = computerRepository.existsByCode(deviceCode);
        if (Boolean.FALSE.equals(isValid)) {
            throw new ApiException(ErrorCode.COMPUTER_NOT_FOUND);
        }
        List<Computers> computersList = computerRepository.findByAccounts_Id(accountId);
        boolean deviceExists =
                computersList.stream().anyMatch(computer -> computer.getCode().equals(deviceCode));
        if (Boolean.FALSE.equals(deviceExists)) {
            throw new ApiException(ErrorCode.COMPUTER_NOT_CONNECT_TO_ACCOUNT);
        }
    }

    public CreateAccountTest createAccountTest(CreateAccountTest createAccountTest) {
        createAccountTest.setPassword(passwordEncoder.encode(createAccountTest.getPassword()));
        createAccountTest.setPinCode(passwordEncoder.encode(createAccountTest.getPinCode()));
        return createAccountTest;
    }
}

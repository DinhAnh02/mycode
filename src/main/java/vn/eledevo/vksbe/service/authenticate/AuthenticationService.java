package vn.eledevo.vksbe.service.authenticate;

import static vn.eledevo.vksbe.constant.ErrorCode.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.config.security.JwtService;
import vn.eledevo.vksbe.constant.ErrorCode;
import vn.eledevo.vksbe.constant.ResponseMessage;
import vn.eledevo.vksbe.constant.Role;
import vn.eledevo.vksbe.constant.TokenType;
import vn.eledevo.vksbe.dto.request.AuthenticationRequest;
import vn.eledevo.vksbe.dto.request.ChangePasswordRequest;
import vn.eledevo.vksbe.dto.request.pinRequest;
import vn.eledevo.vksbe.dto.response.AuthenticationResponse;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.entity.AuthTokens;
import vn.eledevo.vksbe.entity.Computers;
import vn.eledevo.vksbe.entity.Usbs;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.AccountRepository;
import vn.eledevo.vksbe.repository.ComputerRepository;
import vn.eledevo.vksbe.repository.TokenRepository;
import vn.eledevo.vksbe.repository.UsbRepository;
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
    static final String ALGORITHM = "AES";
    static final String PADDING = "PKCS5Padding";
    static final String keyBase64 = "cjFUazVkUHF0dXJRb1BhYmVnY0h5QnFnNFRBRVpDTm0=";
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
                    .findByUsernameAndActive(request.getUsername())
                    .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_EXIST));
            // Xác thực thông tin đăng nhập của người dùng
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            if (!account.getRoles().getCode().equals(Role.IT_ADMIN.toString())) {
                Boolean isValid = computerRepository.existsByCode(request.getCurrentDeviceId());
                if (Boolean.FALSE.equals(isValid)) {
                    throw new ApiException(ErrorCode.COMPUTER_NOT_FOUND);
                }
                List<Computers> computersList = computerRepository.findByAccounts_Id(account.getId());
                boolean deviceExists = computersList.stream()
                        .anyMatch(computer -> computer.getCode().equals(request.getCurrentDeviceId()));
                if (Boolean.FALSE.equals(deviceExists)) {
                    throw new ApiException(ErrorCode.COMPUTER_NOT_CONNECT_TO_ACCOUNT);
                }
            }
            Optional<Usbs> usbs = usbRepository.findByAccounts_Id(account.getId());
            if (usbs.isEmpty()) {
                throw new ApiException(ErrorCode.EX_NOT_FOUND);
            }
            var jwtToken =
                    jwtService.generateToken(account, UUID.fromString(usbs.get().getKeyUsb()));
            // Hủy tất cả các token hiện có của người dùng
            revokeAllUserTokens(account);
            // Lưu token truy cập mới vào cơ sở dữ liệu
            saveUserToken(account, jwtToken, TokenType.ACCESS.toString());
            // Trả về đối tượng AuthenticationResponse chứa các token
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .usbCode(usbs.get().getUsbCode())
                    .usbVendorCode(usbs.get().getUsbVendorCode())
                    .build();
        } catch (BadCredentialsException e) {
            throw new ApiException(ErrorCode.PASSWORD_FAILURE);
        }
    }

    /**
     * Lưu token truy cập cho người dùng vào cơ sở dữ liệu.
     *
     * @param account     Đối tượng User
     * @param jwtToken Token truy cập JWT
     */
    private void saveUserToken(Accounts account, String jwtToken, String type) {

        // Tạo đối tượng Token mới
        var token = AuthTokens.builder()
                .accounts(account)
                .token(jwtToken)
                .tokenType(type)
                .isExpireTime(false)
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

    public static String decrypt(String keyUsb) {
        try {
            byte[] keys = generateKey(keyBase64); // Sử dụng khoá base64 thực tế của bạn
            SecretKeySpec secretKeySpec = new SecretKeySpec(keys, ALGORITHM);

            Cipher cipher = Cipher.getInstance(ALGORITHM + "/ECB/" + PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(keyUsb));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] generateKey(String keyBase64) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] digest = sha.digest(keyBase64.getBytes(StandardCharsets.UTF_8));
        return digest;
    }

    //    public AuthenticationResponse twoFactorAuthentication(TwoFactorAuthenticationRequest request)
    //            throws ApiException, JsonProcessingException {
    //        String username = jwtService.extractUsername(request.getTokenUsb());
    //        Optional<User> userInfo = repository.findByUsernameAndIsDeletedFalse(username);
    //        if (userInfo.isEmpty()) {
    //            throw new ApiException(ErrorCode.USER_NOT_EXIST);
    //        }
    //        if (!username.equals(userInfo.get().getUsername())) {
    //            throw new ApiException(ErrorCode.CHECK_USB);
    //        }
    //        String data = decrypt(request.getKeyUsb());
    //        ObjectMapper objectMapper = new ObjectMapper();
    //        TwoFactorAuthenticationResponse responseUsb =
    //                objectMapper.readValue(data, TwoFactorAuthenticationResponse.class);
    //        Optional<UserDeviceInfoKeyQuery> userDeviceInfoKey =
    // userDeviceInfoKeyRepository.findUserDeviceInfoKeyByUserId(
    //                userInfo.get().getId());
    //        if (userDeviceInfoKey.isEmpty()) {
    //            throw new ApiException(ErrorCode.EX_NOT_FOUND);
    //        }
    //        if (!responseUsb.getDeviceUuid().equals(request.getCurrentDeviceUuid())
    //                || !request.getCurrentDeviceUuid()
    //                        .equals(userDeviceInfoKey.get().getDeviceUuid())) {
    //            throw new ApiException(ErrorCode.CHECK_USB);
    //        }
    //        if (!responseUsb.getKeyUsb().equals(userDeviceInfoKey.get().getKeyUsb())) {
    //            throw new ApiException(ErrorCode.CHECK_USB);
    //        }
    //        var jwtToken = jwtService.generateToken(userInfo.get());
    //        // Hủy tất cả các token hiện có của người dùng
    //        revokeAllUserTokens(userInfo.get());
    //        // Lưu token truy cập mới vào cơ sở dữ liệu
    //        saveUserToken(userInfo.get(), jwtToken);
    //        // Trả về đối tượng AuthenticationResponse chứa các token
    //        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    //    }

    public String createPin(pinRequest pinRequest) throws ApiException {
        String username = SecurityUtils.getUserName();
        Accounts account = accountRepository
                .findByUsernameAndActive(username)
                .orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
        if (!account.getStatus().equals("ACTIVE")) {
            throw new ApiException(ACCOUNT_NOT_STATUS_ACTIVE);
        }
        if (!Boolean.FALSE.equals(account.getIsConditionLogin2())) {
            return null;
        }
        if (!pinRequest.getPin().equals(pinRequest.getPin2())) {
            throw new ApiException(ErrorCode.ACCOUNT_NOT_PIN);
        }
        String hashedPin = passwordEncoder.encode(pinRequest.getPin2());
        account.setPin(hashedPin);
        account.setIsConditionLogin2(Boolean.TRUE);
        accountRepository.save(account);

        return ResponseMessage.CREATE_PIN_SUCCESS;
    }

    public String changePassword(ChangePasswordRequest request) throws ApiException {
        String userName = SecurityUtils.getUserName();
        Accounts accountRequest = accountRepository
                .findByUsernameAndActive(userName)
                .orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
        if (!accountRequest.getStatus().equals("ACTIVE")) {
            throw new ApiException(ACCOUNT_NOT_STATUS_ACTIVE);
        }
        if (!Boolean.FALSE.equals(accountRequest.getIsConditionLogin1())) {
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
        accountRequest.setIsConditionLogin1(Boolean.TRUE);
        accountRepository.save(accountRequest);
        return ResponseMessage.CHANGE_PASSWORD_SUCCESS;
    }
}

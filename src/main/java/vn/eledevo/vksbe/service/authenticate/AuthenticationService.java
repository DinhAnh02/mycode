package vn.eledevo.vksbe.service.authenticate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
import vn.eledevo.vksbe.constant.TokenType;
import vn.eledevo.vksbe.dto.request.AuthenticationRequest;
import vn.eledevo.vksbe.dto.response.AuthenticationResponse;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.entity.AuthTokens;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.AccountRepository;
import vn.eledevo.vksbe.repository.TokenRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationService {
    final AccountRepository accountRepository;
    final TokenRepository tokenRepository;
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
            var jwtToken = jwtService.generateToken(account);
            // Hủy tất cả các token hiện có của người dùng
            revokeAllUserTokens(account);
            // Lưu token truy cập mới vào cơ sở dữ liệu
            saveUserToken(account, jwtToken, TokenType.ACCESS.toString());
            // Trả về đối tượng AuthenticationResponse chứa các token
            return AuthenticationResponse.builder().accessToken(jwtToken).build();
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
        validUserTokens.forEach(token -> {
            tokenRepository.deleteById(token.getId());
        });
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
}

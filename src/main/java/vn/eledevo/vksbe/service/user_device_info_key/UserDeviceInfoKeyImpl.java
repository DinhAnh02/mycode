package vn.eledevo.vksbe.service.user_device_info_key;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ErrorCode;
import vn.eledevo.vksbe.dto.request.UserDeviceInfoKeyRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.UserDeviceInfoKeyResponse;
import vn.eledevo.vksbe.entity.DeviceInfo;
import vn.eledevo.vksbe.entity.UserDeviceInfoKey;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.mapper.UserDeviceInfoKeyMapper;
import vn.eledevo.vksbe.repository.DeviceInfoRepository;
import vn.eledevo.vksbe.repository.UserDeviceInfoKeyRepository;
import vn.eledevo.vksbe.repository.UserRepository;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserDeviceInfoKeyImpl implements UserDeviceInfoKeyService {
    UserDeviceInfoKeyRepository userDeviceInfoKeyRepository;
    UserDeviceInfoKeyMapper userDeviceInfoKeyMapper;
    DeviceInfoRepository deviceInfoRepository;
    UserRepository userRepository;
    static final String ALGORITHM = "AES";
    static final String PADDING = "PKCS5Padding";
    static final String keyBase64 = "cjFUazVkUHF0dXJRb1BhYmVnY0h5QnFnNFRBRVpDTm0=";

    @Override
    public UserDeviceInfoKeyResponse addConnection(UserDeviceInfoKeyRequest userDeviceInfoKeyRequest)
            throws ApiException {
        if (!userRepository.existsById(userDeviceInfoKeyRequest.getUserId())) {
            throw new ApiException(ErrorCode.USER_NOT_EXIST);
        }
        Optional<DeviceInfo> deviceInfo = deviceInfoRepository.findById(userDeviceInfoKeyRequest.getDeviceInfoId());
        if (deviceInfo.isEmpty()) {
            throw new ApiException(ErrorCode.DEVICE_NOT_EXIST);
        }
        if (userDeviceInfoKeyRepository
                .findByUserId(userDeviceInfoKeyRequest.getUserId())
                .isPresent()) {
            throw new ApiException(ErrorCode.RECORD_EXIST);
        }
        var userDeviceInfoKey = UserDeviceInfoKey.builder()
                .deviceInfoId(userDeviceInfoKeyRequest.getDeviceInfoId())
                .userId(userDeviceInfoKeyRequest.getUserId())
                .build();
        deviceInfo.get().setStatus("Connected");
        deviceInfoRepository.save(deviceInfo.get());
        return userDeviceInfoKeyMapper.toResponse(userDeviceInfoKeyRepository.save(userDeviceInfoKey));
    }

    @Override
    public ApiResponse removeConnection(Long id) throws ApiException {
        Optional<UserDeviceInfoKey> userDeviceInfoKey = userDeviceInfoKeyRepository.findById(id);
        if (userDeviceInfoKey.isEmpty()) {
            throw new ApiException(ErrorCode.EX_NOT_FOUND);
        }
        userDeviceInfoKeyRepository.deleteById(id);
        Optional<DeviceInfo> deviceInfo =
                deviceInfoRepository.findById(userDeviceInfoKey.get().getDeviceInfoId());
        if (deviceInfo.isEmpty()) {
            throw new ApiException(ErrorCode.DEVICE_NOT_EXIST);
        }
        deviceInfo.get().setStatus("Disconnect");
        deviceInfoRepository.save(deviceInfo.get());
        return new ApiResponse(200, "Hủy kết nối thành công");
    }

    @Override
    public String createKeyUsb(Long id) throws ApiException {
        Optional<UserDeviceInfoKey> userDeviceInfoKey =
                userDeviceInfoKeyRepository.findById(id);
        if(userDeviceInfoKey.isEmpty()){
            throw new ApiException(ErrorCode.EX_NOT_FOUND);
        }
        UUID uuid = UUID.randomUUID();
        userDeviceInfoKey.get().setKeyUsb(uuid.toString());
        userDeviceInfoKeyRepository.save(userDeviceInfoKey.get());
        return uuid.toString();
    }

    public static String decrypt(String encryptedText) {
        try {
            byte[] keys = generateKey(keyBase64);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keys, ALGORITHM);

            Cipher cipher = Cipher.getInstance(ALGORITHM + "/ECB/" + PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
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

    @Override
    public ApiResponse revokeUsbKey(Long id) throws ApiException {
        Optional<UserDeviceInfoKey> userDeviceInfoKey =
                userDeviceInfoKeyRepository.findById(id);
        if(userDeviceInfoKey.isEmpty()){
            throw new ApiException(ErrorCode.EX_NOT_FOUND);
        }
        String keyUsb = userDeviceInfoKey.get().getKeyUsb();
        if(keyUsb == null || keyUsb.isBlank()){
            throw new ApiException(ErrorCode.KEY_USB_NOT_FOUND);
        }
        userDeviceInfoKey.get().setKeyUsb(null);
        userDeviceInfoKeyRepository.save(userDeviceInfoKey.get());
        return new ApiResponse(200,"Thu hồi thành công");
    }
}

package vn.eledevo.vksbe.service.user_device_info_key;

import java.util.Optional;

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
        deviceInfo.get().setStatus("Disconnect");
        deviceInfoRepository.save(deviceInfo.get());
        return new ApiResponse(200, "Hủy kết nối thành công");
    }
}

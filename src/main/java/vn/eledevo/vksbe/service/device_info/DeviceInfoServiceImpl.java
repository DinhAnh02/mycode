package vn.eledevo.vksbe.service.device_info;

import static vn.eledevo.vksbe.constant.ErrorCode.EX_NOT_FOUND;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ResponseMessage;
import vn.eledevo.vksbe.dto.request.DeviceInfoRequest;
import vn.eledevo.vksbe.dto.response.DeviceInfoResponse;
import vn.eledevo.vksbe.entity.DeviceInfo;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.mapper.DeviceInfoMapper;
import vn.eledevo.vksbe.repository.DeviceInfoRepository;
import vn.eledevo.vksbe.utils.SecurityUtils;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeviceInfoServiceImpl implements DeviceInfoService {
    DeviceInfoRepository deviceInfoRepository;
    DeviceInfoMapper deviceInfoMapper;

    @Override
    public DeviceInfoResponse createDevice(DeviceInfoRequest deviceInfoRequest) throws ValidationException {
        if (deviceInfoRepository.existsByName(deviceInfoRequest.getName())) {
            throw new ValidationException("name", ResponseMessage.DEVICE_EXIST);
        }
        DeviceInfo deviceInfo = deviceInfoMapper.toEntity(deviceInfoRequest);
        deviceInfo.setCreatedBy(SecurityUtils.getUserId());
        deviceInfo.setUpdatedBy(SecurityUtils.getUserId());
        DeviceInfo deviceInfoResult = deviceInfoRepository.save(deviceInfo);
        return deviceInfoMapper.toResponse(deviceInfoResult);
    }

    @Override
    public DeviceInfoResponse deleteDevice(Long idDevice) throws ApiException {
        Optional<DeviceInfo> deviceInfo = deviceInfoRepository.findById(idDevice);
        if (deviceInfo.isEmpty()) {
            throw new ApiException(EX_NOT_FOUND);
        }
        deviceInfo.get().setIsDeleted(true);
        deviceInfoRepository.save(deviceInfo.get());
        return deviceInfoMapper.toResponse(deviceInfo.get());
    }
}

package vn.eledevo.vksbe.service.device_info;

import static vn.eledevo.vksbe.constant.ErrorCode.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.config.DynamicSpecification;
import vn.eledevo.vksbe.dto.request.DeviceInfoRequest;
import vn.eledevo.vksbe.dto.response.DeviceInfoResponse;
import vn.eledevo.vksbe.entity.DeviceInfo;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.mapper.DeviceInfoMapper;
import vn.eledevo.vksbe.repository.DeviceInfoRepository;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeviceInfoServiceImpl implements DeviceInfoService {
    DeviceInfoRepository deviceInfoRepository;
    DeviceInfoMapper deviceInfoMapper;

    @Override
    public DeviceInfoResponse createDevice(DeviceInfoRequest deviceInfoRequest) {
        Optional<DeviceInfo> deviceInfo = deviceInfoRepository.findByDeviceUuid(deviceInfoRequest.getDeviceUuid());
        if (deviceInfo.isPresent()) {
            DeviceInfo deviceInfoEntity = deviceInfoMapper.toEntityUpdate(deviceInfoRequest, deviceInfo.get());
            DeviceInfo deviceInfoUpdate = deviceInfoRepository.save(deviceInfoEntity);
            return deviceInfoMapper.toResponse(deviceInfoUpdate);
        }
        DeviceInfo deviceInfoEntity = deviceInfoMapper.toEntity(deviceInfoRequest);
        DeviceInfo deviceInfoCreate = deviceInfoRepository.save(deviceInfoEntity);
        return deviceInfoMapper.toResponse(deviceInfoCreate);
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

    @Override
    public List<DeviceInfo> searchDevice(Map<String, Object> filters) {
        Specification<DeviceInfo> spec = new DynamicSpecification(filters);
        return deviceInfoRepository.findAll(spec);
    }
}

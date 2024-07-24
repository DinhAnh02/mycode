package vn.eledevo.vksbe.service.device_info;

import java.util.List;
import java.util.Map;

import vn.eledevo.vksbe.dto.request.DeviceInfoRequest;
import vn.eledevo.vksbe.dto.response.DeviceInfoResponse;
import vn.eledevo.vksbe.entity.DeviceInfo;
import vn.eledevo.vksbe.exception.ApiException;

public interface DeviceInfoService {
    DeviceInfoResponse createDevice(DeviceInfoRequest deviceInfoRequest);

    DeviceInfoResponse deleteDevice(Long idDevice) throws ApiException;

    List<DeviceInfo> searchDevice(Map<String, Object> filters);
}

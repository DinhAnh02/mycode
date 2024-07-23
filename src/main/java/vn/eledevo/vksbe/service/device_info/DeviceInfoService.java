package vn.eledevo.vksbe.service.device_info;

import vn.eledevo.vksbe.dto.request.DeviceInfoRequest;
import vn.eledevo.vksbe.dto.response.DeviceInfoResponse;
import vn.eledevo.vksbe.entity.DeviceInfo;
import vn.eledevo.vksbe.exception.ApiException;

import java.util.List;
import java.util.Map;

public interface DeviceInfoService {
    DeviceInfoResponse createDevice(DeviceInfoRequest deviceInfoRequest);

    DeviceInfoResponse deleteDevice(Long idDevice) throws ApiException;

    List<DeviceInfo> searchDevice(Map<String,Object> filters);
}

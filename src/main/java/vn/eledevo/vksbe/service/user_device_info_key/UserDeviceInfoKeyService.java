package vn.eledevo.vksbe.service.user_device_info_key;

import vn.eledevo.vksbe.dto.request.UserDeviceInfoKeyRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.UserDeviceInfoKeyResponse;
import vn.eledevo.vksbe.exception.ApiException;

public interface UserDeviceInfoKeyService {
    UserDeviceInfoKeyResponse addConnection(UserDeviceInfoKeyRequest userDeviceInfoKeyRequest) throws ApiException;

    ApiResponse removeConnection(Long id) throws ApiException;

    String createKeyUsb(Long id) throws ApiException;

    ApiResponse revokeUsbKey(Long id) throws ApiException;
}

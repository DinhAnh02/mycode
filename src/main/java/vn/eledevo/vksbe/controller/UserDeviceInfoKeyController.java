package vn.eledevo.vksbe.controller;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import vn.eledevo.vksbe.dto.request.UserDeviceInfoKeyRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.UserDeviceInfoKeyResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.user_device_info_key.UserDeviceInfoKeyService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserDeviceInfoKeyController {
    private final UserDeviceInfoKeyService userDeviceInfoKeyService;

    @GetMapping("/private/user-device-info-key")
    public ApiResponse<List<UserDeviceInfoKeyResponse>> getListConnection(@RequestParam Map<String,Object> filters){
        return ApiResponse.ok(userDeviceInfoKeyService.getListConnection(filters));
    }

    @PostMapping("/private/user-device-info-key")
    public ApiResponse<UserDeviceInfoKeyResponse> addConnection(
            @RequestBody UserDeviceInfoKeyRequest userDeviceInfoKeyRequest) throws ApiException {
        return ApiResponse.ok(userDeviceInfoKeyService.addConnection(userDeviceInfoKeyRequest));
    }

    @PatchMapping("/private/user-device-info-key/delete/{id}")
    public ApiResponse removeConnection(@PathVariable Long id) throws ApiException {
        return ApiResponse.ok(userDeviceInfoKeyService.removeConnection(id));
    }

    @PatchMapping("/private/user-device-info-key/generate-key-usb/{id}")
    public ApiResponse<String> createKeyUsb(@PathVariable Long id) throws ApiException{
        return ApiResponse.ok(userDeviceInfoKeyService.createKeyUsb(id));
    }

    @PatchMapping("/private/user-device-info-key/remove-key-usb/{id}")
    public ApiResponse revokeKeyUsb (@PathVariable Long id) throws ApiException{
        return ApiResponse.ok(userDeviceInfoKeyService.revokeUsbKey(id));
    }
}

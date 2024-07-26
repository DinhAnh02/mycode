package vn.eledevo.vksbe.controller;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import vn.eledevo.vksbe.dto.request.UserDeviceInfoKeyRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.UserDeviceInfoKeyResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.user_device_info_key.UserDeviceInfoKeyService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserDeviceInfoKeyController {
    private final UserDeviceInfoKeyService userDeviceInfoKeyService;

    @PostMapping("/private/user-device-info-key")
    public ApiResponse<UserDeviceInfoKeyResponse> addConnection(
            @RequestBody UserDeviceInfoKeyRequest userDeviceInfoKeyRequest) throws ApiException {
        return ApiResponse.ok(userDeviceInfoKeyService.addConnection(userDeviceInfoKeyRequest));
    }

    @PatchMapping("/private/user-device-info-key/delete/{id}")
    public ApiResponse removeConnection(@PathVariable Long id) throws ApiException {
        return ApiResponse.ok(userDeviceInfoKeyService.removeConnection(id));
    }
}

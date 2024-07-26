package vn.eledevo.vksbe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ApiResponse<UserDeviceInfoKeyResponse> addConnection (@RequestBody UserDeviceInfoKeyRequest userDeviceInfoKeyRequest) throws ApiException {
        return ApiResponse.ok(userDeviceInfoKeyService.addConnection(userDeviceInfoKeyRequest));
    }
}

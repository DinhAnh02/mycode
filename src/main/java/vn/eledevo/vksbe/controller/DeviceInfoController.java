package vn.eledevo.vksbe.controller;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import vn.eledevo.vksbe.dto.request.DeviceInfoRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.DeviceInfoResponse;
import vn.eledevo.vksbe.entity.DeviceInfo;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.device_info.DeviceInfoService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DeviceInfoController {
    private final DeviceInfoService deviceInfoService;

    @PostMapping("/public/device-info")
    public ApiResponse<DeviceInfoResponse> createUser(@RequestBody @Valid DeviceInfoRequest deviceInfoRequest) {
        return ApiResponse.ok(deviceInfoService.createDevice(deviceInfoRequest));
    }

    @PatchMapping("/private/device-info/{id}")
    public ApiResponse<DeviceInfoResponse> deleteDevice(@PathVariable Long id) throws ApiException {
        return ApiResponse.ok(deviceInfoService.deleteDevice(id));
    }

    @PatchMapping("/search")
    public ApiResponse<List<DeviceInfo>> searchDevice(@RequestParam Map<String, Object> allParams) {
        return ApiResponse.ok(deviceInfoService.searchDevice(allParams));
    }
}

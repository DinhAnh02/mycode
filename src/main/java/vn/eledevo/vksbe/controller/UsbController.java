package vn.eledevo.vksbe.controller;


import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.UsbRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.usb.UsbService;

@RestController
@RequestMapping("/api/v1/private/usbs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Quản lý thiết bị USB")
public class UsbController {
    UsbService usbService;

    @PostMapping("/all")
    @Operation(summary = "Xem danh sách thiết bị USB")
    public ApiResponse<Result> getUsbDeviceList(
            @RequestBody UsbRequest usbRequest,
            @RequestParam(required = false, defaultValue = "1") Integer currentPage,
            @RequestParam(required = false, defaultValue = "10") Integer limit)
            throws ApiException {
        return ApiResponse.ok(usbService.getUsbByFilter(usbRequest, currentPage, limit));
    }
}

package vn.eledevo.vksbe.controller;

import static vn.eledevo.vksbe.constant.ErrorCode.*;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.model.computer.ComputersModel;
import vn.eledevo.vksbe.dto.request.ComputerRequest;
import vn.eledevo.vksbe.dto.request.computer.ComputerRequestForCreate;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.computer.ComputerService;

@RestController
@RequestMapping("/api/v1/private/computers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Quản lý thiết bị máy tính")
public class ComputerController {
    ComputerService computerService;

    @GetMapping("/disconnected")
    @Operation(summary = "Lấy danh sách máy tính không liên kết với tài khoản")
    public ApiResponse<Result> getComputerDisconnectedList(@RequestParam(required = false) String textSearch) {
        return ApiResponse.ok(computerService.getDisconnectedComputers(textSearch));
    }

    @PatchMapping("/update/computer-info/{id}")
    @Operation(summary = "Chỉnh sửa thông tin máy tính")
    public ApiResponse<String> updateComputer(
            @Valid @PathVariable("id") Long id, @RequestBody ComputersModel computerRequest, BindingResult result)
            throws ApiException {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            throw new ApiException(UNCATEGORIZED_EXCEPTION, String.join(", ", errorMessages));
        }
        return ApiResponse.ok(computerService.updateComputer(id, computerRequest));
    }

    @PostMapping("/create/computer-info")
    @Operation(summary = "Tạo mới thiết bị máy tính")
    public ApiResponse<?> createComputer(@Valid @RequestBody ComputerRequestForCreate request) throws ApiException {
        return ApiResponse.ok(computerService.createComputer(request));
    }

    @PostMapping("")
    @Operation(summary = "Xem danh sách thiết bị máy tính")
    public ApiResponse<Result<?>> getComputerList(
            @RequestParam(required = false, defaultValue = "1") Integer currentPage,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestBody ComputerRequest computerRequest)
            throws ApiException {
        return ApiResponse.ok(computerService.getComputerList(computerRequest, currentPage, limit));
    }
}

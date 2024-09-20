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
import vn.eledevo.vksbe.dto.response.PageResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
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
    public ApiResponse<PageResponse<ComputerResponse>> getComputerDisconnectedList(
            @RequestParam(required = false, defaultValue = "1") Integer currentPage,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false) String textSearch) {
        return ApiResponse.ok(computerService.getDisconnectedComputers(currentPage, limit, textSearch));
    }

    @PatchMapping("/update/computer-info/{id}")
    @Operation(summary = "Chỉnh sửa thông tin máy tính")
    public ApiResponse<?> updateComputer(
            @Valid @PathVariable("id") Long id, @RequestBody ComputersModel computerRequest, BindingResult result)
            throws ApiException {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                throw new ApiException(UNCATEGORIZED_EXCEPTION, String.join(", ", errorMessages));
            }
            computerService.updateComputer(id, computerRequest);
            return ApiResponse.ok("Cập nhật thành công");
        } catch (ApiException e) {
            throw new ApiException(UNCATEGORIZED_EXCEPTION);
        }
    }

    @PostMapping("/computer-info")
    public ApiResponse<?> createComputer(@Valid @RequestBody ComputerRequestForCreate request) throws ApiException {
        return ApiResponse.ok(computerService.createComputer(request));
    }

    @GetMapping("")
    public ApiResponse<?> getComputerList(
            @RequestParam(required = false, defaultValue = "1") Long currentPage,
            @RequestParam(required = false, defaultValue = "10") Long limit,
            @RequestBody ComputerRequest computerRequest)
            throws ApiException {
        return ApiResponse.ok(computerService.getComputerList(computerRequest, currentPage, limit));
    }
}

package vn.eledevo.vksbe.controller;

import jakarta.validation.Valid;
import static vn.eledevo.vksbe.constant.ErrorCode.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.model.computer.ComputersModel;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.PageResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.computer.ComputerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/devices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Device Management", description = "Endpoints for managing devices")
public class ComputerController {
    ComputerService computerService;

    @GetMapping("disconnected")
    @Operation(summary = "Get device not link with user")
    public ApiResponse<PageResponse<ComputerResponse>> getComputerDisconnectedList(
            @RequestParam(required = false, defaultValue = "1") Integer currentPage,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false) String textSearch) {
        return ApiResponse.ok(computerService.getDisconnectedComputers(currentPage, limit, textSearch));
    }
    @PatchMapping("/computer-info/{id}")
    public ApiResponse<?> updateComputer(
            @Valid @PathVariable("id") Long id,
            @RequestBody ComputersModel computerRequest,
            BindingResult result) throws ApiException {
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                throw new ApiException(UNCATEGORIZED_EXCEPTION ,String.join(", ", errorMessages));
            }
            computerService.updateComputer(id, computerRequest);
            return ApiResponse.ok("Cập nhật thành công");
        }catch (ApiException e){
          throw new ApiException(UNCATEGORIZED_EXCEPTION);
        }
    }
}

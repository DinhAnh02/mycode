package vn.eledevo.vksbe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.PageResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.service.computer.ComputerService;

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
}

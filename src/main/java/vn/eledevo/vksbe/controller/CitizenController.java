package vn.eledevo.vksbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import vn.eledevo.vksbe.dto.request.citizens.CitizensRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.citizen.CitizenResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.citizen.CitizenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.eledevo.vksbe.dto.response.citizen.CitizenUpdateRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/private/citizens")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Quản lý danh sách công dân")
public class CitizenController {
    CitizenService citizenService;

    @GetMapping("")
    @Operation(summary = "Lấy danh sách công dân")
    public ApiResponse<ResponseFilter<CitizenResponse>> getListCitizen(
            @RequestParam(required = false) String textSearch,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) throws ApiException {
        return ApiResponse.ok(citizenService.getListCitizen(textSearch, page, pageSize));
    }

    @PatchMapping("/{id}/update")
    public ApiResponse<HashMap<String, String>> updateCitizen(@PathVariable Long id,@Valid @RequestBody CitizenUpdateRequest citizenUpdateRequest) throws Exception{
        return ApiResponse.ok(citizenService.updateCitizen(id, citizenUpdateRequest));
    }

    @PostMapping("/create")
    @Operation(summary = "Thêm mới công dân vào hệ thống")
    public ApiResponse<Map<String,String>> createCitizens(@Valid @RequestBody CitizensRequest citizensRequest) throws ApiException {
        return ApiResponse.ok(citizenService.createCitizens(citizensRequest));
    }

}

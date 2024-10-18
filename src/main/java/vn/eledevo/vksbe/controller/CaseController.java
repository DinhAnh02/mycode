package vn.eledevo.vksbe.controller;

import java.util.HashMap;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.cases.CaseCreateRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseUpdateRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.cases.CaseInfomationResponse;
import vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.cases.CaseService;

@RestController
@RequestMapping("/api/v1/private/cases")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Quản lý vụ án")
public class CaseController {
    CaseService caseService;

    @GetMapping("/{id}/case-person/investigator")
    @Operation(summary = "Xem và tìm kiếm danh sách điều tra viên")
    public ApiResponse<ResponseFilter<CitizenCaseResponse>> getAllInvestigatorByCaseId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String textSearch,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize)
            throws ApiException {
        return ApiResponse.ok(caseService.getAllInvestigatorByCaseId(id, textSearch, page, pageSize));
    }

    @GetMapping("/{id}/infomation-detail")
    @Operation(summary = "Xem thông tin chi tiết vụ án")
    public ApiResponse<CaseInfomationResponse> getCaseInfomationDetail(@PathVariable Long id) throws ApiException {
        return ApiResponse.ok(caseService.getCaseInfomationDetail(id));
    }

    @PatchMapping("/{id}/update")
    @Operation(summary = "Chỉnh sửa thông tin vụ án")
    public ApiResponse<HashMap<String, String>> updateCase(
            @PathVariable Long id, @RequestBody CaseUpdateRequest request) throws ApiException {
        return ApiResponse.ok(caseService.updateCase(id, request));
    }

    @PostMapping("/create")
    @Operation(summary = "Tạo mới vụ án")
    public ApiResponse<HashMap<String, Long>> createCase(@Valid @RequestBody CaseCreateRequest caseCreateRequest)
            throws ApiException {
        return ApiResponse.ok(caseService.createCase(caseCreateRequest));
    }

    @GetMapping("/{id}/case-person/suspect-defendant")
    @Operation(summary = "Xem và tìm kiếm danh sách bị can hoặc bị cáo")
    public ApiResponse<ResponseFilter<CitizenCaseResponse>> getAllSuspectDefendantByCaseId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String textSearch,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize)
            throws ApiException {
        return ApiResponse.ok(caseService.getAllSuspectDefendantByCaseId(id, textSearch, page, pageSize));
    }
}

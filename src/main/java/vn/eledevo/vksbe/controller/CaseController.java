package vn.eledevo.vksbe.controller;

import java.util.HashMap;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.cases.CaseCreateRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseFilterRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseUpdateRequest;
import vn.eledevo.vksbe.dto.request.document.DocumentFolderCreationRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseCitizenUpdateRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.cases.CaseFilterResponse;
import vn.eledevo.vksbe.dto.response.cases.CaseInfomationResponse;
import vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.cases.CaseService;
import vn.eledevo.vksbe.dto.response.account.AccountFilterCaseResponse;


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

    @PostMapping("")
    @Operation(summary = "Xem và tìm kiếm danh sách vụ án")
    public ApiResponse<ResponseFilter<CaseFilterResponse>> getCaseByFilter(
            @RequestBody CaseFilterRequest caseFilterRequest,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize)
            throws ApiException {
        return ApiResponse.ok(caseService.getCaseByFilter(caseFilterRequest, page, pageSize));
    }

    @PostMapping("/{caseId}/documents/folder/create")
    @Operation(summary = "Tạo mới thư mục")
    public ApiResponse<HashMap<String, String>> createDocumentFolder(
            @PathVariable Long caseId, @RequestBody @Valid DocumentFolderCreationRequest request) throws ApiException {
        return ApiResponse.ok(caseService.createDocumentFolder(caseId, request));
    }

    @GetMapping("/{id}/account-case/prosecutor")
    @Operation(summary = "Xem và tìm kiếm danh sách kiểm sát viên trong vụ án")
    public ApiResponse<ResponseFilter<AccountFilterCaseResponse>> getCaseProsecutorList(
            @RequestParam @Nullable String textSearch,
            @RequestParam(value = "page", defaultValue = "1") Long page,
            @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize,
            @PathVariable @NotNull Long id) throws ApiException {
        return ApiResponse.ok(caseService.getCaseProsecutorList(textSearch, page, pageSize, id));
    }

    @GetMapping("/{id}/user-in-charge")
    @Operation(summary = "xem và tìm kiếm danh sách lãnh đạo phụ trách")
    public ApiResponse<ResponseFilter<AccountFilterCaseResponse>> getUserInChargeList(
            @RequestParam @javax.annotation.Nullable String textSearch,
            @PathVariable @NotNull Long id,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) throws ApiException {
        return ApiResponse.ok(caseService.getUserInChargeList(textSearch,id,page,pageSize));
    }
    @PatchMapping("/{id}/investigator")
    @Operation(summary = "Chỉnh sửa danh sách điều tra viên")
    public ApiResponse<HashMap<String, String>> updateInvestigatorByCase(
            @PathVariable Long id,
            @RequestBody CaseCitizenUpdateRequest request) throws ApiException {
        return ApiResponse.ok(caseService.updateInvestigator(id, request));
    }
}

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
import vn.eledevo.vksbe.constant.CasePosition;
import vn.eledevo.vksbe.constant.ErrorCodes.TypeCaseCitizen;
import vn.eledevo.vksbe.dto.request.case_flow.CaseFlowCreateRequest;
import vn.eledevo.vksbe.dto.request.case_flow.CaseFlowUpdateRequest;
import vn.eledevo.vksbe.dto.request.cases.*;
import vn.eledevo.vksbe.dto.request.document.DocumentFolderCreationRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseCreateRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseFilterRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseUpdateRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.CaseMindmapTemplateResponse;
import vn.eledevo.vksbe.dto.response.MindmapTemplateResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.case_flow.CaseFlowResponse;
import vn.eledevo.vksbe.dto.response.cases.CaseFilterResponse;
import vn.eledevo.vksbe.dto.response.cases.CaseInfomationResponse;
import vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.account.AccountService;
import vn.eledevo.vksbe.service.case_flow.CaseFlowService;
import vn.eledevo.vksbe.service.cases.CaseService;
import vn.eledevo.vksbe.dto.response.account.AccountFilterCaseResponse;


@RestController
@RequestMapping("/api/v1/private/cases")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Quản lý vụ án")
public class CaseController {
    CaseService caseService;
    AccountService accountService;
    CaseFlowService caseFlowService;

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

    @GetMapping("/accounts")
    @Operation(summary = "Xem và tìm kiếm danh sách lãnh đạo vụ án và kiểm sát viên trong filter vụ án")
    public ApiResponse<ResponseFilter<AccountFilterCaseResponse>> getAccountCaseFilter(
            @RequestParam String textSearch,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize
    )
            throws ApiException {
        return ApiResponse.ok(accountService.getAccountCaseFilter(textSearch,page,pageSize));
    }

    @PatchMapping("/{id}/account-cases/update")
    @Operation(summary = "Chỉnh sửa danh sách kiểm sát viên")
    public ApiResponse<HashMap<String, String>> updateProsecutorList(@RequestParam CasePosition type,
                                                                     @PathVariable Long id, @RequestBody CaseAccountRequest request) throws ApiException {
        return ApiResponse.ok(caseService.updateProsecutorList(type, id, request.getCasePersons()));
    }

    @PatchMapping("/{id}/case-person/suspect-defendant/update")
    @Operation(summary = "Chỉnh sửa danh sách bị can, bị cáo")
    public ApiResponse<HashMap<String, String>> updateSuspectAndDefendantByCase(
            @PathVariable Long id,
            @RequestBody CaseCitizenUpdateRequest request) throws ApiException {
        return ApiResponse.ok(caseService.updateSuspectAndDefendant(id, request));
    }

    @PatchMapping("/{id}/case-person/suspect-defendant/type/update")
    @Operation(summary = "Chỉnh sửa quyền bị can, bị cáo")
    public ApiResponse<HashMap<String, String>> updateTypeCasePersons(
            @PathVariable Long id,
            @RequestBody CaseCitizenUpdateRequest request) throws ApiException {
        return ApiResponse.ok(caseService.updateTypeCasePerson(id, request));
    }

    @PostMapping("/{id}/case-flow/create")
    @Operation(summary = "Tạo mới vụ án")
    public ApiResponse<CaseFlowResponse> createCaseFlow(
            @PathVariable Long id,
            @Valid @RequestBody CaseFlowCreateRequest caseFlowCreateRequest)
            throws ApiException {
        return ApiResponse.ok(caseFlowService.addCaseFlow(id, caseFlowCreateRequest));
    }
    @GetMapping("/{caseId}/case-flow")
    @Operation(summary = "xem sơ đồ vụ án")
    public ApiResponse<CaseFlowResponse> getCaseFlow(@PathVariable Long caseId) throws ApiException {
        return ApiResponse.ok(caseFlowService.getCaseFlow(caseId));
    }

    @PatchMapping("/{id}/case-flow/{idCaseFlow}")
    @Operation(summary = "Chỉnh sửa sơ đồ vụ án")
    public ApiResponse<HashMap<String, String>> update(
            @PathVariable Long id, @PathVariable Long idCaseFlow ,@Valid @RequestBody CaseFlowUpdateRequest caseFlowUpdateRequest)
            throws Exception {
        return ApiResponse.ok(caseFlowService.updateCaseFlow(id,idCaseFlow, caseFlowUpdateRequest));
    }

@GetMapping("/{caseId}/mindMapTemplate")
    @Operation(summary = "Xem danh sách sơ đồ mẫu trong vụ án")
    public ApiResponse<CaseMindmapTemplateResponse<MindmapTemplateResponse>> getAllMindMapTemplates(
            @PathVariable Long caseId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer pageSize,
            @RequestParam(required = false) String textSearch
    ) throws ApiException {
        return ApiResponse.ok(caseService.getAllMindMapTemplates(caseId, page, pageSize, textSearch));
    }

    @GetMapping("/{id}/citizen")
    @Operation(summary = "lấy danh sách công dân")
    public ApiResponse<ResponseFilter<CitizenCaseResponse>> getList_investigator_suspect_defendant(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String textSearch,
            @RequestParam TypeCaseCitizen type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize ) throws ApiException{
        return ApiResponse.ok(caseService.getListInvestigatorSuspectDefendant(id, textSearch,type, page, pageSize));
    }

    @GetMapping("/{caseId}/case-flow/{id}")
    @Operation(summary = "xem chi tiết sơ đồ vụ án")
    public ApiResponse<CaseFlowResponse> getDetailCaseFlow(
            @PathVariable Long caseId,
            @PathVariable Long id
    ) throws ApiException {
        return ApiResponse.ok(caseFlowService.getDetailCaseFlow(caseId, id));
    }
}

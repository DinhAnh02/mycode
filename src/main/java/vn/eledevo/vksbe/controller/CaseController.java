package vn.eledevo.vksbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
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

    @GetMapping("/{id}/investigator")
    @Operation(summary = "Xem và tìm kiếm danh sách điều tra viên")
    public ApiResponse<ResponseFilter<CitizenCaseResponse>> getAllInvestigatorByCaseId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String textSearch,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) throws ApiException {
        return ApiResponse.ok(caseService.getAllInvestigatorByCaseId(id, textSearch, page, pageSize));
    }
}

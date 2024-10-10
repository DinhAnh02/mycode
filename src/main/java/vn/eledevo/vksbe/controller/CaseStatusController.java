package vn.eledevo.vksbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.eledevo.vksbe.dto.request.CaseStatus.CaseStatusCreateRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.service.caseStatus.CaseStatusService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/private/case-status")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Quản lý trạng thái vụ án")
public class CaseStatusController {
    CaseStatusService caseStatusService;
    @PostMapping("/create")
    @Operation(summary = "Thêm mới trạng thái vụ án")
    public ApiResponse<Map<String, String>> createCaseStatus(@Valid @RequestBody CaseStatusCreateRequest caseStatusCreateRequest) throws ApiException {
        return ApiResponse.ok(caseStatusService.createCaseStatus(caseStatusCreateRequest));
    }
}

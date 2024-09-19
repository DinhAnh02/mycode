package vn.eledevo.vksbe.controller;

import static vn.eledevo.vksbe.constant.ErrorCode.CHECK_ORGANIZATIONAL_STRUCTURE;
import static vn.eledevo.vksbe.constant.ErrorCode.FIELD_INVALID;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.account.AccountService;
import vn.eledevo.vksbe.service.department.DepartmentService;
import vn.eledevo.vksbe.service.organization.OrganizationService;
import vn.eledevo.vksbe.service.role.RoleService;

@RestController
@RequestMapping("/api/v1/private/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Account Management", description = "Endpoints for managing user accounts")
public class AccountController {
    AccountService accountService;
    final RoleService roleService;
    final DepartmentService departmentService;
    final OrganizationService organizationService;

    @PatchMapping("/reset-password/{id}")
    @Operation(summary = "Reset user password", description = "Resets the password for the user with the given ID")
    public ApiResponse<AccountResponse> resetPassword(
            @Parameter(description = "ID of the user", required = true) @PathVariable Long id) throws ApiException {
        return ApiResponse.ok(accountService.resetPassword(id));
    }

    @GetMapping("/accounts/{id}/devices")
    @Operation(
            summary = "Get computer devices by account",
            description = "Retrieves a list of computer devices associated with a specific account")
    public ApiResponse<List<ComputerResponse>> getComputerListByAccountId(
            @Parameter(description = "ID of the user", required = true) @PathVariable Long id) throws ApiException {
        return ApiResponse.ok(accountService.getComputersByIdAccount(id));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<AccountDetailResponse>> getAccountDetail(@PathVariable Long id)
            throws ApiException {
        return ResponseEntity.ok(accountService.getAccountDetail(id));
    }

    @PostMapping()
    public ApiResponse<?> getAccountList(
            @RequestBody AccountRequest req, @RequestParam Integer currentPage, @RequestParam Integer limit)
            throws ApiException {
        if (Boolean.FALSE.equals(roleService.roleNameChangeDetector(req.getRoleId(), req.getRoleName()))
                || Boolean.FALSE.equals(
                        departmentService.departmentNameChangeDetector(req.getDepartmentId(), req.getDepartmentName()))
                || Boolean.FALSE.equals(organizationService.organizationNameChangeDetector(
                        req.getOrganizationId(), req.getOrganizationName()))) {
            throw new ApiException(CHECK_ORGANIZATIONAL_STRUCTURE);
        }
        return ApiResponse.ok(accountService.getListAccountByFilter(req, currentPage, limit));
    }

    @PatchMapping("/{idAccount}/inactivate")
    public ApiResponse<?> lockAccount(@PathVariable Long idAccount) throws ApiException {
        if (idAccount == null) {
            throw new ApiException(FIELD_INVALID);
        }
        return ApiResponse.ok(accountService.inactivateAccount(idAccount));
    }
}

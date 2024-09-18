package vn.eledevo.vksbe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.account.AccountService;
import vn.eledevo.vksbe.service.department.DepartmentService;
import vn.eledevo.vksbe.service.organization.OrganizationService;
import vn.eledevo.vksbe.service.role.RoleService;

@RestController
@RequestMapping("/api/v1/private/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountController {
    final AccountService accountService;
    final RoleService roleService;
    final DepartmentService departmentService;
    final OrganizationService organizationService;

    @PostMapping()
    public ResponseEntity<?> getAccountList(
            @RequestBody AccountRequest req, @RequestParam Integer currentPage, @RequestParam Integer limit)
            throws ApiException {
        if (Boolean.FALSE.equals(roleService.roleNameChangeDetector(req.getRoleId(), req.getRoleName()))
                || Boolean.FALSE.equals(
                        departmentService.departmentNameChangeDetector(req.getDepartmentId(), req.getDepartmentName()))
                || Boolean.FALSE.equals(organizationService.organizationNameChangeDetector(
                        req.getOrganizationId(), req.getOrganizationName()))) {
            return ResponseEntity.status(409)
                    .body(new ApiResponse<>(
                            4090, "Cơ cấu tổ chức đã thay đổi. Vui lòng đăng nhập lại để có dữ liệu mới nhất.", null));
        }
        return ResponseEntity.ok().body(accountService.getListAccountByFilter(req, currentPage, limit));
    }
}

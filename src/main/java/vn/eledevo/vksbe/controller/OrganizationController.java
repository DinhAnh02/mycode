package vn.eledevo.vksbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.eledevo.vksbe.dto.request.organization.OrganizationRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.entity.Organizations;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.organization.OrganizationService;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/private/organizations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Quản lý đơn vị")
public class OrganizationController {
    OrganizationService organizationService;

    @PatchMapping("/{id}/update")
    @Operation(summary = "Chỉnh sửa đơn vị")
    public ApiResponse<Organizations> updateOrganization(@PathVariable Long id, @Valid @RequestBody OrganizationRequest organizationRequest) throws ApiException {
        return ApiResponse.ok(organizationService.updateOrganization(id, organizationRequest));
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "xóa đơn vị theo id")
    public ApiResponse<HashMap<String,String>> deleteOrganization(@PathVariable Long id) throws ApiException {
        return ApiResponse.ok(organizationService.deleteOrganization(id));
    }
}

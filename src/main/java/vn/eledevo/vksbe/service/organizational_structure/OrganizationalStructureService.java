package vn.eledevo.vksbe.service.organizational_structure;

import static vn.eledevo.vksbe.constant.ErrorCode.CHECK_ORGANIZATIONAL_STRUCTURE;

import java.util.Objects;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.request.account.AccountCreateRequest;
import vn.eledevo.vksbe.dto.request.account.AccountUpdateRequest;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.department.DepartmentService;
import vn.eledevo.vksbe.service.organization.OrganizationService;
import vn.eledevo.vksbe.service.role.RoleService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrganizationalStructureService {
    RoleService roleService;
    DepartmentService departmentService;
    OrganizationService organizationService;

    public void validate(AccountRequest req) throws ApiException {
        if (!Objects.equals(req.getRoleName(), "")
                && Boolean.FALSE.equals(roleService.roleNameChangeDetector(req.getRoleId(), req.getRoleName()))) {
            throw new ApiException(CHECK_ORGANIZATIONAL_STRUCTURE);
        }
        if (!Objects.equals(req.getDepartmentName(), "")
                && Boolean.FALSE.equals(departmentService.departmentNameChangeDetector(
                        req.getDepartmentId(), req.getDepartmentName()))) {
            throw new ApiException(CHECK_ORGANIZATIONAL_STRUCTURE);
        }
        if (!Objects.equals(req.getOrganizationName(), "")
                && Boolean.FALSE.equals(organizationService.organizationNameChangeDetector(
                        req.getOrganizationId(), req.getOrganizationName()))) {
            throw new ApiException(CHECK_ORGANIZATIONAL_STRUCTURE);
        }
    }

    public void validateUpdate(AccountUpdateRequest req) throws ApiException {
        if (!Objects.equals(req.getRoleName(), "")
                && Boolean.FALSE.equals(roleService.roleNameChangeDetector(req.getRoleId(), req.getRoleName()))) {
            throw new ApiException(CHECK_ORGANIZATIONAL_STRUCTURE);
        }
        if (!Objects.equals(req.getDepartmentName(), "")
                && Boolean.FALSE.equals(departmentService.departmentNameChangeDetector(
                        req.getDepartmentId(), req.getDepartmentName()))) {
            throw new ApiException(CHECK_ORGANIZATIONAL_STRUCTURE);
        }
        if (!Objects.equals(req.getOrganizationName(), "")
                && Boolean.FALSE.equals(organizationService.organizationNameChangeDetector(
                        req.getOrganizationId(), req.getOrganizationName()))) {
            throw new ApiException(CHECK_ORGANIZATIONAL_STRUCTURE);
        }
    }
}

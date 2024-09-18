package vn.eledevo.vksbe.service.category;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.InformationResponse;
import vn.eledevo.vksbe.entity.Departments;
import vn.eledevo.vksbe.entity.Organizations;
import vn.eledevo.vksbe.entity.Roles;
import vn.eledevo.vksbe.repository.DepartmentRepository;
import vn.eledevo.vksbe.repository.OrganizationRepository;
import vn.eledevo.vksbe.repository.RoleRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    DepartmentRepository departmentRepository;

    OrganizationRepository organizationRepository;

    RoleRepository roleRepository;

    @Override
    public ApiResponse<InformationResponse> getAllInformation() {
        List<Departments> departmentsList = departmentRepository.findAll();
        List<Organizations> organizationsList = organizationRepository.findAll();
        List<Roles> rolesList = roleRepository.findAll();

        InformationResponse response = InformationResponse.builder()
                .departments(departmentsList)
                .organizations(organizationsList)
                .roles(rolesList)
                .build();
        return ApiResponse.ok(response);
    }
}

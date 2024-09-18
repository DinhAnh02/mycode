package vn.eledevo.vksbe.service.department;

import java.util.List;

import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.entity.Departments;

public interface DepartmentService {
    Boolean departmentNameChangeDetector(Long departmentId, String departmentName);

    ApiResponse<List<Departments>> getListDepartment();
}

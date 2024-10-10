package vn.eledevo.vksbe.service.department;

import vn.eledevo.vksbe.dto.request.department.UpdateDepartment;
import vn.eledevo.vksbe.exception.ApiException;

import java.util.HashMap;

public interface DepartmentService {
    Boolean departmentNameChangeDetector(Long departmentId, String departmentName);
    HashMap<String, String> updateDepartment(Long departmentId, UpdateDepartment departmentRequest) throws ApiException;
}

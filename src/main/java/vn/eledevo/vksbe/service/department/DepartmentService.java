package vn.eledevo.vksbe.service.department;

import vn.eledevo.vksbe.entity.Departments;

import java.util.List;

public interface DepartmentService {
    Boolean departmentNameChangeDetector(Long departmentId, String departmentName);
    List<Departments> getListDepartment();
}

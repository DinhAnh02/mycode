package vn.eledevo.vksbe.service.department;

import java.util.Optional;

import vn.eledevo.vksbe.entity.Departments;
import vn.eledevo.vksbe.repository.DepartmentRepository;

public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Boolean departmentNameChangeDetector(Long departmentId, String departmentName) {
        Optional<Departments> department = departmentRepository.findById(departmentId);
        return department.isPresent() && department.get().getName().equals(departmentName);
    }
}

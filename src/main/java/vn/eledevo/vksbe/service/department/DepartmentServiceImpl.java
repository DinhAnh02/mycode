package vn.eledevo.vksbe.service.department;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.eledevo.vksbe.entity.Departments;
import vn.eledevo.vksbe.repository.DepartmentRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;

    @Override
    public Boolean departmentNameChangeDetector(Long departmentId, String departmentName) {
        Optional<Departments> department = departmentRepository.findById(departmentId);
        return department.isPresent() && department.get().getName().equals(departmentName);
    }
}

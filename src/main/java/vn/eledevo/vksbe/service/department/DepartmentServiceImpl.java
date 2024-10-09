package vn.eledevo.vksbe.service.department;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Service;
import static vn.eledevo.vksbe.constant.ErrorCodes.DepartmentErrorCode.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.department.UpdateDepartment;
import vn.eledevo.vksbe.entity.Departments;
import vn.eledevo.vksbe.exception.ApiException;
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

    @Override
    public HashMap<String, String> updateDepartment(Long departmentId, UpdateDepartment departmentRequest) throws ApiException {
        Departments existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ApiException(DEPARTMENT_NOT_FOUND));

        if(departmentRepository.existsDepartmentsByName(departmentRequest.getDepartmentName())){
            throw new ApiException(DEPARTMENT_EXISTED);
        }

        existingDepartment.setName(departmentRequest.getDepartmentName());
        departmentRepository.save(existingDepartment);
        return new HashMap<>();
    }
}

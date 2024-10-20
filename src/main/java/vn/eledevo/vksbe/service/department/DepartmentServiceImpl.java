package vn.eledevo.vksbe.service.department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ErrorCodes.DepartmentErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.constant.ResponseMessage;
import vn.eledevo.vksbe.dto.request.department.UpdateDepartment;
import vn.eledevo.vksbe.dto.response.ResultList;
import vn.eledevo.vksbe.dto.response.department.DepartmentResponse;
import vn.eledevo.vksbe.entity.Departments;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;
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
    public ResultList<DepartmentResponse> getDepartmentList() {
        List<DepartmentResponse> departments = departmentRepository.getDepartmentList();
        return ResultList.<DepartmentResponse>builder().content(departments).build();
    }

    @Override
    public HashMap<String, String> updateDepartment(Long departmentId, UpdateDepartment departmentRequest)
            throws ApiException, ValidationException {
        Map<String, String> errors = new HashMap<>();
        Departments existingDepartment = departmentRepository
                .findById(departmentId)
                .orElseThrow(() -> new ApiException(SystemErrorCode.INTERNAL_SERVER));

        if (departmentRepository.existsDepartmentsByName(departmentRequest.getDepartmentName())) {
            errors.put("departmentName", ResponseMessage.DEPARTMENT_EXISTED);
            throw new ValidationException(errors);
        }

        existingDepartment.setName(departmentRequest.getDepartmentName());
        departmentRepository.save(existingDepartment);
        return new HashMap<>();
    }
}

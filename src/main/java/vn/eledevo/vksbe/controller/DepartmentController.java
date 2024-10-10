package vn.eledevo.vksbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.eledevo.vksbe.dto.request.department.UpdateDepartment;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.department.DepartmentService;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/private/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Quản lý phòng ban")
public class DepartmentController {
    DepartmentService departmentService;

    @PatchMapping("/{id}/update-department")
    @Operation(summary = "Cập nhật phòng ban")
    public ApiResponse<HashMap<String, String>> updateDepartment(@PathVariable Long id,
                                                                 @RequestBody @Valid UpdateDepartment departmentRequest)throws ApiException {
        return ApiResponse.ok(departmentService.updateDepartment(id, departmentRequest));
    }
}

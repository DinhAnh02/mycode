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
import vn.eledevo.vksbe.dto.response.ResultList;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.department.DepartmentService;

import java.util.HashMap;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.department.DepartmentResponse;
import vn.eledevo.vksbe.service.department.DepartmentService;

@RestController
@RequestMapping("/api/v1/private/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Quản lý phòng ban")
public class DepartmentController {
    DepartmentService departmentService;

    @GetMapping()
    @Operation(summary = "Xem danh sách phòng ban")
    public ApiResponse<ResultList<DepartmentResponse>> getDepartmentList() {
        return ApiResponse.ok(departmentService.getDepartmentList());
    }

    @PatchMapping("/{id}/update-department")
    @Operation(summary = "Cập nhật phòng ban")
    public ApiResponse<HashMap<String, String>> updateDepartment(@PathVariable Long id,
                                                                 @RequestBody @Valid UpdateDepartment departmentRequest)throws ApiException {
        return ApiResponse.ok(departmentService.updateDepartment(id, departmentRequest));
    }
}

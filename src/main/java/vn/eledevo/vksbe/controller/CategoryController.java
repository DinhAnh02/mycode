package vn.eledevo.vksbe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.InformationResponse;
import vn.eledevo.vksbe.service.category.CategoryService;

@RestController
@RequestMapping("/api/v1/public/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryController {
    final CategoryService service;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<InformationResponse>> getAllInformation() {
        return ResponseEntity.ok(service.getAllInformation());
    }
}

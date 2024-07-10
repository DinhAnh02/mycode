package vn.eledevo.vksbe.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;

@RestController
public class DemoController {

    @GetMapping("/hello")
    public ApiResponse<String> hello() throws ApiException, ValidationException {
        if (true) {
            Map<String, String> errors = new HashMap<>();
            errors.put("name", "Da bi trung");
            errors.put("cccd", "Da bi trung");
            throw new ValidationException(errors);
        }
        return ApiResponse.ok("hello");
    }
}

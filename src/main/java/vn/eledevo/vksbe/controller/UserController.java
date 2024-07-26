package vn.eledevo.vksbe.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import vn.eledevo.vksbe.dto.request.UserRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.UserResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.service.user.UserService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/private/users")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest)
            throws ValidationException {
        return ApiResponse.ok(userService.createUser(userRequest));
    }

    @PatchMapping("/private/users/search")
    public ApiResponse<List<UserResponse>> searchDevice(@RequestParam Map<String, Object> allParams) {
        return ApiResponse.ok(userService.searchUser(allParams));
    }

    @PatchMapping("/private/user/soft-delete/{id}")
    public ApiResponse<UserResponse> deleteUser(@PathVariable UUID id) throws ApiException {
        return ApiResponse.ok(userService.deleteUser(id));
    }

    @DeleteMapping("/private/user/delete/{id}")
    public ApiResponse removeUser(@PathVariable UUID id) throws ApiException {
        return ApiResponse.ok(userService.removeUser(id));
    }
}

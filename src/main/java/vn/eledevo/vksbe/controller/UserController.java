package vn.eledevo.vksbe.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import vn.eledevo.vksbe.dto.request.UserRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.UserResponse;
import vn.eledevo.vksbe.entity.DeviceInfo;
import vn.eledevo.vksbe.entity.User;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.service.user.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/private/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest)
            throws ValidationException {
        return ApiResponse.ok(userService.createUser(userRequest));
    }
    @PatchMapping("/search")
    public ApiResponse<List<User>> searchDevice(@RequestParam Map<String,Object> allParams){
        return ApiResponse.ok(userService.searchUser(allParams));
    }
}

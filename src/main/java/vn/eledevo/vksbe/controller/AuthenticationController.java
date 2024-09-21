package vn.eledevo.vksbe.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.AuthenticationRequest;
import vn.eledevo.vksbe.dto.request.ChangePasswordRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.AuthenticationResponse;
import vn.eledevo.vksbe.dto.response.account.ChangePasswordResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.authenticate.AuthenticationService;
import vn.eledevo.vksbe.utils.SecurityUtils;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Tag(name = "Xác thực tài khoản")
public class AuthenticationController {
    final AuthenticationService service;

    @PostMapping("/authenticate")
    @Operation(summary = "Đăng nhập")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request)
            throws ApiException {
        return ResponseEntity.ok(service.authenticate(request));
    }

    //    @PostMapping("/refresh-token")
    //    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //        service.refreshToken(request, response);
    //    }

    @GetMapping("/test")
    public ResponseEntity<String> twoFactorAuthentication() {
        return ResponseEntity.ok(SecurityUtils.getUserName());
    }

    @PatchMapping("/changePassword")
    @Operation(summary = "Đổi mật khẩu thành tài khoản" )
    public ApiResponse<ChangePasswordResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request)
            throws ApiException {
        return ApiResponse.ok(service.changePassword(request));
    }
}

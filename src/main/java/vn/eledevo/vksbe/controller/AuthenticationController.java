package vn.eledevo.vksbe.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.AuthenticationRequest;
import vn.eledevo.vksbe.dto.request.ChangePasswordRequest;
import vn.eledevo.vksbe.dto.request.TwoFactorAuthenticationRequest;
import vn.eledevo.vksbe.dto.request.account.CreateAccountTest;
import vn.eledevo.vksbe.dto.request.pinRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.AuthenticationResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.authenticate.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Tag(name = "Xác thực tài khoản")
public class AuthenticationController {
    final AuthenticationService service;

    @PostMapping("/authenticate")
    @Operation(summary = "Đăng nhập bước 1 bằng tài khoản và mật khẩu")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request)
            throws ApiException {
        return ApiResponse.ok(service.authenticate(request));
    }

    //    @PostMapping("/refresh-token")
    //    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //        service.refreshToken(request, response);
    //    }

    @PostMapping("/2FA")
    @Operation(summary = "Đăng nhập xác thực bước 2")
    public ApiResponse<AuthenticationResponse> twoFactorAuthentication(
            @RequestBody TwoFactorAuthenticationRequest request) throws Exception {
        return ApiResponse.ok(service.twoFactorAuthenticationRequest(request));
    }

    @PatchMapping("/create-pin")
    @Operation(summary = "Tạo mã PIN khi đăng nhật lần đầu")
    public ApiResponse<String> createPin(@RequestBody @Valid pinRequest pinRequest) throws ApiException {
        return ApiResponse.ok(service.createPin(pinRequest));
    }

    @PatchMapping("/change-password")
    @Operation(summary = "Đổi mật khẩu thành tài khoản")
    public ApiResponse<String> changePassword(@RequestBody @Valid ChangePasswordRequest request) throws ApiException {
        return ApiResponse.ok(service.changePassword(request));
    }

    @PostMapping("/create-account-test")
    @Operation(summary = "API test để tạo mật khẩu và mã pin mẫu")
    public ApiResponse<CreateAccountTest> createAccountTest(@RequestBody CreateAccountTest createAccountTest) {
        return ApiResponse.ok(service.createAccountTest(createAccountTest));
    }
}

package vn.eledevo.vksbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.AuthenticationRequest;
import vn.eledevo.vksbe.dto.response.AuthenticationResponse;
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

}

package vn.eledevo.vksbe.controller;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.AuthenticationRequest;
import vn.eledevo.vksbe.dto.request.RegisterRequest;
import vn.eledevo.vksbe.dto.request.TwoFactorAuthenticationRequest;
import vn.eledevo.vksbe.dto.response.AuthenticationResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.authenticate.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {
    final AuthenticationService service;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
            throws ApiException {
        return ResponseEntity.ok(service.authenticate(request));
    }

//    @PostMapping("/refresh-token")
//    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        service.refreshToken(request, response);
//    }

//    @PostMapping("/public/2fa")
//    public ResponseEntity<AuthenticationResponse> twoFactorAuthentication(
//            @RequestBody TwoFactorAuthenticationRequest twoFactorAuthenticationRequest)
//            throws ApiException, JsonProcessingException {
//        return ResponseEntity.ok(service.twoFactorAuthentication(twoFactorAuthenticationRequest));
//    }
}

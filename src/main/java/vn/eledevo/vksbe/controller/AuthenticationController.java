package vn.eledevo.vksbe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.AuthenticationRequest;
import vn.eledevo.vksbe.dto.response.AuthenticationResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.authenticate.AuthenticationService;

@RestController
@CrossOrigin("*")
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

    @GetMapping("/test")
    public ResponseEntity<String> twoFactorAuthentication() {
        return ResponseEntity.ok("Tesstttt");
    }
}

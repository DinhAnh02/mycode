package vn.eledevo.vksbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.account.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Account Management", description = "Endpoints for managing user accounts")
public class AccountController {
    AccountService accountService;

    @PatchMapping("/reset-password/{id}")
    @Operation(summary = "Reset user password", description = "Resets the password for the user with the given ID")
    public ApiResponse<AccountResponse> resetPassword(
            @Parameter(description = "ID of the user", required = true) @PathVariable Long id
    ) throws ApiException {
        return ApiResponse.ok(accountService.resetPassword(id));
    }

    @GetMapping("/api/v1/private/accounts/{id}/devices")
    @Operation(
            summary = "Get computer devices by account",
            description = "Retrieves a list of computer devices associated with a specific account")
    public ApiResponse<List<ComputerResponse>> getComputerListByAccountId(
            @Parameter(description = "ID of the user", required = true) @PathVariable Long id
    ) throws ApiException {
        return ApiResponse.ok(accountService.getComputersByIdAccount(id));
    }
}

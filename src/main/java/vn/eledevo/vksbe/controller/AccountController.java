package vn.eledevo.vksbe.controller;

import static vn.eledevo.vksbe.constant.ErrorCode.FIELD_INVALID;
import static vn.eledevo.vksbe.utils.FileUtils.getContentType;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.request.account.AccountCreateRequest;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.dto.response.usb.UsbResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.service.account.AccountService;
import vn.eledevo.vksbe.service.organizational_structure.OrganizationalStructureService;

@RestController
@RequestMapping("/api/v1/private/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Quản lý tài khoản")
public class AccountController {
    AccountService accountService;
    OrganizationalStructureService organizationalStructureUtilsService;

    @PatchMapping("/reset-password/{id}")
    @Operation(summary = "Reset mật khẩu")
    public ApiResponse<String> resetPassword(
            @Parameter(description = "ID of the user", required = true) @PathVariable Long id) throws ApiException {
        return ApiResponse.ok(accountService.resetPassword(id));
    }

    @GetMapping("/accounts/{id}/devices")
    @Operation(
            summary = "Lấy danh sách thiết bị đã liên kết với tài khoản",
            description = "Retrieves a list of computer devices associated with a specific account")
    public ApiResponse<List<ComputerResponse>> getComputerListByAccountId(
            @Parameter(description = "ID of the user", required = true) @PathVariable Long id) throws ApiException {
        return ApiResponse.ok(accountService.getComputersByIdAccount(id));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "Xem chi tiết thông tin tài khoản")
    public ApiResponse<AccountDetailResponse> getAccountDetail(@PathVariable Long id)
            throws ApiException {
        return ApiResponse.ok(accountService.getAccountDetail(id));
    }

    @PostMapping()
    @Operation(summary = "Xem danh sách tài khoản")
    public ApiResponse<?> getAccountList(
            @RequestBody AccountRequest req,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer limit)
            throws ApiException {
        organizationalStructureUtilsService.validate(req);
        return ApiResponse.ok(accountService.getListAccountByFilter(req, currentPage, limit));
    }

    @PatchMapping("/{accountId}/inactivate")
    @Operation(summary = "Khóa tài khoản")
    public ApiResponse<?> lockAccount(@PathVariable Long accountId) throws ApiException {
        if (accountId == null) {
            throw new ApiException(FIELD_INVALID);
        }
        return ApiResponse.ok(accountService.inactivateAccount(accountId));
    }

    @GetMapping("/{id}/usb")
    @Operation(summary = "Lấy thông tin USB liên kết với tài khoản", description = "Get usb by account-id")
    public ApiResponse<UsbResponse> getUsbInfo(
            @Parameter(description = "ID of the user", required = true) @PathVariable Long id) throws ApiException {
        return ApiResponse.ok(accountService.getUsbInfo(id));
    }

    @PatchMapping("/connect-computer/{id}/computers")
    @Operation(summary = "Kết nối tài khoản với thiết bị máy tính", description = "Kết nối tài khoản với thiết bị")
    public ApiResponse<Result<?>> connectComputers(
            @PathVariable("id") Long accountId,
            @RequestBody @NotEmpty(message = "Danh sách kết nối không được rỗng") Set<Long> computerIds)
            throws ApiException {
        return ApiResponse.ok(accountService.connectComputers(accountId, computerIds));
    }

    @PatchMapping("/remove-usb/{accountId}/usb/{usbId}")
    @Operation(summary = "Gỡ USB kết nối với tài khoản")
    public ApiResponse<?> removeUsb(@PathVariable Long accountId, @PathVariable Long usbId) throws ApiException {
        return ApiResponse.ok(accountService.removeUSB(accountId, usbId));
    }

    @PatchMapping("/{idAccount}/activate")
    @Operation(summary = "Active tài khoản")
    public ApiResponse<AccountResponse> activateAccount(
            @Parameter(description = "ID of the user", required = true) @PathVariable Long idAccount)
            throws ApiException {
        return ApiResponse.ok(accountService.activeAccount(idAccount));
    }

    @PatchMapping("/swap-account-status/{employeeId}/{requesterId}")
    @Operation(summary = "Hoán đổi vị trí trưởng phòng")
    public ApiResponse<?> swapAccountSattus(@PathVariable Long employeeId, @PathVariable Long requesterId)
            throws ApiException {
        return ApiResponse.ok(accountService.swapStatus(employeeId, requesterId));
    }

    @PostMapping("/upload-image")
    @Operation(summary = "Upload avatar")
    public ApiResponse<String> uploadAvatar(@RequestParam("file") MultipartFile file) throws ApiException, IOException {
        return ApiResponse.ok(accountService.uploadAvatar(file));
    }

    @GetMapping("/download-image/{fileName:.+}")
    @Operation(summary = "Download avatar")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable String fileName) throws ApiException, IOException {
        return ResponseEntity.ok()
                .header("Content-Type", getContentType(fileName))
                .body(accountService.downloadAvatar(fileName));
    }

    @PostMapping("/create")
    @Operation(summary = "Tạo mới tài khoản")
    public ApiResponse<AccountResponse> createAccount(@RequestBody @Valid AccountCreateRequest request)
            throws ApiException, ValidationException {
        return ApiResponse.ok(accountService.createAccountInfo(request));
    }

    @GetMapping("/{accountId}/remove-computer/{computerId}")
    @Operation(summary = "Gỡ thiết bị máy tính đã liên kết với tài khoản")
    public ResponseEntity<ApiResponse<String>> removeComputer(@PathVariable Long accountId, @PathVariable Long computerId)
            throws ApiException {
        return ResponseEntity.ok(accountService.removeConnectComputer(accountId, computerId));
    }
}

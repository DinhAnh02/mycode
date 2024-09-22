package vn.eledevo.vksbe.service.account;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.request.account.AccountCreateRequest;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter;
import vn.eledevo.vksbe.dto.response.account.ObjectSwapResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.dto.response.computer.ConnectComputerResponse;
import vn.eledevo.vksbe.dto.response.usb.UsbResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;

public interface AccountService {
    String resetPassword(Long id) throws ApiException;

    Result<AccountResponseByFilter> getListAccountByFilter(
            AccountRequest accountRequest, Integer currentPage, Integer limit) throws ApiException;

    ApiResponse<AccountDetailResponse> getAccountDetail(Long accountId) throws ApiException;

    List<ComputerResponse> getComputersByIdAccount(Long accountId) throws ApiException;

    ApiResponse inactivateAccount(Long idAccount) throws ApiException;

    ApiResponse<String> removeConnectComputer(Long accountId, Long computerId) throws ApiException;

    UsbResponse getUsbInfo(Long id) throws ApiException;

    List<ConnectComputerResponse> connectComputers(Long id, Set<Long> computerIds) throws ApiException;

    ApiResponse<?> removeUSB(Long idAccount, Long idUsb) throws ApiException;

    AccountResponse activeAccount(Long id) throws ApiException;

    ObjectSwapResponse swapStatus(Long employeeId, Long requesterId) throws ApiException;

    AccountResponse createAccountInfo(AccountCreateRequest request) throws ValidationException, ApiException;

    String uploadAvatar(MultipartFile file) throws ApiException, IOException;

    byte[] downloadAvatar(String fileName) throws ApiException, IOException;


}

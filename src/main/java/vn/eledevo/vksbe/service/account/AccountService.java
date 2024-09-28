package vn.eledevo.vksbe.service.account;

import org.springframework.web.multipart.MultipartFile;
import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.model.account.UserInfo;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.request.account.AccountCreateRequest;
import vn.eledevo.vksbe.dto.request.account.AccountUpdateRequest;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.dto.response.account.AccResponse;
import vn.eledevo.vksbe.dto.model.account.AccountQueryToFilter;
import vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter;
import vn.eledevo.vksbe.dto.response.account.ActivedAccountResponse;
import vn.eledevo.vksbe.dto.response.account.ObjectSwapResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.dto.response.computer.ConnectComputerResponse;
import vn.eledevo.vksbe.dto.response.usb.UsbResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface AccountService {
    String resetPassword(Long id) throws ApiException;

    ResponseFilter<AccountResponseByFilter> getListAccountByFilter(
            AccountRequest accountRequest, Integer currentPage, Integer limit) throws ApiException;

    AccountDetailResponse getAccountDetail(Long accountId) throws ApiException;

    List<ComputerResponse> getComputersByIdAccount(Long accountId) throws ApiException;

    String inactivateAccount(Long idAccount) throws ApiException;

    String removeConnectComputer(Long accountId, Long computerId) throws ApiException;

    UsbResponse getUsbInfo(Long id) throws ApiException;

    Result<ConnectComputerResponse> connectComputers(Long id, Set<Long> computerIds) throws ApiException;

    String removeConnectUSB(Long accountId, Long usbId) throws ApiException;

    ActivedAccountResponse activeAccount(Long id) throws ApiException;

    ObjectSwapResponse swapStatus(Long employeeId, Long requesterId) throws ApiException;

    AccountResponse createAccountInfo(AccountCreateRequest request) throws ValidationException, ApiException;

    String uploadAvatar(MultipartFile file) throws ApiException, IOException;

    byte[] downloadAvatar(String fileName) throws ApiException, IOException;

    AccResponse<Object> updateAccountInfo(Long updatedAccId, AccountUpdateRequest req) throws ApiException;

    UserInfo userInfo() throws ApiException;
}

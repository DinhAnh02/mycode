package vn.eledevo.vksbe.service.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.model.account.UserInfo;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.request.PinChangeRequest;
import vn.eledevo.vksbe.dto.request.account.AccountCreateRequest;
import vn.eledevo.vksbe.dto.request.account.AccountUpdateRequest;
import vn.eledevo.vksbe.dto.request.account.AvatarRequest;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.ResultList;
import vn.eledevo.vksbe.dto.response.ResultUrl;
import vn.eledevo.vksbe.dto.response.account.AccountResponse;
import vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter;
import vn.eledevo.vksbe.dto.response.account.AccountSwapResponse;
import vn.eledevo.vksbe.dto.response.account.ActivedAccountResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.dto.response.computer.ConnectComputerResponse;
import vn.eledevo.vksbe.dto.response.usb.UsbConnectedResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;

public interface AccountService {
    HashMap<String, String> resetPassword(Long id) throws ApiException;

    ResponseFilter<AccountResponseByFilter> getListAccountByFilter(
            AccountRequest accountRequest, Integer currentPage, Integer limit) throws ApiException;

    AccountDetailResponse getAccountDetail(Long accountId) throws ApiException;

    ResultList<ComputerResponse> getComputersByIdAccount(Long accountId) throws ApiException;

    HashMap<String, String> inactivateAccount(Long idAccount) throws ApiException;

    HashMap<String, String> removeConnectComputer(Long accountId, Long computerId) throws ApiException;

    ResultList<UsbConnectedResponse> getUsbInfo(Long id) throws ApiException;

    ResultList<ConnectComputerResponse> connectComputers(Long id, Set<Long> computerIds) throws ApiException;

    HashMap<String, String> removeConnectUSB(Long accountId, Long usbId) throws ApiException;

    ActivedAccountResponse activeAccount(Long id) throws ApiException;

    AccountSwapResponse swapStatus(Long accountId, Long swapAccountId) throws ApiException;

    AccountResponse createAccountInfo(AccountCreateRequest request) throws ValidationException, ApiException;

    ResultUrl uploadAvatar(MultipartFile file) throws ApiException, IOException;

    Resource downloadAvatar(String fileName) throws ApiException, IOException;

    AccountSwapResponse updateAccountInfo(Long updatedAccId, AccountUpdateRequest req) throws ApiException;

    UserInfo userInfo() throws ApiException;

    AccountResponse updateAvatarUserInfo(Long id, AvatarRequest request) throws ApiException;

    HashMap<String, String> changePinUserLogin(PinChangeRequest pinRequest) throws ApiException;
}

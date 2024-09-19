package vn.eledevo.vksbe.service.account;

import java.util.List;

import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter;
import vn.eledevo.vksbe.dto.response.account.Result;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.exception.ApiException;

public interface AccountService {
    AccountResponse resetPassword(Long id) throws ApiException;

    ApiResponse<Result<AccountResponseByFilter>> getListAccountByFilter(AccountRequest accountRequest, Integer currentPage, Integer limit)
            throws ApiException;

    ApiResponse<AccountDetailResponse> getAccountDetail(Long accountId) throws ApiException;

    List<ComputerResponse> getComputersByIdAccount(Long accountId) throws ApiException;
}

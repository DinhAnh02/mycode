package vn.eledevo.vksbe.service.account;

import vn.eledevo.vksbe.dto.model.account.AccountDetailResponse;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.exception.ApiException;

public interface AccountService {
    AccountResponse resetPassword(Long id) throws ApiException;

    ApiResponse getListAccountByFilter(AccountRequest accountRequest, Integer currentPage, Integer limit)
            throws ApiException;

    ApiResponse<AccountDetailResponse> getAccountDetail(Long accountId) throws ApiException;
}

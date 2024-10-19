package vn.eledevo.vksbe.service.cases;

import java.util.HashMap;

import vn.eledevo.vksbe.dto.request.cases.CaseCreateRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseFilterRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseUpdateRequest;
import vn.eledevo.vksbe.dto.request.document.DocumentFolderCreationRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseCitizenUpdateRequest;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.cases.CaseFilterResponse;
import vn.eledevo.vksbe.dto.response.cases.CaseInfomationResponse;
import vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse;
import vn.eledevo.vksbe.exception.ApiException;

import vn.eledevo.vksbe.dto.response.account.AccountFilterCaseResponse;

public interface CaseService {
    ResponseFilter<CitizenCaseResponse> getAllInvestigatorByCaseId(Long id, String textSearch, int page, int pageSize)
            throws ApiException;

    HashMap<String, String> updateCase(Long id, CaseUpdateRequest request) throws ApiException;

    CaseInfomationResponse getCaseInfomationDetail(Long id) throws ApiException;

    HashMap<String, Long> createCase(CaseCreateRequest caseCreateRequest) throws ApiException;

    ResponseFilter<CaseFilterResponse> getCaseByFilter(
            CaseFilterRequest caseFilterRequest, Integer currentPage, Integer limit) throws ApiException;

    ResponseFilter<CitizenCaseResponse> getAllSuspectDefendantByCaseId(
            Long id, String textSearch, int page, int pageSize) throws ApiException;

    HashMap<String, String> createDocumentFolder(Long caseId, DocumentFolderCreationRequest request)
            throws ApiException;

    ResponseFilter<AccountFilterCaseResponse> getCaseProsecutorList (
            String textSearch, Long page, Long pageSize, Long caseId) throws ApiException;

    ResponseFilter<AccountFilterCaseResponse> getUserInChargeList(String textSearch, Long caseId, Integer page, Integer pageSize) throws ApiException;

    HashMap<String, String> updateInvestigator(Long id, CaseCitizenUpdateRequest request) throws ApiException;
}

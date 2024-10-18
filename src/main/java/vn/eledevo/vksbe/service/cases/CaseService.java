package vn.eledevo.vksbe.service.cases;

import java.util.HashMap;

import vn.eledevo.vksbe.dto.request.cases.CaseCreateRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseUpdateRequest;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.cases.CaseInfomationResponse;
import vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse;
import vn.eledevo.vksbe.exception.ApiException;

public interface CaseService {
    ResponseFilter<CitizenCaseResponse> getAllInvestigatorByCaseId(Long id, String textSearch, int page, int pageSize)
            throws ApiException;

    HashMap<String, String> updateCase(Long id, CaseUpdateRequest request) throws ApiException;
    CaseInfomationResponse getCaseInfomationDetail(Long id) throws ApiException;

    HashMap<String, Long> createCase(CaseCreateRequest caseCreateRequest) throws ApiException;

    ResponseFilter<CitizenCaseResponse> getAllSuspectDefendantByCaseId(Long id, String textSearch, int page, int pageSize) throws ApiException;
}

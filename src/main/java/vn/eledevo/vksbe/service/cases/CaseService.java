package vn.eledevo.vksbe.service.cases;

import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse;
import vn.eledevo.vksbe.exception.ApiException;

public interface CaseService {
    ResponseFilter<CitizenCaseResponse> getAllInvestigatorByCaseId(Long id, String textSearch, int page, int pageSize) throws ApiException;
}

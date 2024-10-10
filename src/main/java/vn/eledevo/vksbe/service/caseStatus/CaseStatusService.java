package vn.eledevo.vksbe.service.caseStatus;

import vn.eledevo.vksbe.dto.request.CaseStatus.CaseStatusCreateRequest;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;

import java.util.HashMap;

public interface CaseStatusService {
    HashMap<String,String> createCaseStatus(CaseStatusCreateRequest caseStatusCreateRequest) throws  ApiException;
}

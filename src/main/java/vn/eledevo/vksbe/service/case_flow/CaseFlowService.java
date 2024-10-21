package vn.eledevo.vksbe.service.case_flow;

import vn.eledevo.vksbe.dto.response.case_flow.CaseFlowResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.dto.request.case_flow.CaseFlowCreateRequest;

import vn.eledevo.vksbe.dto.request.case_flow.CaseFlowUpdateRequest;


import java.util.HashMap;


public interface CaseFlowService {
   CaseFlowResponse getCaseFlow(Long caseId) throws ApiException;

   CaseFlowResponse addCaseFlow(Long caseId, CaseFlowCreateRequest caseFlowCreateRequest) throws ApiException;

   HashMap<String, String> updateCaseFlow(Long id, Long idCaseFlow, CaseFlowUpdateRequest caseFlowUpdateRequest)
            throws Exception;

   CaseFlowResponse getDetailCaseFlow(Long caseId, Long id) throws ApiException;
}

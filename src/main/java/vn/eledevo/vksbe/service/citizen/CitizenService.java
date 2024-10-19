package vn.eledevo.vksbe.service.citizen;

import vn.eledevo.vksbe.dto.request.citizens.CitizensRequest;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.citizen.CitizenResponse;
import vn.eledevo.vksbe.dto.response.citizen.CitizenUpdateRequest;
import vn.eledevo.vksbe.exception.ApiException;
import java.util.Map;
import java.util.HashMap;

public interface CitizenService {
    ResponseFilter<CitizenResponse> getListCitizen(String textSearch, Integer page, Integer pageSize) throws ApiException;
    HashMap<String, String> updateCitizen(Long citizenId,CitizenUpdateRequest citizenResponse) throws Exception;
    Map<String, String> createCitizens(CitizensRequest request) throws ApiException;
}

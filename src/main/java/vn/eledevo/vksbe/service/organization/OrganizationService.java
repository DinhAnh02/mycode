package vn.eledevo.vksbe.service.organization;

import vn.eledevo.vksbe.dto.request.OrganizationSearch;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.organization.OrganizationResponse;
import vn.eledevo.vksbe.exception.ApiException;

import java.util.HashMap;

import vn.eledevo.vksbe.dto.request.organization.OrganizationRequest;
import vn.eledevo.vksbe.entity.Organizations;

public interface OrganizationService {
    Boolean organizationNameChangeDetector(Long organizationId, String organizationName);

    Organizations updateOrganization(Long organizationId, OrganizationRequest organizationRequest) throws ApiException;

    HashMap<String,String> deleteOrganization(Long organizationId) throws ApiException;

    ResponseFilter<OrganizationResponse> getOrganizationList(OrganizationSearch organizationSearch, Integer page, Integer pageSize) throws ApiException;

}

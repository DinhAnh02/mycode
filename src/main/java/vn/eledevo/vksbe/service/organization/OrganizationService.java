package vn.eledevo.vksbe.service.organization;

import vn.eledevo.vksbe.dto.request.organization.OrganizationRequest;
import vn.eledevo.vksbe.entity.Organizations;
import vn.eledevo.vksbe.exception.ApiException;

import java.util.HashMap;

public interface OrganizationService {
    Boolean organizationNameChangeDetector(Long organizationId, String organizationName);

    Organizations updateOrganization(Long organizationId, OrganizationRequest organizationRequest) throws ApiException;

    HashMap<String,String> deleteOrganization(Long organizationId) throws ApiException;
}

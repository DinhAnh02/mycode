package vn.eledevo.vksbe.service.organization;

import vn.eledevo.vksbe.dto.request.organization.OrganizationRequest;
import vn.eledevo.vksbe.dto.response.organization.OrganizationResponse;
import vn.eledevo.vksbe.entity.Organizations;
import vn.eledevo.vksbe.exception.ApiException;

public interface OrganizationService {
    Boolean organizationNameChangeDetector(Long organizationId, String organizationName);

    Organizations updateOrganization(Long organizationId, OrganizationRequest organizationRequest) throws ApiException;

}

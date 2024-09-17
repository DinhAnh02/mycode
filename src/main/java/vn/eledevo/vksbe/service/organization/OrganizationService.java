package vn.eledevo.vksbe.service.organization;

public interface OrganizationService {
    Boolean organizationNameChangeDetector(Long organizationId, String organizationName);
}

package vn.eledevo.vksbe.service.organization;

import java.util.Optional;

import vn.eledevo.vksbe.entity.Organizations;
import vn.eledevo.vksbe.repository.OrganizationRepository;

public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Boolean organizationNameChangeDetector(Long organizationId, String organizationName) {
        Optional<Organizations> organization = organizationRepository.findById(organizationId);
        return organization.isPresent() && organization.get().getName().equals(organizationName);
    }
}

package vn.eledevo.vksbe.service.organization;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.entity.Organizations;
import vn.eledevo.vksbe.repository.OrganizationRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrganizationServiceImpl implements OrganizationService {
    OrganizationRepository organizationRepository;

    @Override
    public Boolean organizationNameChangeDetector(Long organizationId, String organizationName) {
        Optional<Organizations> organization = organizationRepository.findById(organizationId);
        return organization.isPresent() && organization.get().getName().equals(organizationName);
    }
}

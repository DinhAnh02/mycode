package vn.eledevo.vksbe.service.organization;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ErrorCodes.OrganizationErrorCode;
import vn.eledevo.vksbe.dto.request.organization.OrganizationRequest;
import vn.eledevo.vksbe.entity.Organizations;
import vn.eledevo.vksbe.exception.ApiException;
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


    @Override
    public Organizations updateOrganization(Long organizationId, OrganizationRequest organizationRequest) throws ApiException {
        Optional<Organizations> organizationOptional = organizationRepository.findById(organizationId);
        if(organizationOptional.isEmpty()){
            throw new ApiException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND);
        }

        if(organizationOptional.get().getIsDefault()){
            throw new ApiException(OrganizationErrorCode.ORGANIZATION_DEFAULT);
        }

        if(!(organizationRequest.getCode().equals(organizationOptional.get().getCode()))){
            Boolean checkOrganizationExistByCode = organizationRepository.existsByCode(organizationRequest.getCode());
            if(checkOrganizationExistByCode){
                throw new ApiException(OrganizationErrorCode.ORGANIZATION_CODE_EXIST);
            }
        }

        if(!(organizationRequest.getName().equals(organizationOptional.get().getName()))){
            Boolean checkOrganizationExistByName = organizationRepository.existsByName(organizationRequest.getName());
            if(checkOrganizationExistByName){
                throw new ApiException(OrganizationErrorCode.ORGANIZATION_NAME_EXIST);
            }
        }

        organizationOptional.get().setName(organizationRequest.getName());
        organizationOptional.get().setCode(organizationRequest.getCode());
        organizationOptional.get().setAddress(organizationRequest.getAddress());

        return organizationRepository.save(organizationOptional.get());
    }
}

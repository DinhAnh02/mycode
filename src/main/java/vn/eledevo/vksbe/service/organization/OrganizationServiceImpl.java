package vn.eledevo.vksbe.service.organization;

import java.util.HashMap;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ErrorCodes.OrganizationErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.constant.ResponseMessage;
import vn.eledevo.vksbe.dto.request.organization.OrganizationRequest;
import vn.eledevo.vksbe.constant.ErrorCodes.AccountErrorCode;
import vn.eledevo.vksbe.dto.request.OrganizationSearch;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.organization.OrganizationResponse;
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
    public ResponseFilter<OrganizationResponse> getOrganizationList(OrganizationSearch organizationSearch, Integer page, Integer pageSize) throws ApiException {
        if (page < 1) {
            throw new ApiException(OrganizationErrorCode.ORGANIZATION_SYSTEM_ERROR);
        }
        if (pageSize < 1) {
            throw new ApiException(OrganizationErrorCode.ORGANIZATION_SYSTEM_ERROR);
        }
        if(organizationSearch.getFromDate() == null){
            organizationSearch.setFromDate(LocalDate.of(1900, 1, 1));
        }
        if(organizationSearch.getToDate()== null){
            organizationSearch.setToDate(LocalDate.now());
        }
        if (organizationSearch.getFromDate().isAfter(organizationSearch.getToDate())) {
            throw new ApiException(AccountErrorCode.START_TIME_GREATER_THAN_END_TIME);
        }
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("updatedAt").descending());
        Page<OrganizationResponse> organizationResponsePage = organizationRepository.getOrganizationList(organizationSearch,pageable);
        return new ResponseFilter<>(
                organizationResponsePage.getContent(),
                (int) organizationResponsePage.getTotalElements(),
                organizationResponsePage.getSize(),
                organizationResponsePage.getNumber(),
                organizationResponsePage.getTotalPages()
        );
    }

    @Override
    public HashMap<String, String> createOrganization(OrganizationRequest organizationRequest) throws ApiException {
        Boolean checkOrganizationExistByName = organizationRepository.existsByName(organizationRequest.getName());
        if (checkOrganizationExistByName) {
            SystemErrorCode errorCode = SystemErrorCode.VALIDATE_FORM;

            HashMap<String, String> errorDetails = new HashMap<>();
            errorDetails.put("name", ResponseMessage.ORGANIZATION_NAME_SIZE);

            errorCode.setResult(Optional.of(errorDetails));
            throw new ApiException(errorCode);
        }

        Boolean checkOrganizationExistByCode = organizationRepository.existsByCode(organizationRequest.getCode());
        if (checkOrganizationExistByCode) {
            SystemErrorCode errorCode = SystemErrorCode.VALIDATE_FORM;

            HashMap<String, String> errorDetails = new HashMap<>();
            errorDetails.put("code", ResponseMessage.ORGANIZATION_CODE_SIZE);

            errorCode.setResult(Optional.of(errorDetails));
            throw new ApiException(errorCode);
        }

        Organizations organization = new Organizations();
        organization.setName(organizationRequest.getName());
        organization.setCode(organizationRequest.getCode());
        organization.setAddress(organizationRequest.getAddress());
        organization.setIsDefault(false);
        organizationRepository.save(organization);
        return new HashMap<>();
    }

    @Override
    public Organizations updateOrganization(Long organizationId, OrganizationRequest organizationRequest) throws ApiException {
        Optional<Organizations> organizationOptional = organizationRepository.findById(organizationId);
        if (organizationOptional.isEmpty()) {
            throw new ApiException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND);
        }

        if (organizationOptional.get().getIsDefault()) {
            throw new ApiException(OrganizationErrorCode.ORGANIZATION_DEFAULT);
        }

        if (!(organizationRequest.getCode().equals(organizationOptional.get().getCode()))) {
            Boolean checkOrganizationExistByCode = organizationRepository.existsByCode(organizationRequest.getCode());
            if (checkOrganizationExistByCode) {
                throw new ApiException(OrganizationErrorCode.ORGANIZATION_CODE_EXIST);
            }
        }

        if (!(organizationRequest.getName().equals(organizationOptional.get().getName()))) {
            Boolean checkOrganizationExistByName = organizationRepository.existsByName(organizationRequest.getName());
            if (checkOrganizationExistByName) {
                throw new ApiException(OrganizationErrorCode.ORGANIZATION_NAME_EXIST);
            }
        }

        organizationOptional.get().setName(organizationRequest.getName());
        organizationOptional.get().setCode(organizationRequest.getCode());
        organizationOptional.get().setAddress(organizationRequest.getAddress());

        return organizationRepository.save(organizationOptional.get());
    }

    @Override
    public OrganizationResponse getOrganizationDetail(Long organizationId) throws ApiException {
        Optional<Organizations> organizationOptional = organizationRepository.findById(organizationId);
        if(organizationOptional.isEmpty()){
            throw new ApiException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND);
        }

        OrganizationResponse organizationResponse  = OrganizationResponse.builder()
                .id(organizationOptional.get().getId())
                .name(organizationOptional.get().getName())
                .code(organizationOptional.get().getCode())
                .address(organizationOptional.get().getAddress())
                .build();
        return organizationResponse;
    }

    @Override
    public HashMap<String, String> deleteOrganization(Long organizationId) throws ApiException {
        Optional<Organizations> organization = organizationRepository.findById(organizationId);
        if(organization.isEmpty()){
            throw new ApiException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND);
        }
        if(organization.get().getIsDefault()){
            throw new ApiException(OrganizationErrorCode.ORGANIZATION_DEFAULT);
        }
        organizationRepository.deleteById(organizationId);
        return new HashMap<>();
    }
}

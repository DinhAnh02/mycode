package vn.eledevo.vksbe.service.case_status;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.eledevo.vksbe.constant.CaseStatusCode;
import vn.eledevo.vksbe.constant.ErrorCodes.CaseStatusErrorCode;
import vn.eledevo.vksbe.dto.request.case_status.CaseStatusCreateRequest;
import vn.eledevo.vksbe.dto.request.case_status.CaseStatusGetRequest;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.case_status.CaseStatusResponse;

import vn.eledevo.vksbe.entity.CaseStatus;
import vn.eledevo.vksbe.entity.Cases;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.mapper.CaseStatusMapper;
import vn.eledevo.vksbe.repository.CaseRepository;
import vn.eledevo.vksbe.repository.CaseStatusRepository;

import java.util.*;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CaseStatusServiceImpl implements CaseStatusService {
    CaseStatusRepository caseStatusRepository;
    CaseStatusMapper caseStatusMapper;
    CaseRepository caseRepository;

    @Override
    public ResponseFilter<CaseStatusResponse> getCaseStatus(CaseStatusGetRequest caseStatusGetRequest, Integer page, Integer pageSize) throws ApiException {
        Pageable pageable =
                PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        Page<CaseStatusResponse> caseStatusResponsePage = caseStatusRepository
                .getCaseStatus(
                        caseStatusGetRequest.getName(),
                        caseStatusGetRequest.getFromDate(),
                        caseStatusGetRequest.getToDate(),
                        pageable);
        return new ResponseFilter<>(
                caseStatusResponsePage.getContent(),
                (int) caseStatusResponsePage.getTotalElements(),
                caseStatusResponsePage.getSize(),
                caseStatusResponsePage.getNumber(),
                caseStatusResponsePage.getTotalPages());
    }

    @Override
    public HashMap<String, String> createCaseStatus(CaseStatusCreateRequest caseStatusCreateRequest) throws ApiException {
        if (caseStatusRepository.existsByName(caseStatusCreateRequest.getName())) {
            throw new ApiException(CaseStatusErrorCode.CASE_STATUS_NAME_ALREADY_EXIST);
        }
        CaseStatus caseStatus = CaseStatus.builder()
                .name(caseStatusCreateRequest.getName())
                .description(caseStatusCreateRequest.getDescription())
                .build();
        caseStatusRepository.save(caseStatus);
        return new HashMap<>();
    }

    @Override
    public HashMap<String, String> updateCaseStatus(Long id, CaseStatusCreateRequest caseStatusCreateRequest) throws ApiException {
        Optional<CaseStatus> caseStatusOptional = caseStatusRepository.findById(id);

        if(caseStatusOptional.isEmpty()){
            throw new ApiException(CaseStatusErrorCode.CASE_STATUS_NOT_FOUND);
        }
        if (!caseStatusCreateRequest.getName().equals(caseStatusOptional.get().getName()) && caseStatusRepository.existsByName(caseStatusCreateRequest.getName())){
            throw new ApiException(CaseStatusErrorCode.CASE_STATUS_NAME_ALREADY_EXIST );
        }
        CaseStatus caseStatus = caseStatusOptional.get();
        caseStatus.setName(caseStatusCreateRequest.getName());
        if (Objects.nonNull(caseStatusCreateRequest.getDescription())) {
            caseStatus.setDescription(caseStatusCreateRequest.getDescription());
        }

        caseStatusRepository.save(caseStatus);
        return new HashMap<>();
    }


    @Override
    public CaseStatusResponse getCaseStatusDetail(Long caseStatusId) throws ApiException {
        CaseStatus existingCaseStatus = caseStatusRepository
                .findById(caseStatusId)
                .orElseThrow(() -> new ApiException(CaseStatusErrorCode.CASE_STATUS_NOT_FOUND));

        if (existingCaseStatus.getCases().isEmpty()) {
            throw new ApiException(CaseStatusErrorCode.CASE_STATUS_NOT_FOUND_IN_CASE);
        }

        return CaseStatusResponse.builder()
                .id(existingCaseStatus.getId())
                .name(existingCaseStatus.getName())
                .description(existingCaseStatus.getDescription())
                .build();
    }


    private CaseStatus getCaseStatusById(Long id) throws ApiException {
        return caseStatusRepository.findById(id).orElseThrow(() -> new ApiException(CaseStatusErrorCode.CASE_STATUS_NOT_FOUND));
    }

    private CaseStatus getCaseStatusByCode(String code) throws ApiException {
        return caseStatusRepository.findByCode(code).orElseThrow(() -> new ApiException(CaseStatusErrorCode.CASE_STATUS_DEFAULT_EMPTY));
    }

    @Override
    @Transactional
    public HashMap<String, String> deleteCaseStatusById(Long id) throws ApiException {
        CaseStatus caseStatus = getCaseStatusById(id);
        if (Boolean.TRUE.equals(caseStatus.getIsDefault())) {
            throw new ApiException(CaseStatusErrorCode.CASE_STATUS_IS_DEFAULT);
        }
        List<Cases> cases = caseStatus.getCases();
        if (!cases.isEmpty()) {
            CaseStatus statusDefault = getCaseStatusByCode(CaseStatusCode.CASE_INITIALIZATION.name());
            cases.forEach(casesItem -> casesItem.setCase_status(statusDefault));
            caseRepository.saveAll(cases);
        }
        caseStatus.setCases(null);
        caseStatusRepository.delete(caseStatus);
        return new HashMap<>();
    }
}

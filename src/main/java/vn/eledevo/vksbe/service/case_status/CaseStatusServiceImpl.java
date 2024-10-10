package vn.eledevo.vksbe.service.case_status;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.eledevo.vksbe.constant.ErrorCodes.CaseStatusErrorCode;
import vn.eledevo.vksbe.dto.request.CaseStatus.CaseStatusCreateRequest;
import vn.eledevo.vksbe.dto.request.case_status.CaseStatusGetRequest;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.case_status.CaseStatusResponse;

import vn.eledevo.vksbe.entity.CaseStatus;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.mapper.CaseStatusMapper;
import vn.eledevo.vksbe.repository.CaseStatusRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CaseStatusServiceImpl implements CaseStatusService {
    CaseStatusRepository caseStatusRepository;
    CaseStatusMapper caseStatusMapper;

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
}

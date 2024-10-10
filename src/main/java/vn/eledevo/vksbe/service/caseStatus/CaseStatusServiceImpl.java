package vn.eledevo.vksbe.service.caseStatus;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.eledevo.vksbe.constant.ErrorCodes.CaseStatusErrorCode;
import vn.eledevo.vksbe.dto.request.CaseStatus.CaseStatusCreateRequest;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.entity.CaseStatus;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.repository.CaseStatusRepository;
import vn.eledevo.vksbe.utils.SecurityUtils;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CaseStatusServiceImpl implements CaseStatusService {
    CaseStatusRepository caseStatusRepository;

    @Override
    public HashMap<String, String> createCaseStatus(CaseStatusCreateRequest caseStatusCreateRequest) throws ApiException {
       if(caseStatusRepository.existsByName(caseStatusCreateRequest.getName())) {
           throw new ApiException(CaseStatusErrorCode.CASE_STATUS_NAME_ALREADY_EXIST );
       }
        CaseStatus caseStatus = CaseStatus.builder()
                .name(caseStatusCreateRequest.getName())
                .description(caseStatusCreateRequest.getDescription())
                .build();
        caseStatusRepository.save(caseStatus);
       return new HashMap<>();
    }
}

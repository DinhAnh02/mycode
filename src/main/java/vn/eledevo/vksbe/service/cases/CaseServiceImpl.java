package vn.eledevo.vksbe.service.cases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.eledevo.vksbe.constant.ErrorCodes.CaseErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.constant.Role;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.entity.Cases;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.CaseRepository;
import vn.eledevo.vksbe.repository.CitizenRepository;
import vn.eledevo.vksbe.utils.SecurityUtils;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {
    CaseRepository caseRepository;
    CitizenRepository citizenRepository;

    private Cases getCaseById(Long id) throws ApiException {
        return caseRepository.findById(id).orElseThrow(() -> new ApiException(CaseErrorCode.CASE_NOT_FOUND));
    }

    private boolean isReadDataDepartment(Accounts accounts, Cases cases) {
        if (accounts.getRoles().getCode().equals(Role.VIEN_TRUONG.name())
                || accounts.getRoles().getCode().equals(Role.VIEN_PHO.name())) {
            return true;
        }

        return accounts.getDepartments().getId().equals(cases.getDepartments().getId());
    }

    @Override
    public ResponseFilter<CitizenCaseResponse> getAllInvestigatorByCaseId(
            Long id, String textSearch, int page, int pageSize) throws ApiException {
        Cases cases = getCaseById(id);
        Accounts loginAccount = SecurityUtils.getUser();
        if (!isReadDataDepartment(loginAccount, cases)) {
            throw new ApiException(SystemErrorCode.ORGANIZATION_STRUCTURE);
        }

        if (page < 1 || pageSize < 1) {
            throw new ApiException(SystemErrorCode.PAGE_NOT_VALID);
        }

        Pageable pageable =
                PageRequest.of(page - 1, pageSize, Sort.by("updatedAt").descending());
        Page<CitizenCaseResponse> pages =
                citizenRepository.searchAllInvestigatorByCaseId(cases.getId(), textSearch, pageable);
        return new ResponseFilter<>(
                pages.getContent(), pages.getContent().size(), pages.getSize(), page, pages.getTotalPages());
    }
}

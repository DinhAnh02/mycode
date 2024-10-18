package vn.eledevo.vksbe.service.cases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.eledevo.vksbe.constant.CasePersonType;
import vn.eledevo.vksbe.constant.CaseStatusCode;
import vn.eledevo.vksbe.constant.ErrorCodes.*;
import vn.eledevo.vksbe.constant.Role;
import vn.eledevo.vksbe.dto.request.cases.CaseCreateRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseUpdateRequest;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.cases.CaseInfomationResponse;
import vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse;
import vn.eledevo.vksbe.entity.*;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.*;
import vn.eledevo.vksbe.utils.SecurityUtils;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {
    CaseRepository caseRepository;
    CitizenRepository citizenRepository;
    AccountCaseRepository accountCaseRepository;
    CaseStatusRepository caseStatusRepository;
    AccountRepository accountRepository;
    DepartmentRepository departmentRepository;

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

    private ResponseFilter<CitizenCaseResponse> genderResponseFilterCitizenCaseResponse(
            Long id, String textSearch, int page, int pageSize, List<String> types) throws ApiException {
        Cases cases = getCaseById(id);
        Accounts loginAccount = SecurityUtils.getUser();
        if (!isReadDataDepartment(loginAccount, cases)) {
            throw new ApiException(SystemErrorCode.AUTHENTICATION_SERVER);
        }

        if (page < 1 || pageSize < 1) {
            throw new ApiException(SystemErrorCode.BAD_REQUEST_SERVER);
        }

        Pageable pageable =
                PageRequest.of(page - 1, pageSize, Sort.by("updatedAt").descending());

        Page<CitizenCaseResponse> pages =
                citizenRepository.searchAllInvestigatorByCaseId(cases.getId(), textSearch, types, pageable);
        return new ResponseFilter<>(
                pages.getContent(), pages.getContent().size(), pages.getSize(), page, pages.getTotalPages());
    }

    @Override
    public ResponseFilter<CitizenCaseResponse> getAllInvestigatorByCaseId(
            Long id, String textSearch, int page, int pageSize) throws ApiException {
        List<String> types = List.of(CasePersonType.INVESTIGATOR.name());
        return genderResponseFilterCitizenCaseResponse(id, textSearch, page, pageSize, types);
    }

    @Override
    public ResponseFilter<CitizenCaseResponse> getAllSuspectDefendantByCaseId(
            Long id, String textSearch, int page, int pageSize) throws ApiException {
        List<String> types = List.of(CasePersonType.SUSPECT.name(), CasePersonType.DEFENDANT.name());
        return genderResponseFilterCitizenCaseResponse(id, textSearch, page, pageSize, types);
    }

    @Override
    public CaseInfomationResponse getCaseInfomationDetail(Long id) throws ApiException {
        Cases cases = getCaseById(id);
        Accounts loginAccount = SecurityUtils.getUser();

        validateAccountPermission(loginAccount, cases);
        Accounts account = accountRepository.findAccountsByUsername(cases.getCreatedBy());
        String createdByNew = account.getProfile().getFullName() + " - " + account.getUsername();
        return CaseInfomationResponse.builder()
                .id(cases.getId())
                .name(cases.getName())
                .code(cases.getCode())
                .departmentId(cases.getDepartments().getId())
                .departmentName(cases.getDepartments().getName())
                .statusName(cases.getCase_status().getName())
                .statusId(cases.getCase_status().getId())
                .createAt(cases.getCreatedAt())
                .type(cases.getCaseType())
                .description(cases.getDescription())
                .createdBy(createdByNew)
                .build();
    }

    private AccountCase getAccountCaseByAccountIdAndCaseId(Long accountId, Long caseId) throws ApiException {
        return accountCaseRepository
                .findAccountCaseByAccounts_IdAndCases_Id(accountId, caseId)
                .orElseThrow(() -> new ApiException(SystemErrorCode.UNAUTHORIZED_SERVER));
    }

    private CaseStatus getCaseStatusById(Long id) throws ApiException {
        return caseStatusRepository
                .findById(id)
                .orElseThrow(() -> new ApiException(CaseStatusErrorCode.CASE_STATUS_NOT_FOUND));
    }

    private boolean isUserAuthorized(Accounts accounts, Cases cases) {
        if (accounts.getRoles().getCode().equals(Role.VIEN_TRUONG.name())
                || accounts.getRoles().getCode().equals(Role.VIEN_PHO.name())) {
            return true;
        }
        if (!accounts.getDepartments().getId().equals(cases.getDepartments().getId())) {
            return false;
        }

        return accounts.getRoles().getCode().equals(Role.TRUONG_PHONG.name());
    }

    private boolean isReadDataAccountCase(AccountCase accountCase) {
        Accounts accounts = accountCase.getAccounts();

        Cases cases = accountCase.getCases();
        if (!accounts.getDepartments().getId().equals(cases.getDepartments().getId())) {
            return false;
        }

        return Boolean.TRUE.equals(accountCase.getHasAccess());
    }

    private void validateAccountPermission(Accounts accounts, Cases cases) throws ApiException {
        if (!isUserAuthorized(accounts, cases)) {
            AccountCase accountCase = getAccountCaseByAccountIdAndCaseId(accounts.getId(), cases.getId());
            if (!isReadDataAccountCase(accountCase)) {
                throw new ApiException(SystemErrorCode.UNAUTHORIZED_SERVER);
            }
        }
    }

    private void updateCaseName(CaseUpdateRequest request, Cases cases) throws ApiException {
        if (request.getName() != null && !request.getName().equals(cases.getName())) {
            if (caseRepository.existsByName(request.getName())) {
                throw new ApiException(CaseErrorCode.CASE_EXISTED);
            }
            cases.setName(request.getName());
        }
    }

    private void updateCaseCode(CaseUpdateRequest request, Cases cases) throws ApiException {
        if (request.getCode() != null && !request.getCode().equals(cases.getCode())) {
            if (caseRepository.existsByCode(request.getCode())) {
                throw new ApiException(CaseErrorCode.CASE_CODE_EXISTED);
            }
            cases.setCode(request.getCode());
        }
    }

    private void updateCaseStatus(CaseUpdateRequest request, Cases cases) throws ApiException {
        if (request.getStatusId() != null) {
            CaseStatus caseStatus = getCaseStatusById(request.getStatusId());
            cases.setCase_status(caseStatus);
        }
    }

    private void updateCaseType(CaseUpdateRequest request, Cases cases) {
        if (request.getType() != null) {
            cases.setCaseType(request.getType());
        }
    }

    private void updateCaseDescription(CaseUpdateRequest request, Cases cases) {
        if (request.getDescription() != null) {
            cases.setDescription(request.getDescription());
        }
    }

    @Override
    public HashMap<String, String> updateCase(Long id, CaseUpdateRequest request) throws ApiException {
        Cases cases = getCaseById(id);
        Accounts accounts = SecurityUtils.getUser();

        validateAccountPermission(accounts, cases);

        updateCaseName(request, cases);
        updateCaseCode(request, cases);
        updateCaseStatus(request, cases);
        updateCaseType(request, cases);
        updateCaseDescription(request, cases);
        caseRepository.save(cases);
        return new HashMap<>();
    }

    private boolean hasPermissionCreateCase(Accounts accounts, CaseCreateRequest caseCreateRequest) {
        if (accounts.getRoles().getCode().equals(Role.VIEN_TRUONG.name())
                || accounts.getRoles().getCode().equals(Role.VIEN_PHO.name())) {
            return true;
        }
        if (accounts.getRoles().getCode().equals(Role.TRUONG_PHONG.name())) {
            return accounts.getDepartments().getId().equals(caseCreateRequest.getDepartmentId());
        }
        if (accounts.getRoles().getCode().equals(Role.PHO_PHONG.name())
                || accounts.getRoles().getCode().equals(Role.KIEM_SAT_VIEN.name())) {
            return accounts.getDepartments().getId().equals(caseCreateRequest.getDepartmentId())
                    && accounts.getIsCreateCase();
        }
        return false;
    }

    @Override
    public HashMap<String, Long> createCase(CaseCreateRequest caseCreateRequest) throws ApiException {
        Accounts accounts = SecurityUtils.getUser();
        Departments departments = departmentRepository
                .findById(caseCreateRequest.getDepartmentId())
                .orElseThrow(() -> new ApiException(DepartmentErrorCode.DEPARTMENT_NOT_FOUND));
        if (!caseCreateRequest.getDepartmentName().equals(departments.getName())) {
            throw new ApiException(SystemErrorCode.ORGANIZATION_STRUCTURE);
        }
        if (!hasPermissionCreateCase(accounts, caseCreateRequest)) {
            throw new ApiException(SystemErrorCode.NOT_ENOUGH_PERMISSION);
        }
        boolean caseCodeExist =
                caseRepository.existsByCode(caseCreateRequest.getCode().trim());
        if (caseCodeExist) {
            throw new ApiException(CaseErrorCode.CASE_CODE_EXISTED);
        }
        CaseStatus caseStatus = caseStatusRepository
                .findByCode(CaseStatusCode.CASE_INITIALIZATION.name())
                .orElseThrow(() -> new ApiException(CaseStatusErrorCode.CASE_STATUS_NOT_FOUND));
        Cases cases = Cases.builder()
                .name(caseCreateRequest.getName().trim())
                .code(caseCreateRequest.getCode().trim())
                .departments(departments)
                .caseType(caseCreateRequest.getCaseType().name())
                .case_status(caseStatus)
                .description(caseCreateRequest.getDescription().trim())
                .build();
        caseRepository.save(cases);
        AccountCase accountCase;
        if (accounts.getRoles().getCode().equals(Role.VIEN_TRUONG.name())
                || accounts.getRoles().getCode().equals(Role.VIEN_PHO.name())
                || accounts.getRoles().getCode().equals(Role.TRUONG_PHONG.name())) {
            accountCase = AccountCase.builder()
                    .hasAccess(true)
                    .accountRole(accounts.getRoles().getCode())
                    .isProsecutor(false)
                    .isInCharge(true)
                    .accounts(accounts)
                    .cases(cases)
                    .build();
        } else {
            accountCase = AccountCase.builder()
                    .hasAccess(true)
                    .accountRole(accounts.getRoles().getCode())
                    .isProsecutor(true)
                    .isInCharge(false)
                    .accounts(accounts)
                    .cases(cases)
                    .build();
        }
        accountCaseRepository.save(accountCase);
        return new HashMap<>(Map.of("id", cases.getId()));
    }
}

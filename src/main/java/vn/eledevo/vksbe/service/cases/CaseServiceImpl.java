package vn.eledevo.vksbe.service.cases;

import java.time.LocalDate;
import java.util.*;

import org.checkerframework.checker.units.qual.A;
import io.jsonwebtoken.lang.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.eledevo.vksbe.constant.*;
import vn.eledevo.vksbe.constant.ErrorCodes.*;
import vn.eledevo.vksbe.constant.ErrorCodes.AccountErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.dto.request.cases.CaseCreateRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseFilterRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseUpdateRequest;
import vn.eledevo.vksbe.dto.request.document.DocumentFolderCreationRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseCitizenRequest;
import vn.eledevo.vksbe.dto.request.cases.CaseCitizenUpdateRequest;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.cases.CaseFilterResponse;
import vn.eledevo.vksbe.dto.response.cases.CaseInfomationResponse;
import vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse;
import vn.eledevo.vksbe.entity.*;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.*;
import vn.eledevo.vksbe.repository.AccountCaseRepository;
import vn.eledevo.vksbe.repository.CasePersonRepository;
import vn.eledevo.vksbe.repository.CaseRepository;
import vn.eledevo.vksbe.repository.CitizenRepository;
import vn.eledevo.vksbe.utils.SecurityUtils;
import vn.eledevo.vksbe.constant.ErrorCodes.AccountErrorCode;
import vn.eledevo.vksbe.constant.RoleCodes;
import vn.eledevo.vksbe.dto.response.account.AccountFilterCaseResponse;
import vn.eledevo.vksbe.entity.Roles;
import vn.eledevo.vksbe.repository.AccountCaseRepository;

import java.util.Optional;
import vn.eledevo.vksbe.constant.ErrorCodes.AccountErrorCode;
import vn.eledevo.vksbe.constant.RoleCodes;
import vn.eledevo.vksbe.dto.response.account.AccountFilterCaseResponse;
import vn.eledevo.vksbe.entity.Cases;
import vn.eledevo.vksbe.entity.Roles;
import vn.eledevo.vksbe.repository.AccountCaseRepository;
import vn.eledevo.vksbe.repository.CaseRepository;

import java.util.Optional;

import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.AccountErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;

import java.util.stream.Collectors;

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
    DocumentRepository documentRepository;
    CasePersonRepository casePersonRepository;


    private Cases getCaseById(Long id) throws ApiException {
        return caseRepository.findById(id).orElseThrow(() -> new ApiException(CaseErrorCode.CASE_NOT_FOUND));
    }

    private boolean isNotReadDataDepartment(Accounts accounts, Cases cases) {
        if (accounts.getRoles().getCode().equals(Role.VIEN_TRUONG.name())
                || accounts.getRoles().getCode().equals(Role.VIEN_PHO.name())) {
            return false;
        }

        return !accounts.getDepartments().getId().equals(cases.getDepartments().getId());
    }

    private ResponseFilter<CitizenCaseResponse> genderResponseFilterCitizenCaseResponse(
            Long id, String textSearch, int page, int pageSize, List<String> types) throws ApiException {
        Cases cases = getCaseById(id);
        Accounts loginAccount = SecurityUtils.getUser();
        if (isNotReadDataDepartment(loginAccount, cases)) {
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
                .orElseThrow(() -> new ApiException(SystemErrorCode.INTERNAL_SERVER));
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

    @Override
    public ResponseFilter<CaseFilterResponse> getCaseByFilter(
            CaseFilterRequest caseFilterRequest, Integer currentPage, Integer limit) throws ApiException {
        Accounts accounts = SecurityUtils.getUser();
        Optional<Departments> departments = departmentRepository.findDepartmentsByIdAndName(
                caseFilterRequest.getDepartmentId(), caseFilterRequest.getDepartmentName());

        if (caseFilterRequest.getDepartmentId() != null
                && caseFilterRequest.getDepartmentId() != 0
                && !departments.isPresent()) {
            throw new ApiException(SystemErrorCode.ORGANIZATION_STRUCTURE);
        }
        if (currentPage < 1 || limit < 1) {
            throw new ApiException(SystemErrorCode.BAD_REQUEST_SERVER);
        }
        if (caseFilterRequest.getTextSearch() == null
                || caseFilterRequest.getTextSearch().isBlank()) {
            caseFilterRequest.setTextSearch(null);
        } else {
            caseFilterRequest.setTextSearch(caseFilterRequest.getTextSearch().toLowerCase());
        }
        if (caseFilterRequest.getUserInChargeId() == null || caseFilterRequest.getUserInChargeId() == 0) {
            caseFilterRequest.setUserInChargeId(null);
        }
        if (caseFilterRequest.getProsecutorId() == null || caseFilterRequest.getProsecutorId() == 0) {
            caseFilterRequest.setProsecutorId(null);
        }
        if (caseFilterRequest.getCitizenId() == null || caseFilterRequest.getCitizenId() == 0) {
            caseFilterRequest.setCitizenId(null);
        }
        if (caseFilterRequest.getStatusId() == null || caseFilterRequest.getStatusId() == 0) {
            caseFilterRequest.setStatusId(null);
        }
        if (caseFilterRequest.getFromDate() == null) {
            caseFilterRequest.setFromDate(LocalDate.of(1900, 1, 1));
        }
        if (caseFilterRequest.getToDate() == null) {
            caseFilterRequest.setToDate(LocalDate.now());
        }
        if (caseFilterRequest.getFromDate().isAfter(caseFilterRequest.getToDate())) {
            throw new ApiException(CaseErrorCode.START_TIME_GREATER_THAN_END_TIME);
        }
        if (Objects.equals(accounts.getRoles().getCode(), Role.TRUONG_PHONG.name())
                || Objects.equals(accounts.getRoles().getCode(), Role.PHO_PHONG.name())
                || Objects.equals(accounts.getRoles().getCode(), Role.KIEM_SAT_VIEN.name())) {
            if (!Objects.equals(accounts.getDepartments().getId(), caseFilterRequest.getDepartmentId())) {
                throw new ApiException(CaseErrorCode.CASE_NOT_ACCESS);
            }
        } else {
            if (caseFilterRequest.getDepartmentId() == null || caseFilterRequest.getDepartmentId() == 0) {
                caseFilterRequest.setDepartmentId(null);
            }
        }

        Boolean hasAccess;
        if (Objects.equals(accounts.getRoles().getCode(), Role.PHO_PHONG.name())
                || Objects.equals(accounts.getRoles().getCode(), Role.KIEM_SAT_VIEN.name())) {
            hasAccess = true;
        } else {
            hasAccess = null;
        }
        Pageable pageable =
                PageRequest.of(currentPage - 1, limit, Sort.by("updatedAt").descending());

        Page<CaseFilterResponse> page = caseRepository.getCaseFilter(
                caseFilterRequest.getTextSearch(),
                caseFilterRequest.getUserInChargeId(),
                caseFilterRequest.getProsecutorId(),
                caseFilterRequest.getFromDate(),
                caseFilterRequest.getToDate(),
                caseFilterRequest.getDepartmentId(),
                caseFilterRequest.getStatusId(),
                caseFilterRequest.getCitizenId(),
                hasAccess,
                accounts.getId(),
                pageable);

        return new ResponseFilter<>(
                page.getContent(),
                (int) page.getTotalElements(),
                page.getSize(),
                page.getNumber(),
                page.getTotalPages());
    }

    private Documents getDocumentById(Long id) throws ApiException {
        return documentRepository
                .findById(id)
                .orElseThrow(() -> new ApiException(DocumentErrorCode.DOCUMENT_NOT_FOUND));
    }

    @Override
    public HashMap<String, String> createDocumentFolder(Long caseId, DocumentFolderCreationRequest request)
            throws ApiException {
        Accounts accounts = SecurityUtils.getUser();
        Cases cases = getCaseById(caseId);
        if (isNotReadDataDepartment(accounts, cases)) {
            throw new ApiException(SystemErrorCode.AUTHENTICATION_SERVER);
        }
        Documents document = getDocumentById(request.getParentId());
        if (!document.getType().equals(TypeDocument.FOLDER.name())) {
            throw new ApiException(SystemErrorCode.BAD_REQUEST_SERVER);
        }
        Documents documentCreation = Documents.builder()
                .name(request.getName())
                .type(TypeDocument.FOLDER.name())
                .parentId(document)
                .documentType(document.getDocumentType())
                .cases(cases)
                .build();
        documentRepository.save(documentCreation);
        return new HashMap<>();
    }

    @Override
    public ResponseFilter<AccountFilterCaseResponse> getCaseProsecutorList(
            String textSearch, Long page, Long pageSize, Long caseId) throws ApiException {
        Long userLoggedDepartmentId = SecurityUtils.getDepartmentId();
        Roles userLoggedRole = SecurityUtils.getRole();
        Long userLoggedId = SecurityUtils.getUserId();

        if (page < 1 || pageSize < 1) {
            throw new ApiException(SystemErrorCode.BAD_REQUEST_SERVER);
        }
        if (textSearch == null || textSearch.isBlank()) {
            textSearch = null;
        }

        Optional<Cases> cases = caseRepository.findById(caseId);
        if (cases.isEmpty()) {
            throw new ApiException(CaseErrorCode.CASE_NOT_FOUND);
        }

        boolean checkUserDepartmentIsInDepartmentCase = userLoggedDepartmentId.equals(cases.get().getDepartments().getId());

        if (!checkUserDepartmentIsInDepartmentCase
                && !userLoggedRole.getCode().equals(RoleCodes.VIEN_TRUONG)
                && !userLoggedRole.getCode().equals(RoleCodes.VIEN_PHO)) {
            throw new ApiException(SystemErrorCode.NOT_ENOUGH_PERMISSION);
        }

        if (userLoggedRole.getCode().equals(RoleCodes.PHO_PHONG)
                || userLoggedRole.getCode().equals(RoleCodes.KIEM_SAT_VIEN)) {
            boolean existsAccountCaseByAccountIdAndHasAccessTrue =
                    accountCaseRepository.existsAccountCaseByAccountIdAndHasAccessTrue(userLoggedId, caseId);
            if (!existsAccountCaseByAccountIdAndHasAccessTrue) {
                throw new ApiException(SystemErrorCode.NOT_ENOUGH_PERMISSION);
            }
        }

        Pageable pageable = PageRequest.of(page.intValue() - 1, pageSize.intValue());

        Long departmentId = cases.get().getDepartments().getId();

        Page<AccountFilterCaseResponse> accountFilterCaseResponses
                = caseRepository.getCaseProsecutorList(textSearch, caseId, departmentId, pageable);

        for(AccountFilterCaseResponse item : accountFilterCaseResponses) {
            if (item.getIsAssigned() == null) {
                item.setIsAssigned(false);
            }
        }

        int numberOfProsecutorInCase = accountCaseRepository.countAccountCaseByIsProsecutorTrue(caseId);

        return new ResponseFilter<>(
                accountFilterCaseResponses.getContent(),
                (int) accountFilterCaseResponses.getTotalElements(),
                accountFilterCaseResponses.getSize(),
                accountFilterCaseResponses.getNumber(),
                accountFilterCaseResponses.getTotalPages(),
                numberOfProsecutorInCase
        );
    }

    @Override
    public ResponseFilter<AccountFilterCaseResponse> getUserInChargeList(String textSearch, Long caseId, Integer page, Integer pageSize) throws ApiException {
        if (page < 1 || pageSize < 1) {
            throw new ApiException(SystemErrorCode.BAD_REQUEST_SERVER);
        }
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Optional<Cases> cases = caseRepository.findById(caseId);
        if (cases.isEmpty()) {
            throw new ApiException(CaseErrorCode.CASE_NOT_FOUND);
        }
        Accounts userInfo = SecurityUtils.getUser();

        if(!checkUserPermissionToGetList(userInfo, cases.get())){
            throw new ApiException(SystemErrorCode.NOT_ENOUGH_PERMISSION);
        }

        if (textSearch == null || textSearch.isBlank()) {
            textSearch = null;
        }
        Page<AccountFilterCaseResponse> inChargeList = caseRepository.getUserInChargeList(textSearch, caseId, cases.get().getDepartments().getId(), pageable);
        for (AccountFilterCaseResponse filterCaseResponse : inChargeList.getContent()) {
            if (filterCaseResponse.getIsAssigned() == null) {
                filterCaseResponse.setIsAssigned(false);
            }
        }

        int countAssigner = accountCaseRepository.countAccountCaseByIsInChargeTrue(caseId);
        int totalElements = (int) inChargeList.getTotalElements();

        return new ResponseFilter<>(
                inChargeList.getContent(),
                totalElements,
                inChargeList.getSize(),
                inChargeList.getNumber(),
                inChargeList.getTotalPages(),
                countAssigner);
    }

    private boolean checkUserPermissionToGetList(Accounts userInfo, Cases caseIn) throws ApiException {
        if(userInfo.getRoles().getCode().equals(RoleCodes.VIEN_TRUONG) || userInfo.getRoles().getCode().equals(RoleCodes.VIEN_PHO)){
            return true;
        }
        if(userInfo.getDepartments().getId().equals(caseIn.getDepartments().getId())){
            if(userInfo.getRoles().getCode().equals(RoleCodes.TRUONG_PHONG)){
                return true;
            }
            if(userInfo.getRoles().getCode().equals(RoleCodes.PHO_PHONG) || userInfo.getRoles().getCode().equals(RoleCodes.KIEM_SAT_VIEN)){
                if (!accountCaseRepository.existsAccountCaseByAccountIdAndHasAccessTrue(userInfo.getId(), caseIn.getId())) {
                    throw new ApiException(CaseErrorCode.CASE_NOT_ACCESS);
                }else {
                    return true;
                }
            }
        }else{
            throw new ApiException(SystemErrorCode.ORGANIZATION_STRUCTURE);
        }
        return false;
    }

    @Override
    public HashMap<String, String> updateInvestigator(Long id, CaseCitizenUpdateRequest request) throws ApiException {
        Accounts accounts = SecurityUtils.getUser();
        Cases cases = getCaseById(id);
        if (!accounts.getRoles().getCode().equals(Role.VIEN_TRUONG.name())
                && !(accounts.getRoles().getCode().equals(Role.VIEN_PHO.name()))) {

            if (accounts.getRoles().getCode().equals(Role.TRUONG_PHONG.name())) {
                if (!cases.getDepartments().getId().equals(accounts.getDepartments().getId())) {
                    throw new ApiException(CaseErrorCode.CASE_ACCESS_DENIED);
                }
            }

            if (accounts.getRoles().getCode().equals(Role.PHO_PHONG.name()) || accounts.getRoles().getCode().equals(Role.KIEM_SAT_VIEN.name())) {
                if (!cases.getDepartments().getId().equals(accounts.getDepartments().getId())) {
                    throw new ApiException(CaseErrorCode.CASE_ACCESS_DENIED);
                }
                if (!Boolean.TRUE.equals(accounts.getIsCreateCase())) {
                    throw new ApiException(CaseErrorCode.CASE_EDIT_PERMISSION_DENIED);
                }
                Optional<AccountCase> accountCases = accountCaseRepository
                        .getListAccountCase(accounts.getId(), cases.getId());
                if (accountCases.isEmpty()) {
                    throw new ApiException(CaseErrorCode.CASE_ACCESS_DENIED);
                }
            }
        }

        List<Long> isCheckedTrue = request.getRequests().stream()
                .filter(CaseCitizenRequest::getIsChecked)
                .map(CaseCitizenRequest::getId)
                .toList();

        if (!Collections.isEmpty(isCheckedTrue)) {
            List<Citizens> citizens = citizenRepository.findAllById(isCheckedTrue);
            if (citizens.size() != isCheckedTrue.size()) {
                throw new ApiException(CaseErrorCode.CASE_CITIZEN_NOT_FOUND_IN_LIST);
            }

            List<Citizens> citizensList = citizenRepository.findAllById(isCheckedTrue);

            List<CasePerson> casePersons = citizensList.stream()
                    .map(citizen -> CasePerson.builder()
                            .type(CasePersonType.INVESTIGATOR.name())
                            .cases(cases)
                            .citizens(citizen)
                            .build())
                    .collect(Collectors.toList());

            List<CasePerson> casePeople = casePersonRepository.findExistingCasePersons(CasePersonType.INVESTIGATOR.name(), id, isCheckedTrue);
            if (!Collections.isEmpty(casePeople)) {
                casePersons.removeIf(newCasePerson -> casePeople.stream()
                        .anyMatch(existingCasePerson ->
                                existingCasePerson.getCitizens().getId().equals(newCasePerson.getCitizens().getId())));
            }
            casePersonRepository.saveAll(casePersons);
        }

        List<Long> isCheckedFalse = request.getRequests().stream()
                .filter(caseCitizenRequest -> !caseCitizenRequest.getIsChecked())
                .map(CaseCitizenRequest::getId)
                .toList();
        if (!Collections.isEmpty(isCheckedFalse)) {
            casePersonRepository.deleteCitizenInCase(id, isCheckedFalse, CasePersonType.INVESTIGATOR.name());
        }

        return new HashMap<>();
    }
}

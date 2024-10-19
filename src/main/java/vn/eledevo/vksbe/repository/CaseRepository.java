package vn.eledevo.vksbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import vn.eledevo.vksbe.dto.response.cases.CaseFilterResponse;
import org.springframework.data.domain.Pageable;
import vn.eledevo.vksbe.dto.response.account.AccountFilterCaseResponse;
import vn.eledevo.vksbe.entity.Cases;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface CaseRepository extends BaseRepository<Cases, Long> {
    boolean existsByName(String name);

    boolean existsByCode(String code);

    @Transactional
    @Query("SELECT new vn.eledevo.vksbe.dto.response.cases.CaseFilterResponse(c.id, c.code, c.name, d.name, cs.name, c.updatedAt) "
            + "FROM Cases c "
            + "LEFT JOIN AccountCase ac ON ac.cases.id = c.id "
            + "LEFT JOIN AccountCase ap ON ap.cases.id = c.id "
            + "LEFT JOIN AccountCase am ON am.cases.id = c.id "
            + "LEFT JOIN CasePerson cp ON cp.cases.id = c.id "
            + "LEFT JOIN Departments d ON c.departments.id = d.id "
            + "LEFT JOIN CaseStatus cs ON cs.id = c.case_status.id "
            + "WHERE  ((COALESCE(:textSearch, NULL) IS NULL) OR LOWER(c.name) LIKE %:textSearch% OR LOWER(c.code) LIKE %:textSearch%) "
            + "AND ((COALESCE(:userInChargeId, NULL) IS NULL) OR (ac.accounts.id = :userInChargeId AND ac.isInCharge = true)) "
            + "AND ((COALESCE(:prosecutorId, NULL) IS NULL) OR (ap.accounts.id = :prosecutorId AND ap.isProsecutor = true)) "
            + "AND ((COALESCE(:citizenId, NULL) IS NULL) OR cp.citizens.id = :citizenId) "
            + "AND ((COALESCE(:fromDate, NULL) IS NULL) OR c.updatedAt >= :fromDate) "
            + "AND ((COALESCE(:toDate, NULL) IS NULL) OR c.updatedAt <= :toDate) "
            + "AND ((COALESCE(:departmentId, NULL) IS NULL) OR d.id = :departmentId) "
            + "AND ((COALESCE(:statusId, NULL) IS NULL) OR cs.id = :statusId) "
            + "AND ((COALESCE(:hasAccess, NULL) IS NULL) OR (am.accounts.id = :accoutId and am.hasAccess = true) OR (am.accounts.id = :accoutId and am.hasAccess = true)) "
            + "group by c.id, c.code, c.name, d.name, cs.name, c.updatedAt "
    )
    Page<CaseFilterResponse> getCaseFilter(
            String textSearch ,
            Long userInChargeId,
            Long prosecutorId,
            LocalDate fromDate,
            LocalDate toDate,
            Long departmentId,
            Long statusId,
            Long citizenId,
            Boolean hasAccess,
            Long accoutId,
            Pageable pageable);

    @Query(
            "SELECT new vn.eledevo.vksbe.dto.response.account.AccountFilterCaseResponse(" +
                    "a.id, a.username, p.fullName, p.avatar, r.name, p.gender, " +
                    "CASE WHEN ac.cases.id = :caseId THEN ac.isProsecutor ELSE false END) " +
                    "FROM Accounts a " +
                    "LEFT JOIN AccountCase ac ON a.id = ac.accounts.id AND ac.cases.id = :caseId " +
                    "JOIN Departments d ON a.departments.id = d.id " +
                    "LEFT JOIN Profiles p ON a.id = p.accounts.id " +
                    "JOIN Roles r ON a.roles.id = r.id " +
                    "WHERE (a.departments.id = :departmentId " +
                    "OR (r.code IN ('VIEN_TRUONG', 'VIEN_PHO') AND d.code = 'PB_LANH_DAO')) " +
                    "AND (:textSearch IS NULL " +
                    "OR LOWER(p.fullName) LIKE LOWER(CONCAT('%', :textSearch, '%')) " +
                    "OR LOWER(a.username) LIKE LOWER(CONCAT('%', :textSearch, '%'))) " +
                    "ORDER BY ac.isProsecutor DESC"
    )
    Page<AccountFilterCaseResponse> getCaseProsecutorList(
            String textSearch,
            Long caseId,
            Long departmentId,
            Pageable pageable
    );

    @Query("SELECT new vn.eledevo.vksbe.dto.response.account.AccountFilterCaseResponse(" +
            "acc.id, acc.username, pro.fullName, pro.avatar, rol.name, pro.gender, " +
            "CASE WHEN acc_c.cases.id = :caseId THEN acc_c.isInCharge ELSE false END) " +
            "FROM Accounts acc " +
            "LEFT JOIN acc.accountCases acc_c ON acc_c.cases.id = :caseId " +
            "LEFT JOIN acc.profile pro " +
            "JOIN acc.roles rol " +
            "WHERE (:textSearch IS NULL OR LOWER(acc.username) LIKE LOWER(CONCAT('%', :textSearch, '%')) " +
            "   OR LOWER(pro.fullName) LIKE LOWER(CONCAT('%', :textSearch, '%'))) " +
            "AND (:departmentId IS NULL OR acc.departments.id = :departmentId) " +
            "OR acc.departments.code = 'PB_LANH_DAO' " +
            "ORDER BY acc_c.isInCharge DESC ")
    Page<AccountFilterCaseResponse> getUserInChargeList(
            String textSearch,
            Long caseId,
            Long departmentId,
            Pageable pageable
    );
}

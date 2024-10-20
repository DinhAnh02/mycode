package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.eledevo.vksbe.entity.AccountCase;
import vn.eledevo.vksbe.entity.CasePerson;

import java.util.List;
import java.util.Optional;

public interface AccountCaseRepository extends BaseRepository<AccountCase, Long> {
    @Query("SELECT COUNT(ac) "
            + "FROM AccountCase ac "
            + "WHERE ac.cases.id = :caseId "
            + "AND ac.isProsecutor = true")
    int countAccountCaseByIsProsecutorTrue(Long caseId);

    @Query("SELECT COUNT(ac) > 0 "
            + "FROM AccountCase ac "
            + "WHERE ac.accounts.id = :accountId "
            + "AND ac.cases.id = :caseId "
            + "AND ac.hasAccess = true")
    boolean existsAccountCaseByAccountIdAndHasAccessTrue(Long accountId, Long caseId);

    Optional<AccountCase> findFirstAccountCaseByAccounts_IdAndCases_Id(Long accountsId, Long casesId);

    @Query("SELECT COUNT(ac) FROM AccountCase ac WHERE ac.cases.id = :caseId AND ac.isInCharge = true")
    int countAccountCaseByIsInChargeTrue(Long caseId);

    @Query("SELECT acc FROM AccountCase acc WHERE acc.accounts.id = :accountId AND acc.cases.id = :caseId AND acc.hasAccess = true ")
    Optional<AccountCase> getAccountAccessTrue(Long accountId, Long caseId);
    Optional<AccountCase> findAccountCaseByAccounts_IdAndCases_Id(Long accountsId, Long casesId);

    @Query("SELECT ac FROM AccountCase ac WHERE ac.accounts.id IN :listIdsCreate AND ac.cases.id = :caseId")
    List<AccountCase> findAccountCasesByAccountIdsAndCaseId(@Param("listIdsCreate") List<Long> listIdsCreate, @Param("caseId") Long caseId);

    @Query("SELECT COUNT(ac) > 0 FROM AccountCase ac JOIN ac.accounts a WHERE ac.hasAccess = true AND a.isCreateCase = true AND a.id = :userId")
    boolean existsByHasAccessAndIsCreateCaseForUser(@Param("userId") Long userId);

    @Query("select a from AccountCase a where a.accounts.id=:accountId and a.cases.id=:caseId")
    Optional<CasePerson> findByAccountIdAndCaseIdNative(Long accountId, Long caseId);

}

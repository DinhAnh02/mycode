package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.Query;
import vn.eledevo.vksbe.entity.AccountCase;

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

    Optional<AccountCase> findAccountCaseByAccounts_IdAndCases_Id(Long accountsId, Long casesId);

    @Query("SELECT COUNT(ac) FROM AccountCase ac WHERE ac.cases.id = :caseId AND ac.isInCharge = true")
    int countAccountCaseByIsInChargeTrue(Long caseId);

    @Query("SELECT acc FROM AccountCase acc WHERE acc.accounts.id = :accountId AND acc.cases.id = :caseId AND acc.hasAccess = true ")
    Optional<AccountCase> getListAccountCase(Long accountId, Long caseId);
}

package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.eledevo.vksbe.entity.AccountCase;

import java.util.List;

public interface AccountCaseRepository extends BaseRepository<AccountCase, Long> {
    @Query(value = "Select ac.caseId from account_case ac where ac.accounts.id = :accountId", nativeQuery = true)
    List<Long> findCaseIdByAccountId(@Param("accountId") Long accountId);
}

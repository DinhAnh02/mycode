package vn.eledevo.vksbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.eledevo.vksbe.entity.AccountCase;

public interface AccountCaseRepository extends BaseRepository<AccountCase, Long> {
    @Query(value = "Select ac.caseId from account_case ac where ac.accounts.id = :accountId", nativeQuery = true)
    List<Long> findCaseIdByAccountId(@Param("accountId") Long accountId);
}

package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.eledevo.vksbe.entity.AccountCase;

import java.util.Optional;

public interface AccountCaseRepository extends BaseRepository<AccountCase, Long> {
    Optional<AccountCase> findAccountCaseByAccounts_IdAndCases_Id(Long accountsId, Long casesId);
}

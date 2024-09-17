package vn.eledevo.vksbe.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import vn.eledevo.vksbe.entity.Accounts;

public interface AccountRepository extends BaseRepository<Accounts, Long>, JpaSpecificationExecutor<Accounts> {
    @Query("SELECT a from Accounts a where a.username =: username and a.status = 'ACTIVE'")
    Optional<Accounts> findByUsernameAndActive(String username);

    Boolean existsByUsername(String username);
}

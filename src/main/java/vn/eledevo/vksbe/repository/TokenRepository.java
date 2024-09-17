package vn.eledevo.vksbe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import vn.eledevo.vksbe.entity.AuthTokens;

public interface TokenRepository extends BaseRepository<AuthTokens, Long> {
    @Query(
            value =
                    """
			select t from AuthTokens t inner join Accounts u\s
			on t.accounts.id = u.id\s
			where u.id = :id and (t.isExpireTime = false ) \s
			""")
    List<AuthTokens> findAllValidTokenByUser(Long id);

    Optional<AuthTokens> findByToken(String token);

    @Query(
            value =
                    """
			select t from AuthTokens t inner join Accounts u\s
			on t.accounts.id = u.id\s
			where u.id = :id \s
			""")
    List<AuthTokens> findAllTokenByUser(Long id);

    void deleteByAccounts_Id(Long accountId);
}

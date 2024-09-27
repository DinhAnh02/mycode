package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.Query;

import vn.eledevo.vksbe.entity.Profiles;

public interface ProfileRepository extends BaseRepository<Profiles, Long> {
    @Query("SELECT p from Profiles p where p.accounts.id =:accountId")
    Profiles findByAccountId(Long accountId);
}

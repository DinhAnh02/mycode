package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.Query;

import vn.eledevo.vksbe.entity.Profiles;

public interface ProfileRepository extends BaseRepository<Profiles, Long> {
    Profiles findByAccounts_Id(Long accountId);
}

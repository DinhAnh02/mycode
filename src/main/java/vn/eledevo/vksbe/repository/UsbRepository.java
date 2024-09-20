package vn.eledevo.vksbe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import vn.eledevo.vksbe.entity.Usbs;

public interface UsbRepository extends BaseRepository<Usbs, Long> {
    Optional<Usbs> findByAccounts_Id(Long accountId);
}

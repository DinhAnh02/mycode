package vn.eledevo.vksbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import vn.eledevo.vksbe.entity.Computers;

public interface ComputerRepository extends BaseRepository<Computers, Long> {
    @Query(value = "Select c from computers c where c.accounts.id = :accountId", nativeQuery = true)
    List<Computers> findByAccountId(Long accountId);
}

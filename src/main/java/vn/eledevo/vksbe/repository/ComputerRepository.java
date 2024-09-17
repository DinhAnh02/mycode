package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.Query;
import vn.eledevo.vksbe.entity.Computers;

import java.util.List;

public interface ComputerRepository extends BaseRepository<Computers, Long> {
    @Query(value = "Select c from computers c where c.accounts.id = :accountId", nativeQuery = true)
    List<Computers> findByAccountId(Long accountId);
}

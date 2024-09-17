package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.Query;
import vn.eledevo.vksbe.entity.Usbs;

import java.util.List;

public interface UsbRepository extends BaseRepository<Usbs, Long> {
    @Query("SELECT u FROM Usbs u WHERE u.accounts.id = :accountId")
    List<Usbs> findByAccountId (Long accountId);
}

package vn.eledevo.vksbe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.eledevo.vksbe.dto.request.ComputerRequest;
import vn.eledevo.vksbe.dto.response.ListComputerResponse;
import vn.eledevo.vksbe.entity.Computers;

public interface ComputerRepository extends BaseRepository<Computers, Long> {
    @Query(value = "Select c from computers c where c.accounts.id = :accountId", nativeQuery = true)
    List<Computers> findByAccountId(Long accountId);

    List<Computers> findByAccounts_Id(Long accountId);

    @Query(
            value = "SELECT c.name, c.status, c.note, p.full_name " + "FROM computers c "
                    + "JOIN accounts a ON c.account_id = a.id "
                    + "JOIN profiles p ON a.id = p.account_id "
                    + "WHERE c.name = :#{#computerRequest.usbCode} "
                    + "AND p.full_name = :#{#computerRequest.fullName} "
                    + "AND c.status IS NOT NULL "
                    + "AND c.status <> '' "
                    + "AND c.status = :#{#computerRequest.status}",
            nativeQuery = true)
    List<ListComputerResponse> getComputerList(ComputerRequest computerRequest);

    @Query("SELECT c FROM Computers c "
            + "WHERE ((COALESCE(:#{#textSearch}, NULL) IS NULL) "
            + "OR LOWER(c.name) LIKE %:#{#textSearch}% "
            + "OR LOWER(c.code) LIKE %:#{#textSearch}% "
            + "AND c.accounts IS NULL)")
    Page<Computers> getByTextSearchAndAccountsIsNull(@Param("textSearch") String textSearch, Pageable pageable);

    boolean existsByCode(String code);

    boolean existsByName(String name);
}

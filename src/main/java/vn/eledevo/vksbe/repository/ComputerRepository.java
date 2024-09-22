package vn.eledevo.vksbe.repository;

import java.util.List;
import java.util.Set;

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
            value = "SELECT c.name, c.status, c.note, p.full_name " +
                    "FROM computers c " +
                    "JOIN accounts a ON c.account_id = a.id " +
                    "JOIN profiles p ON a.id = p.account_id " +
                    "WHERE (:#{#computerRequest.usbCode} IS NULL OR c.name LIKE %:#{#computerRequest.computerCode}%) " +
                    "AND (:#{#computerRequest.fullName} IS NULL OR p.full_name LIKE %:#{#computerRequest.fullName}%) " +
                    "AND (:#{#computerRequest.status} IS NULL OR c.status = :#{#computerRequest.status})",
            nativeQuery = true)
    Page<ListComputerResponse> getComputerList(ComputerRequest computerRequest, Pageable pageable);

    @Query("SELECT c FROM Computers c "
            + "WHERE (((COALESCE(:textSearch, NULL) IS NULL ) "
            + "OR LOWER(c.name) LIKE %:textSearch% "
            + "OR LOWER(c.code) LIKE %:textSearch% ) "
            + "AND c.accounts IS NULL )")
    List<Computers> getByTextSearchAndAccountsIsNull(@Param("textSearch") String textSearch);

    boolean existsByCode(String code);

    boolean existsByName(String name);

    List<Computers> findByIdIn(Set<Long> ids);
}

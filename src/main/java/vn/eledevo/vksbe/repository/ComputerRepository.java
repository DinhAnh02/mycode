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
    List<Computers> findByAccounts_Id(Long accountId);

    @Query("SELECT c.name, c.status, c.note, p.fullName " + "FROM Computers c "
            + "JOIN Accounts a ON c.accounts.id = a.id "
            + "JOIN Profiles p ON a.id = p.accounts.id "
            + "WHERE (:#{#computerRequest.computerCode} IS NULL OR c.name LIKE %:#{#computerRequest.computerCode}%) "
            + "AND (:#{#computerRequest.fullName} IS NULL OR p.fullName LIKE %:#{#computerRequest.fullName}%) "
            + "AND (:#{#computerRequest.status} IS NULL OR c.status = :#{#computerRequest.status})")
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

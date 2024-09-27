package vn.eledevo.vksbe.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.eledevo.vksbe.dto.request.ComputerRequest;
import vn.eledevo.vksbe.dto.response.ComputerResponseFilter;
import vn.eledevo.vksbe.entity.Computers;

public interface ComputerRepository extends BaseRepository<Computers, Long> {
    List<Computers> findByAccounts_Id(Long accountId);

    @Query(
            "SELECT new vn.eledevo.vksbe.dto.response.ComputerResponseFilter(c.id, c.code, c.brand, c.type, c.name, c.status, c.note, p.fullName) "
                    + "FROM Computers c "
                    + "LEFT JOIN Accounts a ON c.accounts.id = a.id "
                    + "LEFT JOIN Profiles p ON p.accounts.id = a.id "
                    + "WHERE (:#{#computerRequest.name} IS NULL OR COALESCE(c.name, '') LIKE %:#{#computerRequest.name}%) "
                    + "AND (:#{#computerRequest.accountFullName} IS NULL OR COALESCE(p.fullName, '') LIKE %:#{#computerRequest.accountFullName}%) "
                    + "AND (:#{#computerRequest.status} IS NULL OR :#{#computerRequest.status} = '' OR COALESCE(c.status, '') = :#{#computerRequest.status})")
    Page<ComputerResponseFilter> getComputerList(ComputerRequest computerRequest, Pageable pageable);

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

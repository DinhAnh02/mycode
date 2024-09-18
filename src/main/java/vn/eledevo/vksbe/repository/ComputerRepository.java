package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.eledevo.vksbe.dto.model.computer.ComputersDto;
import vn.eledevo.vksbe.dto.request.ComputerRequest;
import vn.eledevo.vksbe.dto.response.ListComputerResponse;
import vn.eledevo.vksbe.entity.Computers;

import java.util.List;

public interface ComputerRepository extends BaseRepository<Computers, Long> {
    @Query(value = "Select c from computers c where c.accounts.id = :accountId", nativeQuery = true)
    List<Computers> findByAccountId(Long accountId);

    List<Computers> findByAccounts_Id(Long accountId);

    @Query(value = "SELECT c.name, c.status, c.note, p.full_name " +
            "FROM computers c " +
            "JOIN accounts a ON c.account_id = a.id " +
            "JOIN profiles p ON a.id = p.account_id " +
            "WHERE c.name = :#{#computerRequest.usbCode} " +
            "AND p.full_name = :#{#computerRequest.fullName} " +
            "AND c.status IS NOT NULL " +
            "AND c.status <> '' " +
            "AND c.status = :#{#computerRequest.status}",
            nativeQuery = true)
    List<ListComputerResponse> getComputerList(ComputerRequest computerRequest);

    @Query("SELECT new vn.eledevo.vksbe.dto.model.computer.ComputersDto( " +
            "c.id, c.name, c.status, c.createAt) FROM Computers c " +
            "WHERE ((COALESCE(:#{#textSearch}, NULL) IS NULL) " +
            "OR LOWER(c.name) LIKE %:#{#textSearch}% " +
            "OR LOWER(c.code) LIKE %:#{#textSearch}%) ")
    List<ComputersDto> getByTextSearch(@Param("textSearch") String textSearch);

}

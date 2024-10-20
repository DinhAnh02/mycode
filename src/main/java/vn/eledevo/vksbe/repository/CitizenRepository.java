package vn.eledevo.vksbe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse;
import vn.eledevo.vksbe.dto.response.citizen.CitizenResponse;
import vn.eledevo.vksbe.entity.Citizens;

@Repository
public interface CitizenRepository extends BaseRepository<Citizens, Long> {
    @Query(
            "SELECT new vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse(cp.citizens.id, cp.type, cp.citizens.name, cp.citizens.citizenId, cp.citizens.gender, cp.citizens.profileImage, cp.citizens.workingAddress,cp.citizens.address,cp.citizens.position) "
                    + "FROM CasePerson cp "
                    + "WHERE cp.cases.id = :caseId "
                    + "AND (cp.citizens.name LIKE %:textSearch% OR cp.citizens.citizenId LIKE %:textSearch%) "
                    + "AND cp.type IN :types ")
    Page<CitizenCaseResponse> searchAllInvestigatorByCaseId(
            @Param("caseId") Long caseId,
            @Param("textSearch") String textSearch,
            @Param("types") List<String> types,
            Pageable pageable);

    @Query("SELECT new vn.eledevo.vksbe.dto.response.citizen.CitizenResponse(c.id, c.name, c.profileImage, c.citizenId)"
            + " FROM Citizens c "
            + "WHERE ((COALESCE(:textSearch, NULL) IS NULL ) "
            + "OR LOWER(c.name) LIKE CONCAT('%', LOWER(:textSearch), '%')"
            + "OR c.citizenId LIKE %:textSearch% ) ")
    Page<CitizenResponse> getListCitizenByTextSearch(String textSearch, Pageable pageable);

    boolean existsByCitizenId(String citizenId);

    @Query("SELECT new vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse (" +
            "c.id, CASE WHEN cp.type IS NULL THEN '' ELSE cp.type END, c.name, c.citizenId,  " + // Sử dụng CASE WHEN ở đây
            "c.gender, c.profileImage, c.workingAddress, c.address, c.position) " +
            "FROM Citizens c " +
            "LEFT JOIN CasePerson cp ON c.id = cp.citizens.id AND cp.cases.id = :caseId " +
            "AND (:listType IS NULL OR cp.type IN :listType) " +
            "AND (:textSearch IS NULL OR LOWER(c.name) LIKE CONCAT('%', :textSearch, '%') OR c.citizenId LIKE CONCAT('%', :textSearch, '%')) " +
            "ORDER BY cp.type DESC")
    Page<CitizenCaseResponse> findCitizenCaseResponses(
            @Param("caseId") Long caseId,
            @Param("listType") List<String> listType,
            @Param("textSearch") String textSearch,
            Pageable pageable);
}

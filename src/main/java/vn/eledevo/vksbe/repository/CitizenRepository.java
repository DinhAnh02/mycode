package vn.eledevo.vksbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse;
import vn.eledevo.vksbe.entity.Citizens;

@Repository
public interface CitizenRepository extends BaseRepository<Citizens, Long> {
    @Query(
            "SELECT new vn.eledevo.vksbe.dto.response.citizen.CitizenCaseResponse(cp.citizens.id, cp.citizens.name, cp.citizens.citizenId, cp.type, cp.citizens.profileImage) "
                    + "FROM CasePerson cp "
                    + "WHERE cp.cases.id = :caseId "
                    + "AND (cp.citizens.name LIKE %:textSearch% OR cp.citizens.citizenId LIKE %:textSearch%) "
                    + "AND cp.type = 'INVESTIGATOR' ")
    Page<CitizenCaseResponse> searchAllInvestigatorByCaseId(
            @Param("caseId") Long caseId,
            @Param("textSearch") String textSearch,
            Pageable pageable);
}

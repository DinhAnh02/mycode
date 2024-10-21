package vn.eledevo.vksbe.repository;


import org.springframework.data.jpa.repository.Query;
import vn.eledevo.vksbe.entity.CaseFlow;

import java.util.Optional;


public interface CaseFlowRepository extends BaseRepository<CaseFlow, Long> {
    Optional<CaseFlow> findFirstByCases_Id (Long id);
    @Query("SELECT cf " +
            "FROM CaseFlow cf " +
            "left JOIN Cases c ON cf.cases.id = c.id " +
            "left JOIN AccountCase ac ON ac.cases.id = c.id " +
            "where ac.hasAccess = true " +
            "and c.departments.id = :departmentId " +
            "and cf.id = :idCaseFlow  " +
            "and ac.accounts.id = :idAccount"
    )
    Optional<CaseFlow> existsCaseFlow(Long idCaseFlow,Long idAccount,Long departmentId);
    @Query("SELECT cf " +
            "FROM CaseFlow cf " +
            "WHERE cf.id = :caseFlowId and cf.cases.id = :id ")
    Optional<CaseFlow> existsCaseFlowAndCase(Long id, Long caseFlowId);

    Optional<CaseFlow> findFistByCases_IdAndId (Long caseId, Long id);
}

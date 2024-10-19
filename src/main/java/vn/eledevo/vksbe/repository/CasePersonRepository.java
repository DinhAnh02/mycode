package vn.eledevo.vksbe.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.eledevo.vksbe.entity.CasePerson;

import java.util.List;

public interface CasePersonRepository  extends BaseRepository<CasePerson, Long>{
    @Query("SELECT cp FROM CasePerson cp WHERE cp.type = :type AND cp.cases.id = :id AND cp.citizens.id IN :isCheckTrue")
    List<CasePerson> findExistingCasePersons(String type, Long id, List<Long> isCheckTrue);

    @Modifying
    @Transactional
    @Query("DELETE FROM CasePerson cp WHERE cp.cases.id = :id AND cp.citizens.id IN :isCheckFalse  AND cp.type = :type")
    void deleteCitizenInCase(@Param("id") Long id, @Param("isCheckFalse") List<Long> isCheckFalse, @Param("type")  String type);
}

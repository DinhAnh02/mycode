package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.eledevo.vksbe.entity.CaseStatus;

public interface CaseStatusRepository extends BaseRepository<CaseStatus,Long>{
    Boolean existsByName(String name);
}

package vn.eledevo.vksbe.repository;

import vn.eledevo.vksbe.entity.Cases;

public interface CaseRepository extends BaseRepository<Cases, Long> {
    boolean existsByName(String name);
    boolean existsByCode(String code);
}

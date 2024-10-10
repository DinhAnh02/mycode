package vn.eledevo.vksbe.repository;

import vn.eledevo.vksbe.entity.Organizations;

public interface OrganizationRepository extends BaseRepository<Organizations, Long> {
    Boolean existsByCode(String Code);

    Boolean existsByName(String Name);
}

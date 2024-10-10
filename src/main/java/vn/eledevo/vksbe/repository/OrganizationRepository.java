package vn.eledevo.vksbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import vn.eledevo.vksbe.dto.request.OrganizationSearch;
import vn.eledevo.vksbe.dto.response.organization.OrganizationResponse;
import vn.eledevo.vksbe.entity.Organizations;

public interface OrganizationRepository extends BaseRepository<Organizations, Long> {
    @Query("SELECT new vn.eledevo.vksbe.dto.response.organization.OrganizationResponse(o.id,o.name,o.code,o.address,o.isDefault,o.updatedAt) FROM Organizations o "
            + "WHERE (:#{#organizationsSearch.name} IS NULL OR COALESCE(o.name, ' ') LIKE %:#{#organizationsSearch.name}%) "
            + "AND (:#{#organizationsSearch.code} IS NULL OR COALESCE(o.code, ' ') LIKE %:#{#organizationsSearch.code}%) "
            + "AND (:#{#organizationsSearch.address} IS NULL OR COALESCE(o.address, ' ') LIKE %:#{#organizationsSearch.address}%) "
            + "AND (:#{#organizationsSearch.fromDate} IS NULL OR o.createdAt >= :#{#organizationsSearch.fromDate}) "
            + "AND (:#{#organizationsSearch.toDate} IS NULL OR o.createdAt <= :#{#organizationsSearch.toDate})")
    Page<OrganizationResponse> getOrganizationList(OrganizationSearch organizationsSearch, Pageable pageable);

    Boolean existsByCode(String Code);

    Boolean existsByName(String Name);
}

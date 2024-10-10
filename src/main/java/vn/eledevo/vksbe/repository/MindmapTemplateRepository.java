package vn.eledevo.vksbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import vn.eledevo.vksbe.dto.response.MindmapTemplateResponse;
import vn.eledevo.vksbe.entity.MindmapTemplate;


public interface MindmapTemplateRepository extends BaseRepository<MindmapTemplate, Long> {
    boolean existsByNameAndDepartments_Id (String name, Long id);
    @Query("select new vn.eledevo.vksbe.dto.response.MindmapTemplateResponse(m.id, m.name, m.url)  from MindmapTemplate m"
            + " where m.departments.id =:departmentId "
            + "And ((COALESCE(:textSearch, NULL) IS NULL OR m.name LIKE %:textSearch% )) ")
    Page<MindmapTemplateResponse> getListMindmapTemplate(Long departmentId, String textSearch, Pageable pageable);
}

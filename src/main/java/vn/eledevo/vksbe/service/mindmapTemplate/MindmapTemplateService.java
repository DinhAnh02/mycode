package vn.eledevo.vksbe.service.mindmapTemplate;

import vn.eledevo.vksbe.dto.request.MindMapTemplateRequest;
import vn.eledevo.vksbe.dto.response.MindmapTemplateResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.exception.ApiException;

public interface MindmapTemplateService {
    ResponseFilter<MindmapTemplateResponse> getListMindMapTemplate(Long departmentId, Integer page, Integer pageSize, String textSearch) throws ApiException;
    MindmapTemplateResponse createMindMapTemplate(MindMapTemplateRequest request) throws ApiException;
}

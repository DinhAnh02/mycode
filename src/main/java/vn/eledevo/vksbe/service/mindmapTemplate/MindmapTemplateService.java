package vn.eledevo.vksbe.service.mindmapTemplate;

import vn.eledevo.vksbe.dto.request.mindmapTemplate.MindMapTemplateRequest;
import vn.eledevo.vksbe.dto.request.mindmapTemplate.MindmapTemplateUpdateRequest;
import vn.eledevo.vksbe.dto.response.MindmapTemplateResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.exception.ApiException;

import java.util.HashMap;

public interface MindmapTemplateService {
    ResponseFilter<MindmapTemplateResponse> getListMindMapTemplate(Long departmentId, Integer page, Integer pageSize, String textSearch) throws ApiException;

    MindmapTemplateResponse createMindMapTemplate(MindMapTemplateRequest request) throws ApiException;

    MindmapTemplateResponse deleteMindMapTemplate(Long id) throws Exception;

    MindmapTemplateResponse detailMindMap(Long id) throws ApiException;

    HashMap<String, String> updateMindMapTemplate(Long id, MindmapTemplateUpdateRequest mindmapTemplateUpdateRequest) throws Exception;
}

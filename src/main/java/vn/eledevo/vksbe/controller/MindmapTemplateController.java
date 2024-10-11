package vn.eledevo.vksbe.controller;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.eledevo.vksbe.dto.request.MindMapTemplateRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.MindmapTemplateResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.mindmapTemplate.MindmapTemplateService;

@RestController
@RequestMapping("/api/v1/private/mindmapTemplate")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Quản lý sơ đồ mẫu")
public class MindmapTemplateController {

    MindmapTemplateService mindmapTemplateService;

    @GetMapping("/{departmentId}/department")
    @Operation(summary = "Lấy danh sách sơ đồ mẫu theo phòng ban")
    public ApiResponse<ResponseFilter<MindmapTemplateResponse>> getList(
            @PathVariable Long departmentId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer pageSize,
            @RequestParam(required = false) String textSearch
    ) throws ApiException {
        return ApiResponse.ok(mindmapTemplateService.getListMindMapTemplate(departmentId, page, pageSize, textSearch));
    }

    @PostMapping("/create")
    public ApiResponse <MindmapTemplateResponse> create(
            @RequestBody MindMapTemplateRequest mindMapTemplateRequest
    ) throws ApiException{
        return ApiResponse.ok(mindmapTemplateService.createMindMapTemplate(mindMapTemplateRequest));
    }

    @DeleteMapping("/{id}/delete")
    public ApiResponse<MindmapTemplateResponse> delete(
            @PathVariable Long id
    ) throws Exception {
        return ApiResponse.ok(mindmapTemplateService.deleteMindMapTemplate(id));
    }

    @GetMapping("/{id}/detail")
    @Operation(summary = "xem chi tiết sơ đồ mẫu")
    public ApiResponse<MindmapTemplateResponse> getDetail(
            @PathVariable Long id
    ) throws ApiException {
        return ApiResponse.ok(mindmapTemplateService.detailMindMap(id));
    }
}

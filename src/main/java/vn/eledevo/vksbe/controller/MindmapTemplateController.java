package vn.eledevo.vksbe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
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

}

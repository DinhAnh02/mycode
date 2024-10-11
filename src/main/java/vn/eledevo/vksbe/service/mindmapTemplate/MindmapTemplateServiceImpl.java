package vn.eledevo.vksbe.service.mindmapTemplate;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ErrorCodes.DepartmentErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.MindmapTemplateErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.constant.Role;
import vn.eledevo.vksbe.dto.request.MindMapTemplateRequest;
import vn.eledevo.vksbe.dto.response.MindmapTemplateResponse;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.entity.Departments;
import vn.eledevo.vksbe.entity.MindmapTemplate;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.DepartmentRepository;
import vn.eledevo.vksbe.repository.MindmapTemplateRepository;
import vn.eledevo.vksbe.utils.SecurityUtils;
import vn.eledevo.vksbe.utils.minio.MinioService;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MindmapTemplateServiceImpl implements MindmapTemplateService {

    MindmapTemplateRepository mindmapTemplateRepository;

    DepartmentRepository departmentRepository;
    MinioService minioService;

    public ResponseFilter<MindmapTemplateResponse> getListMindMapTemplate(Long departmentId, Integer page, Integer pageSize, String textSearch) throws ApiException {
        Accounts accounts = SecurityUtils.getUser();
        Optional<Departments> departments = departmentRepository.findById(departmentId);

        if (departments.isEmpty()) {
            throw new ApiException(DepartmentErrorCode.DEPARTMENT_NOT_FOUND);
        }
        if (!accounts.getRoles().getCode().equals(Role.VIEN_TRUONG.name()) && !(accounts.getRoles().getCode().equals(Role.VIEN_PHO.name()))) {
            if (!accounts.getDepartments().getId().equals(departmentId)) {
                throw new ApiException(MindmapTemplateErrorCode.MINDMAP_TEMPLATE_NO_PERMISSION_TO_ACCESS);
            }
        }
        if (page < 1) {
            throw new ApiException(SystemErrorCode.BAD_REQUEST_SERVER);
        }
        if (pageSize < 1) {
            throw new ApiException(SystemErrorCode.BAD_REQUEST_SERVER);
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("updatedAt").descending());
        Page<MindmapTemplate> mindmapTemplate = mindmapTemplateRepository.getListMindmapTemplate(departmentId, textSearch, pageable);
        Page<MindmapTemplateResponse> mindmapTemplateResponses = mindmapTemplate.map(mindmap ->
                MindmapTemplateResponse.builder()
                        .id(mindmap.getId())
                        .name(mindmap.getName())
                        .url(mindmap.getUrl())
                        .build()
        );
        return new ResponseFilter<>(
                mindmapTemplateResponses.getContent(),
                (int) mindmapTemplateResponses.getTotalElements(),
                mindmapTemplateResponses.getSize(),
                mindmapTemplateResponses.getNumber(),
                mindmapTemplateResponses.getTotalPages()
        );

    }

    @Override
    public MindmapTemplateResponse createMindMapTemplate(MindMapTemplateRequest request) throws ApiException {
        Accounts accounts = SecurityUtils.getUser();
        Optional<Departments> departments = departmentRepository.findById(request.getDepartmentId());
        if (departments.isEmpty()){
            throw new ApiException(DepartmentErrorCode.DEPARTMENT_NOT_FOUND);
        }
        if(!request.getDepartmentName().equals(departments.get().getName())) {
            throw new ApiException(SystemErrorCode.ORGANIZATION_STRUCTURE);
        }
        if (!accounts.getRoles().getCode().equals(Role.VIEN_TRUONG.name())
                && !(accounts.getRoles().getCode().equals(Role.VIEN_PHO.name()))
                && !accounts.getDepartments().getId().equals(request.getDepartmentId())) {
                throw new ApiException(MindmapTemplateErrorCode.MINDMAP_TEMPLATE_NOT_ENOUGH_PERMISSION);
        }
        if(mindmapTemplateRepository.existsByNameAndDepartments_Id(request.getName(), departments.get().getId())){
            throw new ApiException(MindmapTemplateErrorCode. MINDMAP_TEMPLATE_NAME_ALREADY_EXISTS);
        }
        MindmapTemplate mindmapTemplate = MindmapTemplate.builder()
                .name(request.getName())
                .departments(departments.get())
                .build();

        mindmapTemplate = mindmapTemplateRepository.save(mindmapTemplate);
        return MindmapTemplateResponse.builder()
                .id(mindmapTemplate.getId())
                .name(mindmapTemplate.getName())
                .build();

    }

    @Override
    public MindmapTemplateResponse deleteMindMapTemplate(Long id) throws Exception {
        Accounts accounts = SecurityUtils.getUser();
        Optional<MindmapTemplate> mindmapTemplate = mindmapTemplateRepository.findById(id);
        if(mindmapTemplate.isEmpty()){
            throw new ApiException(MindmapTemplateErrorCode.MINDMAP_TEMPLATE_NOT_FOUND);
        }
        if (!accounts.getRoles().getCode().equals(Role.VIEN_TRUONG.name()) && !(accounts.getRoles().getCode().equals(Role.VIEN_PHO.name()))) {
            if (!accounts.getDepartments().getId().equals(mindmapTemplate.get().getDepartments().getId())) {
                throw new ApiException(MindmapTemplateErrorCode.MINDMAP_TEMPLATE_NO_PERMISSION_TO_ACCESS);
            }
        }
        if (mindmapTemplate.get().getUrl() != null){
            minioService.deleteFile(mindmapTemplate.get().getUrl());
        }
        mindmapTemplateRepository.deleteById(id);
        return null;
    }

}

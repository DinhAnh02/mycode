package vn.eledevo.vksbe.service.citizen;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.eledevo.vksbe.constant.ErrorCodes.CitizenErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.constant.ResponseMessage;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.citizen.CitizenResponse;
import vn.eledevo.vksbe.dto.response.citizen.CitizenUpdateRequest;
import vn.eledevo.vksbe.entity.Citizens;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.CitizenRepository;
import vn.eledevo.vksbe.utils.minio.MinioProperties;
import vn.eledevo.vksbe.utils.minio.MinioService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static vn.eledevo.vksbe.constant.FileConst.AVATAR_ALLOWED_EXTENSIONS;
import static vn.eledevo.vksbe.utils.FileUtils.isPathAllowedExtension;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CitizenServiceImpl implements CitizenService {
    CitizenRepository citizenRepository;
    MinioService minioService;
    MinioProperties minioProperties;

    @Value("${app.host}")
    @NonFinal
    private String appHost;

    @Override
    public ResponseFilter<CitizenResponse> getListCitizen(String textSearch, Integer page, Integer pageSize) throws ApiException {
        if (page < 1 || pageSize < 1) {
            throw new ApiException(SystemErrorCode.BAD_REQUEST_SERVER);
        }
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<CitizenResponse> citizenResponseList = citizenRepository.getListCitizenByTextSearch(textSearch, pageable);
        return new ResponseFilter<>(
                citizenResponseList.getContent(),
                (int) citizenResponseList.getTotalElements(),
                citizenResponseList.getSize(),
                citizenResponseList.getNumber(),
                citizenResponseList.getTotalPages()
        );
    }

    @Override
    public HashMap<String, String> updateCitizen(Long citizenId, CitizenUpdateRequest citizenUpdateRequest) throws Exception {
        Citizens existingCitizens = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new ApiException(CitizenErrorCode.CITIZEN_NOT_FOUND));
        if (Objects.nonNull(citizenUpdateRequest.getProfileImage())) {
            Map<String, String> error = new HashMap<>();
            validateUrlImg(citizenUpdateRequest.getProfileImage(), error);
            if (!error.isEmpty()) {
                SystemErrorCode errorCode = SystemErrorCode.VALIDATE_FORM;
                errorCode.setResult(Optional.of(error));
                throw new ApiException(errorCode);
            }
            if (Objects.nonNull(existingCitizens.getProfileImage())
                    && !Objects.equals(existingCitizens.getProfileImage(), "")) {
                minioService.deleteFile(existingCitizens.getProfileImage());
            }
        }
        existingCitizens.setName(citizenUpdateRequest.getFullName());
        existingCitizens.setGender(citizenUpdateRequest.getGender());
        existingCitizens.setAddress(citizenUpdateRequest.getAddress());
        existingCitizens.setProfileImage(citizenUpdateRequest.getProfileImage());
        existingCitizens.setWorkingAddress(citizenUpdateRequest.getWorkingAddress());
        existingCitizens.setPosition(citizenUpdateRequest.getJob());
        citizenRepository.save(existingCitizens);
        return new HashMap<>();
    }

    public void validateUrlImg(String avatarUrl, Map<String, String> errors) {
        if (StringUtils.isBlank(avatarUrl)) {
            return;
        }
        String keyError = "url";

        try {
            URI uri = new URI(avatarUrl);

            String scheme = uri.getScheme();
            if (!"http".equals(scheme) && !"https".equals(scheme)) {
                errors.put(keyError, ResponseMessage.PROFILE_IMG_URL_INVALID);
                return;
            }

            String host = uri.getHost();
            if (host == null || !appHost.contains(host)) {
                errors.put(keyError, ResponseMessage.PROFILE_IMG_URL_INVALID);
                return;
            }

            String path = uri.getPath();
            if (path == null || !path.contains("/" + minioProperties.getBucketName() + "/")) {
                errors.put(keyError, ResponseMessage.PROFILE_IMG_URL_INVALID);
                return;
            }

            if (!isPathAllowedExtension(path, AVATAR_ALLOWED_EXTENSIONS)) {
                errors.put(keyError, ResponseMessage.PROFILE_IMG_URL_INVALID);
            }

        } catch (URISyntaxException e) {
            errors.put("url", ResponseMessage.PROFILE_IMG_URL_INVALID);
        }
    }


}

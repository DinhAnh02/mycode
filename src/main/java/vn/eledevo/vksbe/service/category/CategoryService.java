package vn.eledevo.vksbe.service.category;

import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.InformationResponse;

public interface CategoryService {
    ApiResponse<InformationResponse> getAllInformation();
}

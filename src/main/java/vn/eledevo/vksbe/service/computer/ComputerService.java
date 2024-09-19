package vn.eledevo.vksbe.service.computer;

import vn.eledevo.vksbe.dto.model.computer.ComputersModel;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.PageResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.exception.ApiException;

public interface ComputerService {
    ApiResponse updateComputer(Long computerId, ComputersModel computerRequest) throws ApiException;

    PageResponse<ComputerResponse> getDisconnectedComputers(Integer currentPage, Integer limit, String textSearch);
}

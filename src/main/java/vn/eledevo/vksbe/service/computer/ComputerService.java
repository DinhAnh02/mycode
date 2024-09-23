package vn.eledevo.vksbe.service.computer;

import vn.eledevo.vksbe.dto.model.computer.ComputersModel;
import vn.eledevo.vksbe.dto.request.ComputerRequest;
import vn.eledevo.vksbe.dto.request.computer.ComputerRequestForCreate;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.exception.ApiException;

public interface ComputerService {

    ApiResponse getComputerList(ComputerRequest computerRequest, Long currentPage, Long limit) throws ApiException;

    ApiResponse updateComputer(Long computerId, ComputersModel computerRequest) throws ApiException;

    Result getDisconnectedComputers(String textSearch);

    ApiResponse<?> createComputer(ComputerRequestForCreate request) throws ApiException;
}

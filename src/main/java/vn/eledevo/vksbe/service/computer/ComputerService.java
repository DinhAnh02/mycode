package vn.eledevo.vksbe.service.computer;


import vn.eledevo.vksbe.dto.model.computer.ComputersModel;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.exception.ApiException;

public interface ComputerService {
    ApiResponse updateComputer(Long computerId,ComputersModel computerRequest) throws ApiException;
}

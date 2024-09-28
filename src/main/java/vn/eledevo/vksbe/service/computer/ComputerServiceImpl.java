package vn.eledevo.vksbe.service.computer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.eledevo.vksbe.constant.ResponseMessage;
import vn.eledevo.vksbe.dto.model.computer.ComputersModel;
import vn.eledevo.vksbe.dto.request.ComputerRequest;
import vn.eledevo.vksbe.dto.request.computer.ComputerRequestForCreate;
import vn.eledevo.vksbe.dto.response.ComputerResponseFilter;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.entity.Computers;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.mapper.ComputerMapper;
import vn.eledevo.vksbe.repository.ComputerRepository;

import java.util.List;
import java.util.Objects;

import static vn.eledevo.vksbe.constant.ErrorCode.*;
import static vn.eledevo.vksbe.constant.ResponseMessage.CREATE_NEW_DEVICE_SUCCESS;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComputerServiceImpl implements ComputerService {
    ComputerRepository computerRepository;
    ComputerMapper computerMapper;

    @Override
    public Result<ComputerResponseFilter> getComputerList(ComputerRequest computerRequest, Integer currentPage, Integer limit) {
        Pageable pageable = PageRequest.of(currentPage - 1, limit);
        Page<ComputerResponseFilter> page = computerRepository.getComputerList(computerRequest, pageable);
        return new Result<>(page.getContent(), (int) page.getTotalElements());
    }

    @Override
    public String updateComputer(Long computerId, ComputersModel computerRequest) throws ApiException {
        Computers exitstingComputer =
                computerRepository.findById(computerId).orElseThrow(() -> new ApiException(DEVICE_NOT_EXIST));
        if (computerRepository.existsByName(computerRequest.getName())) {
            throw new ApiException(DEVICE_NOT_EXIST);
        }

        exitstingComputer.setName(computerRequest.getName());
        exitstingComputer.setBrand(computerRequest.getBrand());
        exitstingComputer.setType(computerRequest.getType());
        exitstingComputer.setNote(computerRequest.getNote());
        computerRepository.save(exitstingComputer);
        return ResponseMessage.UPDATE_COMPUTER_INFOR_SUCCESS;
    }

    @Override
    public Result<ComputerResponse> getDisconnectedComputers(String textSearch) {
        String keyword =
                StringUtils.isBlank(textSearch) ? null : textSearch.trim().toLowerCase();
        List<Computers> computersList = computerRepository.getByTextSearchAndAccountsIsNull(keyword);
        return new Result<>(computerMapper.toListResponse(computersList), computersList.size());
    }

    @Override
    @Transactional
    public String createComputer(ComputerRequestForCreate request) throws ApiException {
        Boolean computerExist = computerRepository.existsByCode(request.getCode());
        if (Objects.equals(computerExist, Boolean.TRUE)) {
            throw new ApiException(COMPUTER_HAS_EXISTED);
        }
        if (computerRepository.existsByName(request.getName())) {
            throw new ApiException(NAME_COMPUTER_HAS_EXISTED);
        }
        Computers computersCreate = computerRepository.save(computerMapper.toResource(request));
        computerMapper.toResponse(computersCreate);
        return CREATE_NEW_DEVICE_SUCCESS;
    }
}

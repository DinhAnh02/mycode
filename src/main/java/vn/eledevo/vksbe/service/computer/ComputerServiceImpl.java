package vn.eledevo.vksbe.service.computer;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ErrorCodes.ComputerErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.constant.ResponseMessage;
import vn.eledevo.vksbe.dto.model.computer.ComputersModel;
import vn.eledevo.vksbe.dto.request.ComputerRequest;
import vn.eledevo.vksbe.dto.request.computer.ComputerRequestForCreate;
import vn.eledevo.vksbe.dto.request.computer.ComputerToCheckExist;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.ResultList;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponseFilter;
import vn.eledevo.vksbe.entity.Computers;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.mapper.ComputerMapper;
import vn.eledevo.vksbe.repository.ComputerRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComputerServiceImpl implements ComputerService {
    ComputerRepository computerRepository;
    ComputerMapper computerMapper;

    @Override
    public ResponseFilter<ComputerResponseFilter> getComputerList(
            ComputerRequest computerRequest, Integer currentPage, Integer limit) {
        Pageable pageable =
                PageRequest.of(currentPage - 1, limit, Sort.by("updatedAt").descending());
        Page<ComputerResponseFilter> page = computerRepository.getComputerList(computerRequest, pageable);
        return new ResponseFilter<>(
                page.getContent(),
                (int) page.getTotalElements(),
                page.getSize(),
                page.getNumber(),
                page.getTotalPages());
    }

    @Override
    public HashMap<String, String> updateComputer(Long requestId, ComputersModel computerRequest) throws ApiException, ValidationException {
        Map<String, String> errors = new HashMap<>();
        Computers computer = computerRepository
                .findById(requestId)
                .orElseThrow(() -> new ApiException(SystemErrorCode.INTERNAL_SERVER));
        Computers computerByName = computerRepository.findByName(computerRequest.getName());
        if (computerRepository.existsByName(computerRequest.getName())
                && !computerByName.getId().equals(requestId)) {
            errors.put("name", ResponseMessage.PC_NAME_ALREADY_EXISTS);
            throw new ValidationException(errors);
        }

        computer.setName(computerRequest.getName());
        computer.setBrand(computerRequest.getBrand());
        computer.setType(computerRequest.getType());
        computer.setNote(computerRequest.getNote());
        computerRepository.save(computer);
        return new HashMap<>();
    }

    @Override
    public ResultList<ComputerResponse> getDisconnectedComputers(String textSearch) {
        String keyword =
                StringUtils.isBlank(textSearch) ? null : textSearch.trim().toLowerCase();
        List<Computers> computersList = computerRepository.getByTextSearchAndAccountsIsNull(keyword);
        return new ResultList<>(computerMapper.toListResponse(computersList));
    }

    @Override
    @Transactional
    public HashMap<String, String> createComputer(ComputerRequestForCreate request) throws ApiException, ValidationException {
        Map<String, String> errors = new HashMap<>();
        Boolean computerExist = computerRepository.existsByCode(request.getCode());
        if (Objects.equals(computerExist, Boolean.TRUE)) {
            throw new ApiException(ComputerErrorCode.PC_CODE_ALREADY_EXISTS);
        }
        if (computerRepository.existsByName(request.getName())) {
            errors.put("name", ResponseMessage.PC_NAME_ALREADY_EXISTS);
            throw new ValidationException(errors);
        }

        Computers computersCreate = computerRepository.save(computerMapper.toResource(request));
        computerMapper.toResponse(computersCreate);
        return new HashMap<>();
    }

    @Override
    public HashMap<String, String> checkExistComputer(ComputerToCheckExist computer) throws ApiException {
        Optional<Computers> result = computerRepository.findComputersByCode(computer.getComputerCode());
        if (result.isPresent()) {
            throw new ApiException(ComputerErrorCode.PC_CODE_ALREADY_EXISTS);
        }
        return new HashMap<>();
    }
}

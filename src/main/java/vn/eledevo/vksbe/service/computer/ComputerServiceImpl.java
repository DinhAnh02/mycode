package vn.eledevo.vksbe.service.computer;

import static vn.eledevo.vksbe.constant.ErrorCode.*;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.model.computer.ComputersModel;
import vn.eledevo.vksbe.dto.request.ComputerRequest;
import vn.eledevo.vksbe.dto.request.computer.ComputerRequestForCreate;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.ListComputerResponse;
import vn.eledevo.vksbe.dto.response.PageResponse;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.entity.Computers;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.mapper.ComputerMapper;
import vn.eledevo.vksbe.repository.AccountRepository;
import vn.eledevo.vksbe.repository.ComputerRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComputerServiceImpl implements ComputerService {
    ComputerRepository computerRepository;
    AccountRepository accountRepository;
    ComputerMapper computerMapper;

    @Override
    public ApiResponse<List<ListComputerResponse>> getComputerList(
            ComputerRequest computerRequest, Long currentPage, Long limit) throws ApiException {
        try {
            Pageable pageable = PageRequest.of(currentPage.intValue(), limit.intValue());
            Page<ListComputerResponse> responsePage = computerRepository.getComputerList(computerRequest, pageable);
            List<ListComputerResponse> listComputerResponses =
                    responsePage.getContent().stream().toList();
            return ApiResponse.ok(listComputerResponses);
        } catch (Exception e) {
            throw new ApiException(UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public ApiResponse updateComputer(Long computerId, ComputersModel computerRequest) throws ApiException {
        try {
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
            return new ApiResponse(200, "Cập nhật thành công");
        } catch (Exception e) {
            throw new ApiException(UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public Result getDisconnectedComputers(String textSearch) {
        String keyword =
                StringUtils.isBlank(textSearch) ? null : textSearch.trim().toLowerCase();
        List<Computers> computersList = computerRepository.getByTextSearchAndAccountsIsNull(keyword);
        return new Result(computerMapper.toListResponse(computersList), computersList.size());
    }

    @Override
    @Transactional
    public ApiResponse<?> createComputer(ComputerRequestForCreate request) throws ApiException {
        Boolean computerExist = computerRepository.existsByCode(request.getCode());
        if (Objects.equals(computerExist, Boolean.TRUE)) {
            throw new ApiException(COMPUTER_HAS_EXISTED);
        }
        if (computerRepository.existsByName(request.getName())) {
            throw new ApiException(NAME_COMPUTER_HAS_EXISTED);
        }
        Computers computersCreate = computerRepository.save(computerMapper.toResource(request));
        ComputerResponse computerResponse = computerMapper.toResponse(computersCreate);
        return ApiResponse.ok(computerResponse);
    }
}

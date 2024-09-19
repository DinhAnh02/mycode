package vn.eledevo.vksbe.service.computer;

import static vn.eledevo.vksbe.constant.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.model.computer.ComputersModel;
import vn.eledevo.vksbe.dto.request.AccountProfile;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.PageResponse;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.entity.Computers;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.mapper.ComputerMapper;
import vn.eledevo.vksbe.repository.AccountRepository;
import vn.eledevo.vksbe.repository.ComputerRepository;
import vn.eledevo.vksbe.utils.SecurityUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComputerServiceImpl implements ComputerService {
    ComputerRepository computerRepository;
    AccountRepository accountRepository;
    ComputerMapper computerMapper;

    @Override
    public ApiResponse updateComputer(Long computerId, ComputersModel computerRequest) throws ApiException {
        try {
            String userName = SecurityUtils.getUserName();
            AccountProfile accountRequest = accountRepository.findByUsernameAndProfile(userName);
            Accounts exitstingAccount = accountRepository
                    .findById(accountRequest.getId())
                    .orElseThrow(() -> new ApiException(ACCOUNT_NOT_FOUND));
            Computers exitstingComputer =
                    computerRepository.findById(computerId).orElseThrow(() -> new ApiException(DEVICE_NOT_EXIST));
            if (computerRepository.existsByName(computerRequest.getName())) {
                throw new ApiException(DEVICE_NOT_EXIST);
            }

            exitstingComputer.setName(computerRequest.getName());
            exitstingComputer.setStatus(computerRequest.getStatus());
            exitstingComputer.setBrand(computerRequest.getBrand());
            exitstingComputer.setType(computerRequest.getType());
            exitstingComputer.setNote(computerRequest.getNote());
            exitstingComputer.setUpdateAt(LocalDateTime.now());
            exitstingComputer.setUpdateBy(exitstingAccount.getProfile().getFullName());
            computerRepository.save(exitstingComputer);
            return new ApiResponse(200, "Cập nhật thành công");
        } catch (Exception e) {
            throw new ApiException(UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public PageResponse<ComputerResponse> getDisconnectedComputers(
            Integer currentPage, Integer limit, String textSearch) {
        int pageNumber = Objects.isNull(currentPage) ? 1 : currentPage;
        int pageSize = Objects.isNull(limit) ? 10 : limit;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Computers> page = computerRepository.getByTextSearchAndAccountsIsNull(textSearch, pageable);
        return new PageResponse<>(page.getTotalElements(), computerMapper.toListResponse(page.getContent()));
    }
}

package vn.eledevo.vksbe.service.user;

import static vn.eledevo.vksbe.constant.ResponseMessage.USER_EXIST;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.config.DynamicSpecification;
import vn.eledevo.vksbe.constant.ErrorCode;
import vn.eledevo.vksbe.dto.request.UserRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.UserResponse;
import vn.eledevo.vksbe.entity.User;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.mapper.UserMapper;
import vn.eledevo.vksbe.repository.TokenRepository;
import vn.eledevo.vksbe.repository.UserRepository;
import vn.eledevo.vksbe.utils.SecurityUtils;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    TokenRepository tokenRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserMapper mapper;

    public UserResponse createUser(UserRequest userRequest) throws ValidationException {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ValidationException("username", USER_EXIST);
        }
        User user = mapper.toEntity(userRequest);
        user.setCreatedBy(SecurityUtils.getUserId());
        user.setUpdatedBy(SecurityUtils.getUserId());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User userResult = userRepository.save(user);
        return mapper.toResponse(userResult);
        //        UserResponse createdUser = mapper.toResponse(
        //                userRepository.findById(userResult.getCreatedBy()).orElse(null));
        //        userResponse.setCreatedUser(createdUser);

    }

    @Override
    public List<UserResponse> searchUser(Map<String, Object> filters) {
        Specification<User> spec = new DynamicSpecification<>(filters);
        return mapper.toListResponse(userRepository.findAll(spec));
    }

    @Override
    public UserResponse deleteUser(UUID idUser) throws ApiException {
        Optional<User> userInfo = userRepository.findByIdAndIsDeletedFalse(idUser);
        if (userInfo.isEmpty()) {
            throw new ApiException(ErrorCode.USER_NOT_EXIST);
        }
        userInfo.get().setIsDeleted(true);
        var validUserTokens = tokenRepository.findAllTokenByUser(idUser);
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> tokenRepository.deleteById(token.getId()));
        }
        userRepository.save(userInfo.get());
        return mapper.toResponse(userInfo.get());
    }

    @Override
    public ApiResponse removeUser(UUID idUser) throws ApiException {
        var validUserTokens = tokenRepository.findAllTokenByUser(idUser);
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> tokenRepository.deleteById(token.getId()));
        }
        userRepository.deleteById(idUser);
        return new ApiResponse<>(200, "Xoá thành công");
    }

    @Override
    public ApiResponse updateUser(UUID idUser, UserRequest userRequest) throws ApiException {
        Optional<User> userInfo = userRepository.findById(idUser);
        if (userInfo.isEmpty()) {
            throw new ApiException(ErrorCode.USER_NOT_EXIST);
        }
        userInfo.get().setId(idUser);
        userInfo.get().setFullName(userRequest.getFullName());
        userInfo.get().setPassword(userRequest.getPassword());
        userRepository.save(userInfo.get());
        return new ApiResponse<>(200, "Cập nhật thành công");
    }
}

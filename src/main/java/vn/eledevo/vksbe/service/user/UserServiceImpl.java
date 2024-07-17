package vn.eledevo.vksbe.service.user;

import static vn.eledevo.vksbe.constant.ResponseMessage.USER_EXIST;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.request.UserRequest;
import vn.eledevo.vksbe.dto.response.UserResponse;
import vn.eledevo.vksbe.entity.User;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.mapper.UserMapper;
import vn.eledevo.vksbe.repository.UserRepository;
import vn.eledevo.vksbe.utils.SecurityUtils;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

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
}

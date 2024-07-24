package vn.eledevo.vksbe.service.user;

import java.util.List;
import java.util.Map;

import vn.eledevo.vksbe.dto.request.UserRequest;
import vn.eledevo.vksbe.dto.response.UserResponse;
import vn.eledevo.vksbe.entity.User;
import vn.eledevo.vksbe.exception.ValidationException;

public interface UserService {
    UserResponse createUser(UserRequest userRequest) throws ValidationException;

    List<User> searchUser(Map<String, Object> filters);
}

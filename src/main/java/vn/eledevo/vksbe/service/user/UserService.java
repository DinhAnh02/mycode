package vn.eledevo.vksbe.service.user;

import vn.eledevo.vksbe.dto.request.UserRequest;
import vn.eledevo.vksbe.dto.response.UserResponse;
import vn.eledevo.vksbe.exception.ValidationException;

public interface UserService {
    UserResponse createUser(UserRequest userRequest) throws ValidationException;
}

package vn.eledevo.vksbe.service.user;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import vn.eledevo.vksbe.dto.request.UserRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.UserResponse;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.exception.ValidationException;

public interface UserService {
    UserResponse createUser(UserRequest userRequest) throws ValidationException;

    List<UserResponse> searchUser(Map<String, Object> filters);

    UserResponse deleteUser(UUID idUser) throws ApiException;

    ApiResponse removeUser(UUID idUser) throws ApiException;

    ApiResponse updateUser(UUID idUser, UserRequest userRequest) throws ApiException;
}

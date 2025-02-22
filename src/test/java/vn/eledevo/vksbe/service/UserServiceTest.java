// package vn.eledevo.vksbe.service;
//
// import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.when;
//
// import java.util.*;
//
// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.data.jpa.domain.Specification;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.test.context.support.WithUserDetails;
//
// import vn.eledevo.vksbe.constant.ErrorCode;
// import vn.eledevo.vksbe.constant.ResponseMessage;
// import vn.eledevo.vksbe.constant.Role;
// import vn.eledevo.vksbe.dto.request.UserRequest;
// import vn.eledevo.vksbe.dto.response.UserResponse;
// import vn.eledevo.vksbe.entity.User;
// import vn.eledevo.vksbe.exception.ApiException;
// import vn.eledevo.vksbe.exception.ValidationException;
// import vn.eledevo.vksbe.repository.UserRepository;
// import vn.eledevo.vksbe.service.user.UserService;
//
// @SpringBootTest
// @AutoConfigureMockMvc
// class UserServiceTest {
//    @Autowired
//    private UserService userService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    private UserRequest userRequest;
//    private User user;
//
//    @BeforeEach
//    void initData() {
//        userRequest = UserRequest.builder()
//                .username("long")
//                .fullName("Nguyen Hoang Long")
//                .password("123456")
//                .build();
//
//        user = User.builder()
//                .id(UUID.fromString("88100e6d-99fe-44cd-aa44-1d2aa5c04d52"))
//                .username("long")
//                .fullName("Nguyen Hoang Long")
//                .build();
//    }
//
//    @TestConfiguration
//    static class TestConfig {
//        @Bean
//        public UserDetailsService testUserDetailsService() {
//            User userMock = new User();
//            userMock.setRole(Role.ADMIN);
//            return username -> userMock;
//        }
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    // Kiểm tra Id, Username trả về khi tạo tài khoản
//    void createUser_validRequest_success() throws Exception {
//        when(userRepository.existsByUsername(anyString())).thenReturn(false);
//        when(userRepository.save(any())).thenReturn(user);
//        var response = userService.createUser(userRequest);
//        Assertions.assertThat(response.getId()).isEqualTo(UUID.fromString("88100e6d-99fe-44cd-aa44-1d2aa5c04d52"));
//        Assertions.assertThat(response.getUsername()).isEqualTo("long");
//    }
//
//    @Test
//    // Kiểm tra username có tồn tại khi tạo tài khoản
//    void createUser_userExisted_fail() {
//        when(userRepository.existsByUsername(anyString())).thenReturn(true);
//        var exception = assertThrows(ValidationException.class, () -> userService.createUser(userRequest));
//        Assertions.assertThat(exception.getErrors().get("username")).isEqualTo(ResponseMessage.USER_EXIST);
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    // Kiểm tra kết quả tìm kiếm theo fullName
//    void searchUser_withFullNameFilter_returnsMatchingUsers() {
//        Map<String, Object> filters = new HashMap<>();
//        filters.put("fullName", "John Doe");
//        User user1 = User.builder()
//                .id(UUID.randomUUID())
//                .username("john")
//                .fullName("John Doe")
//                .build();
//        List<User> matchingUsers = Collections.singletonList(user1);
//        when(userRepository.findAll(any(Specification.class))).thenReturn(matchingUsers);
//        List<UserResponse> result = userService.searchUser(filters);
//        Assertions.assertThat(result).hasSize(1);
//        Assertions.assertThat(result.get(0).getUsername()).isEqualTo("john");
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    void updateUser_WhenUserDoesNotExist_ShouldThrowApiException() {
//        UUID idUser = UUID.randomUUID();
//        when(userRepository.findById(idUser)).thenReturn(Optional.empty());
//        assertThatThrownBy(() -> userService.updateUser(idUser, userRequest))
//                .isInstanceOf(ApiException.class)
//                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_NOT_EXIST)
//                .hasMessage("Tài khoản không tồn tại hoặc đã bị xóa trước đó");
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    void updateUser_WhenUserExists_ShouldUpdateAndReturnSuccessResponse() throws ApiException {
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//        var response = userService.updateUser(user.getId(), userRequest);
//        Assertions.assertThat(response.getMessage()).isEqualTo("Cập nhật thành công");
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    void deleteUser_WhenUserExists_ShouldDeleteSuccessfully() throws Exception {
//        UUID userId = UUID.randomUUID();
//        when(userRepository.findByIdAndIsDeletedFalse(userId)).thenReturn(Optional.of(user));
//        when(userRepository.save(any(User.class))).thenReturn(user);
//        UserResponse response = userService.deleteUser(userId);
//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(user.getIsDeleted()).isTrue();
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    void deleteUser_WhenUserDoesNotExist_ShouldThrowApiException() {
//        when(userRepository.findByIdAndIsDeletedFalse(user.getId())).thenReturn(Optional.empty());
//        assertThatThrownBy(() -> userService.deleteUser(user.getId()))
//                .isInstanceOf(ApiException.class)
//                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_NOT_EXIST)
//                .hasMessage("Tài khoản không tồn tại hoặc đã bị xóa trước đó");
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    void removeUser_WhenUserExists_ShouldDeleteSuccessfully() throws Exception {
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//        var response = userService.removeUser(user.getId());
//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(response.getCode()).isEqualTo(200);
//        Assertions.assertThat(response.getMessage()).isEqualTo("Xoá thành công");
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    void removeUser_WhenUserDoesNotExist_ShouldStillReturnSuccessResponse() throws Exception {
//        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
//        var response = userService.removeUser(user.getId());
//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(response.getCode()).isEqualTo(200);
//        Assertions.assertThat(response.getMessage()).isEqualTo("Xoá thành công");
//    }
// }

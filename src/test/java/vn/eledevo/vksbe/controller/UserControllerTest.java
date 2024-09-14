//package vn.eledevo.vksbe.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import java.util.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import vn.eledevo.vksbe.constant.ResponseMessage;
//import vn.eledevo.vksbe.constant.Role;
//import vn.eledevo.vksbe.dto.request.UserRequest;
//import vn.eledevo.vksbe.dto.response.ApiResponse;
//import vn.eledevo.vksbe.dto.response.UserResponse;
//import vn.eledevo.vksbe.entity.User;
//import vn.eledevo.vksbe.service.user.UserService;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private UserService userService;
//
//    private UserRequest userRequest;
//    private UserResponse userResponse;
//
//    @BeforeEach
//    void initData() {
//        userRequest = UserRequest.builder()
//                .username("long")
//                .fullName("Nguyen Hoang Long")
//                .password("123456")
//                .build();
//
//        userResponse = UserResponse.builder()
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
//    // Kiểm tra mã code trả về khi tạo tài khoản thành công
//    void createUser_validRequest_success() throws Exception {
//        String content = objectMapper.writeValueAsString(userRequest);
//        when(userService.createUser(any())).thenReturn(userResponse);
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/private/users")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    // Kiểm tra mã code, message, result.username trả về trong trường hợp nhập thiếu ký tự tên đăng nhập
//    void createUser_usernameInvalid_fail() throws Exception {
//        userRequest.setUsername("ts");
//        String content = objectMapper.writeValueAsString(userRequest);
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/private/users")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value(422))
//                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Unprocessable Entity"))
//                .andExpect(MockMvcResultMatchers.jsonPath("result.username").value(ResponseMessage.USER_SIZE));
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    // Kiểm tra kết quả sau khi tìm kiếm
//    void searchUser_withNameFilter_success() throws Exception {
//        Map<String, Object> filters = new HashMap<>();
//        filters.put("username", "John");
//        String content = objectMapper.writeValueAsString(filters);
//        UserResponse user1 = UserResponse.builder().username("John").build();
//        UserResponse user2 = UserResponse.builder().username("Jene").build();
//        when(userService.searchUser(any())).thenReturn(List.of(user1, user2));
//
//        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/private/users/search")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200))
//                .andExpect(MockMvcResultMatchers.jsonPath("result.length()").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("result[0].username").value("John"));
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    // Kiểm tra kết quả sau khi tìm kiếm không có trong db
//    void searchUser_withNameFilter_fail() throws Exception {
//        Map<String, Object> filters = new HashMap<>();
//        filters.put("username", "John");
//        String content = objectMapper.writeValueAsString(filters);
//        when(userService.searchUser(any())).thenReturn(Collections.emptyList());
//        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/private/users/search")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200))
//                .andExpect(MockMvcResultMatchers.jsonPath("result").isArray())
//                .andExpect(MockMvcResultMatchers.jsonPath("result").isEmpty());
//    }
//
//    @Test
//    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
//    void updateUser_WhenValidRequest_ShouldReturnOkResponse() throws Exception {
//        UUID userId = UUID.randomUUID();
//        ApiResponse expectedResponse = ApiResponse.ok("Cập nhật thành công");
//        when(userService.updateUser(userId, userRequest)).thenReturn(expectedResponse);
//        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/private/user/update/{id}", userId)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(userRequest)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200))
//                .andExpect(MockMvcResultMatchers.jsonPath("message").value("OK"));
//    }
//}

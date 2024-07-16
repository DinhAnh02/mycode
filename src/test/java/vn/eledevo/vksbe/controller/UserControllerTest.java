package vn.eledevo.vksbe.controller;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.eledevo.vksbe.constant.ResponseMessage;
import vn.eledevo.vksbe.dto.request.UserRequest;
import vn.eledevo.vksbe.dto.response.UserResponse;
import vn.eledevo.vksbe.service.user.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void initData() {
        userRequest = UserRequest.builder()
                .username("long")
                .fullName("Nguyen Hoang Long")
                .password("123456")
                .build();

        userResponse = UserResponse.builder()
                .id(UUID.fromString("88100e6d-99fe-44cd-aa44-1d2aa5c04d52"))
                .username("long")
                .fullName("Nguyen Hoang Long")
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        String content = objectMapper.writeValueAsString(userRequest);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/private/users")
                        .header(
                                "Authorization",
                                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW9udiIsImlhdCI6MTcyMTA5NDc0OCwiZXhwIjoxNzIxMTgxMTQ4fQ.01KLCAvXZkvIbstLvGg06Dt-Ak2v_G3RbhtsaqIAxDM")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
    }

    @Test
    void createUser_usernameInvalid_fail() throws Exception {
        userRequest.setUsername("ts");
        String content = objectMapper.writeValueAsString(userRequest);

        //        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/private/users")
                        .header(
                                "Authorization",
                                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW9udiIsImlhdCI6MTcyMTA5NDc0OCwiZXhwIjoxNzIxMTgxMTQ4fQ.01KLCAvXZkvIbstLvGg06Dt-Ak2v_G3RbhtsaqIAxDM")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(422))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Unprocessable Entity"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.username").value(ResponseMessage.USER_SIZE));
    }
}

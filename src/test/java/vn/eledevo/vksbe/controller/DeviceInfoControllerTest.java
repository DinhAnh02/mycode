package vn.eledevo.vksbe.controller;

import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.eledevo.vksbe.constant.ErrorCode;
import vn.eledevo.vksbe.constant.Role;
import vn.eledevo.vksbe.dto.request.DeviceInfoRequest;
import vn.eledevo.vksbe.dto.response.DeviceInfoResponse;
import vn.eledevo.vksbe.entity.DeviceInfo;
import vn.eledevo.vksbe.entity.User;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.DeviceInfoRepository;
import vn.eledevo.vksbe.service.device_info.DeviceInfoService;

@SpringBootTest
@AutoConfigureMockMvc
class DeviceInfoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceInfoRepository deviceInfoRepository;

    @MockBean
    private DeviceInfoService deviceInfoService;

    private DeviceInfoRequest createDeviceInfoRequest;
    private DeviceInfoRequest createDeviceInfoDulicateIdRequest;
    private DeviceInfoResponse createDeviceInfoResponse;
    private DeviceInfoResponse createDeviceInfoDulicateIdResponse;
    private DeviceInfoResponse deviceInfoResponse;
    private DeviceInfo existingDeviceInfo;

    @BeforeEach
    void initData() {
        createDeviceInfoRequest = DeviceInfoRequest.builder()
                .name("Asus-Rog-Strix")
                .deviceUuid("12345")
                .build();
        createDeviceInfoDulicateIdRequest = DeviceInfoRequest.builder()
                .name("Asus-Rog-Strix-1")
                .deviceUuid("12345")
                .build();
        createDeviceInfoResponse = DeviceInfoResponse.builder()
                .id(100L)
                .name("Asus-Rog-Strix")
                .deviceUuid("12345")
                .status("Not Connect")
                .isDeleted(false)
                .build();
        createDeviceInfoDulicateIdResponse = DeviceInfoResponse.builder()
                .id(100L)
                .name("Asus-Rog-Strix-1")
                .deviceUuid("12345")
                .status("Not Connect")
                .isDeleted(false)
                .build();

        deviceInfoResponse = DeviceInfoResponse.builder()
                .id(21L)
                .name("ASUS-TuanVQ")
                .deviceUuid("0987654321")
                .status("Not Connect")
                .isDeleted(true)
                .build();
        existingDeviceInfo = DeviceInfo.builder()
                .id(100L)
                .name("Asus-Rog-Strix")
                .deviceUuid("12345")
                .status("Not Connect")
                .isDeleted(false)
                .build();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserDetailsService testUserDetailsService() {
            User userMock = new User();
            userMock.setRole(Role.ADMIN);
            return username -> userMock;
        }
    }

    @Test
    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
    void createDevice_whenDeviceExists_updateSuccess() throws Exception {
        when(deviceInfoRepository.findByDeviceUuid(createDeviceInfoDulicateIdRequest.getDeviceUuid()))
                .thenReturn(Optional.of(existingDeviceInfo));
        when(deviceInfoService.createDevice(ArgumentMatchers.any())).thenAnswer(invocation -> {
            DeviceInfoRequest request = invocation.getArgument(0);
            if (createDeviceInfoDulicateIdRequest.getDeviceUuid().equals(request.getDeviceUuid())) {
                return createDeviceInfoDulicateIdResponse; // Cập nhật thiết bị với tên mới
            }
            return null;
        });

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/public/device-info")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(createDeviceInfoDulicateIdRequest)))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("result.name").value("Asus-Rog-Strix-1"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.deviceUuid").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.status").value("Not Connect"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.isDeleted").value(false));
    }

    @Test
    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
    void createDevice_validRequest_failure() throws Exception {
        createDeviceInfoRequest.setName("");
        when(deviceInfoService.deleteDevice(ArgumentMatchers.any())).thenReturn(deviceInfoResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/public/device-info")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(createDeviceInfoRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(422))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Unprocessable Entity"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.name").value("Tên thiết bị không được để trống"));
    }

    @Test
    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
    void deleteDevice_validRequest_success() throws Exception {
        Long idDevice = 21L;
        when(deviceInfoService.deleteDevice(ArgumentMatchers.any())).thenReturn(deviceInfoResponse);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/private/device-info/{id}", idDevice)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("result.name").value("ASUS-TuanVQ"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.deviceUuid").value("0987654321"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.status").value("Not Connect"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.isDeleted").value(true));
    }

    @Test
    @WithUserDetails(value = "john", userDetailsServiceBeanName = "testUserDetailsService")
    void deleteDevice_validRequest_failure() throws Exception {
        Long idDevice = 23L;
        doThrow(new ApiException(ErrorCode.EX_NOT_FOUND))
                .when(deviceInfoService)
                .deleteDevice(ArgumentMatchers.anyLong());
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/private/device-info/{id}", idDevice)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1008))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Không tìm thấy bản ghi"));
    }
}

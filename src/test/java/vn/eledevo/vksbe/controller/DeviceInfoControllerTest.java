package vn.eledevo.vksbe.controller;

import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
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
import vn.eledevo.vksbe.entity.User;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.service.device_info.DeviceInfoService;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceInfoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ErrorCode errorCode;

    @MockBean
    private DeviceInfoService deviceInfoService;

    private DeviceInfoRequest deviceInfoRequest;
    private DeviceInfoResponse deviceInfoResponse;

    @BeforeEach
    void initData() {
        deviceInfoRequest = DeviceInfoRequest.builder()
                .name("Asus-Rog-Strix")
                .deviceUuid("0987654321")
                .build();
        //        addDeviceInfoResponse = DeviceInfoResponse.builder()
        //                .id(22L)
        //                .name("Asus-Rog-Strix")
        //                .deviceUuid("0987654321")
        //                .status("Not Connect")
        //                .isDeleted(false)
        //                .build();

        deviceInfoResponse = DeviceInfoResponse.builder()
                .id(21L)
                .name("ASUS-TuanVQ")
                .deviceUuid("0987654321")
                .status("Not Connect")
                .isDeleted(true)
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
    void deleteDevice_validRequest_success() throws Exception {
        Long idDevice = 21L;
        Mockito.when(deviceInfoService.deleteDevice(ArgumentMatchers.any())).thenReturn(deviceInfoResponse);
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

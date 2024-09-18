// package vn.eledevo.vksbe.service;
//
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.when;
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
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.test.context.support.WithUserDetails;
//
// import vn.eledevo.vksbe.constant.Role;
// import vn.eledevo.vksbe.dto.request.DeviceInfoRequest;
// import vn.eledevo.vksbe.dto.response.DeviceInfoResponse;
// import vn.eledevo.vksbe.entity.DeviceInfo;
// import vn.eledevo.vksbe.entity.User;
// import vn.eledevo.vksbe.repository.DeviceInfoRepository;
// import vn.eledevo.vksbe.service.device_info.DeviceInfoService;
//
// @SpringBootTest
// @AutoConfigureMockMvc
// class DeviceInfoServiceTest {
//    @Autowired
//    private DeviceInfoService deviceInfoService;
//
//    @MockBean
//    private DeviceInfoRepository deviceInfoRepository;
//
//    private DeviceInfoRequest deviceInfoRequest;
//
//    private DeviceInfo deviceInfo;
//
//    @BeforeEach
//    void initData() {
//        deviceInfoRequest = DeviceInfoRequest.builder()
//                .name("Asus-Rog-Strix")
//                .deviceUuid("12345")
//                .build();
//        deviceInfo = DeviceInfo.builder()
//                .id(100L)
//                .name("Asus-Rog-Strix")
//                .deviceUuid("12345")
//                .status("Not Connect")
//                .isDeleted(false)
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
//    void createDevice_validRequest_success() {
//        // Kiểm tra Id, name trả về khi tạo thiết bị
//        when(deviceInfoRepository.existsByName(anyString())).thenReturn(false);
//        when(deviceInfoRepository.save(any(DeviceInfo.class))).thenReturn(deviceInfo);
//        DeviceInfoResponse response = deviceInfoService.createDevice(deviceInfoRequest);
//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(response.getId()).isEqualTo(100L);
//        Assertions.assertThat(response.getName()).isEqualTo("Asus-Rog-Strix");
//        Assertions.assertThat(response.getDeviceUuid()).isEqualTo("12345");
//    }
// }

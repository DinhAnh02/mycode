package vn.eledevo.vksbe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.service.account.AccountService;

// @SpringBootTest
// @AutoConfigureMockMvc
// @ActiveProfiles("test")
public class AccountControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private AccountService accountService;

//     private ApiResponse<?> apiResponse;

//     @BeforeEach
//     void init() {
//         // Mock response data
//         apiResponse = new ApiResponse<>(200, "Khóa tài khoản thành công");
//     }

//     @Test
//     @WithMockUser(
//             username = "spring",
//             roles = {"IT_ADMIN"})
//     void testLockAccount_Success() throws Exception {
//         // Setup mock service response
//         when(accountService.inactivateAccount(any())).thenReturn(apiResponse);

//         // Perform the request and verify response
//         mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/private/accounts/1/inactivate"))
//                 .andExpect(MockMvcResultMatchers.status().isOk()) // Check if status is 200 OK
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OK"));
//     }

//     @Test
//     @WithMockUser(
//             username = "spring",
//             roles = {"IT_ADMIN"})
//     void testLockAccount_NotFound() throws Exception {
//         when(accountService.inactivateAccount(any())).thenReturn(apiResponse);

//         // Perform the request and expect 404 error
//         mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/private/accounts/1/inactivate"))
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Tài khoản không tồn tại"));
//     }

//     @Test
//     @WithMockUser(
//             username = "spring",
//             roles = {"IT_ADMIN"})
//     void testLockAccount_ServerError() throws Exception {
//         // Simulate server error
//         when(accountService.inactivateAccount(any())).thenThrow(new RuntimeException("Internal server error"));

//         // Perform the request and expect 500 error
//         mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/private/accounts/1/inactivate"))
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(500))
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Internal server error"));
//     }
}

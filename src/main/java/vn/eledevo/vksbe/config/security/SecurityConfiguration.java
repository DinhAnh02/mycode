package vn.eledevo.vksbe.config.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;
import vn.eledevo.vksbe.constant.Role;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
        "/api/v1/auth/authenticate",
        "/api/v1/auth/createAccountTest",
        "/api/v1/private/categories",
        "/v2/api-docs",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui/**",
        "/webjars/**",
        "/swagger-ui.html",
        "/api/v1/private/accounts/download-image/{fileName:.+}",
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL)
                        .permitAll()
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/api/v1/private/accounts/get-user-info",
                                "/api/v1/private/accounts/change-pin-code",
                                "/api/v1/private/accounts/{id}/update-avatar-user-info")
                        .hasAnyAuthority(
                                Role.IT_ADMIN.name(),
                                Role.KIEM_SAT_VIEN.name(),
                                Role.VIEN_PHO.name(),
                                Role.VIEN_TRUONG.name(),
                                Role.TRUONG_PHONG.name(),
                                Role.PHO_PHONG.name())
                        .requestMatchers(
                                "/api/v1/private/accounts",
                                "/api/v1/private/accounts/{id}/detail",
                                "/api/v1/private/accounts/{id}/usb",
                                "/api/v1/private/accounts/{accountId}/inactivate",
                                "/api/v1/private/accounts/{accountId}/swap-account-status/{swapAccountId}",
                                "/api/v1/private/accounts/{id}/devices")
                        .hasAnyAuthority(
                                Role.VIEN_TRUONG.name(),
                                Role.VIEN_PHO.name(),
                                Role.TRUONG_PHONG.name(),
                                Role.PHO_PHONG.name(),
                                Role.IT_ADMIN.name())
                        .requestMatchers(
                                "/api/v1/private/accounts/reset-password/{id}",
                                "/api/v1/private/computers/**",
                                "/api/v1/private/usbs/**",
                                "/api/v1/private/usbs/download/{username}",
                                "/api/v1/private/accounts/{accountId}/remove-computer/{computerId}",
                                "/api/v1/private/accounts/connect-computer/{id}/computers",
                                "/api/v1/private/accounts/remove-usb/{accountId}/usb/{usbId}",
                                "/api/v1/private/accounts/create",
                                "/api/v1/private/accounts/{updatedAccId}/update-info",
                                "/api/v1/private/accounts/connect-computer/{id}/computers",
                                "/api/v1/private/computers/check-exist-computer",
                                "/api/v1/private/accounts/upload-image")
                        .hasAuthority(Role.IT_ADMIN.name())
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider) // Để xác thực người dùng trong lần đăng nhập
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter
                                .class) // Thêm bộ lọc xác thực JWT (Mỗi request đều được điều hướng đến doFilter để
                // kiểm tra)
                .logout(
                        logout -> // Cấu hình đăng xuất
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler) // Thêm xử lý đăng xuất
                                .logoutSuccessHandler((request, response, authentication) ->
                                        SecurityContextHolder
                                                .clearContext()) // Định nghĩa là đăng xuất thành công và xóa bỏ thông
                        // tin người dùng trong phiên đăng nhập trong
                        // SecurityContextHolder
                        );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

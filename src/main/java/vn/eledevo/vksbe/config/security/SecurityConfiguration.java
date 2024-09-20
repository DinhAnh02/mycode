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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
        "/api/v1/auth/authenticate",
        "/api/v1/auth/register",
        "/api/v1/public/categories/getAll",
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
        "/api/v1/auth/download"
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL)
                        .permitAll()
                        .requestMatchers("/api/v1/auth/test")
                        .hasAuthority("VIEN_TRUONG")
                        .requestMatchers("/api/v1/private/accounts/detail")
                        .hasAnyAuthority("VIEN_TRUONG", "VIEN_PHO", "TRUONG_PHONG", "PHO_PHONG", "IT_ADMIN")
                        .requestMatchers("/api/v1/private/accounts")
                        .hasAnyAuthority("VIEN_TRUONG", "VIEN_PHO", "TRUONG_PHONG", "PHO_PHONG", "IT_ADMIN")
                        .requestMatchers("/api/v1/private/computers/disconnected")
                        .hasAnyAuthority("VIEN_TRUONG", "VIEN_PHO", "TRUONG_PHONG", "PHO_PHONG", "IT_ADMIN")
                        .requestMatchers("/api/v1/private/computers/update/computer-info/{id}")
                        .hasAuthority("IT_ADMIN")
                        .requestMatchers("/api/v1/private/accounts/{accountId}/inactivate")
                        .hasAnyAuthority("VIEN_TRUONG", "VIEN_PHO", "TRUONG_PHONG", "PHO_PHONG", "IT_ADMIN")
                        .requestMatchers("/api/v1/private/computers/computer-info")
                        .hasAuthority("IT_ADMIN")
                        .requestMatchers("/api/v1/private/computers**")
                        .hasAnyAuthority("IT_ADMIN")
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

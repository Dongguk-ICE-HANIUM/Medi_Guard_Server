package hanium.dongguk.global.security.config;

import hanium.dongguk.global.constants.Constants;
import hanium.dongguk.global.security.filter.JwtAuthenticationFilter;
import hanium.dongguk.global.security.filter.JwtExceptionFilter;
import hanium.dongguk.global.security.handler.exception.CustomAccessDeniedHandler;
import hanium.dongguk.global.security.handler.exception.CustomAuthenticationEntryPointHandler;
import hanium.dongguk.global.security.handler.login.DefaultFailureHandler;
import hanium.dongguk.global.security.handler.login.DefaultSuccessHandler;
import hanium.dongguk.global.security.handler.logout.CustomLogoutHandler;
import hanium.dongguk.global.security.handler.logout.CustomLogoutSuccessHandler;
import hanium.dongguk.global.security.provider.JwtAuthenticationManager;
import hanium.dongguk.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationManager jwtAuthenticationManager;
    private final DefaultSuccessHandler defaultSuccessHandler;
    private final DefaultFailureHandler defaultFailureHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(new PortRequestMatcher(8080))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(Constants.NO_NEED_AUTH.toArray(String[]::new)).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/api/auth/normal/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(defaultSuccessHandler)
                        .failureHandler(defaultFailureHandler)
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/api/auth/logout")
                                .addLogoutHandler(customLogoutHandler)
                                .logoutSuccessHandler(customLogoutSuccessHandler)
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPointHandler)
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, jwtAuthenticationManager), LogoutFilter.class
                )
                .addFilterBefore(
                        new JwtExceptionFilter(), JwtAuthenticationFilter.class
                )
                .getOrBuild();
    }
}
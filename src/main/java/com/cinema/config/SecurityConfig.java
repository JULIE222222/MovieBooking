package com.cinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf.disable()) // 개발 환경에서만 CSRF 보호 비활성화

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/loginForm", "/joinForm","movieInfoForm").permitAll()
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/jquery/**", "/webfonts/**", "/upload/**").permitAll() // 정적 리소스 허용
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/loginForm")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .loginProcessingUrl("/loginForm")
                        .defaultSuccessUrl("/", true) // 로그인 성공 후 리디렉션 URL
                        .permitAll()
                )

                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                )

                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error") // 예외 처리
                );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


//.requestMatchers는 상단부터 적용이 되기때문에 순서를 잘 정리해놔야함
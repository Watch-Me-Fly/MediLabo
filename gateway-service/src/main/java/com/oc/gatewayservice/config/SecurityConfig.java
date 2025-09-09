package com.oc.gatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final String PATIENT_ROLE = "PATIENT";
    private final String DOCTOR_ROLE = "DOCTOR";

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    // simulate 2 accounts : doctor and patient
    @Bean
    public MapReactiveUserDetailsService userDetailsService(BCryptPasswordEncoder passwordEncoder) {

        // Patient account → limited access
        UserDetails patient = User.builder()
                .username("patient")
                .password(passwordEncoder.encode("patient123"))
                .roles(PATIENT_ROLE)
                .build();

        // Doctor account → full access
        UserDetails doctor = User.builder()
                .username("doctor")
                .password(passwordEncoder.encode("doctor123"))
                .roles(DOCTOR_ROLE)
                .build();

        return new MapReactiveUserDetailsService(patient, doctor);
    }

    // manage authorizations
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        logger.info("Configuring security for reactive gateway...");

        http.headers(headers -> headers
                .cache(ServerHttpSecurity.HeaderSpec.CacheSpec::disable)
        );

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        // public pages
                        .pathMatchers("/", "/login", "/logout").permitAll()
                        .pathMatchers("/js/**", "/css/**", "/img/**", "/static/**").permitAll()
                        // role-based dashboards
                        .pathMatchers("/dashboard/patient").hasRole(PATIENT_ROLE)
                        .pathMatchers("/dashboard/doctor", "/**", "/api/**").hasRole(DOCTOR_ROLE)
                        // all other requests must be authenticated
                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/login")
                        .authenticationSuccessHandler((webFilterExchange,
                                                       authentication) -> {
                            String role = authentication.getAuthorities().iterator().next().getAuthority();

                            String redirectUrl = role.equals("ROLE_" + PATIENT_ROLE)
                                    ? "/dashboard/patient" : "/dashboard/doctor";

                            webFilterExchange.getExchange()
                                    .getResponse()
                                    .setStatusCode(HttpStatus.FOUND);
                            webFilterExchange.getExchange()
                                    .getResponse()
                                    .getHeaders()
                                    .setLocation(URI.create(redirectUrl));
                            return Mono.empty();
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .requiresLogout(ServerWebExchangeMatchers
                                .pathMatchers(HttpMethod.GET, "/logout"))
                        .logoutSuccessHandler(((exchange, auth) -> {
                            exchange.getExchange().getResponse().setStatusCode(HttpStatus.FOUND);
                            exchange.getExchange().getResponse().getHeaders()
                                    .setLocation(URI.create(frontendBaseUrl));
                            return Mono.empty();
                        }))
                )
                .build();
    }

    // beans
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        logger.info("Password encoder");
        return new BCryptPasswordEncoder();
    }
}

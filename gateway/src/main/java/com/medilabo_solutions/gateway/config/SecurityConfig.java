package com.medilabo_solutions.gateway.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configuration class for security settings in the application using Spring WebFlux Security.
 * This class configures the security settings, including user details, password encoding,
 * and access control rules for different URLs based on roles.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * Creates a {@link BCryptPasswordEncoder} bean that provides the password hashing mechanism
     * using the BCrypt algorithm.
     *
     * @return a {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a {@link MapReactiveUserDetailsService} bean to provide in-memory user details service.
     * Defines two users:
     * <ul>
     *     <li>"user" with the role "USER" and password encoded using {@link BCryptPasswordEncoder}</li>
     *     <li>"admin" with the role "ADMIN" and password encoded using {@link BCryptPasswordEncoder}</li>
     * </ul>
     *
     * @return a {@link MapReactiveUserDetailsService} containing the defined users
     */
    @Bean
    public MapReactiveUserDetailsService mapReactiveUserDetailsService() {
        UserDetails user = User
                .withUsername("user")
                .password(bCryptPasswordEncoder().encode("pass"))
                .roles("USER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password(bCryptPasswordEncoder().encode("adminpass"))
                .roles("ADMIN")
                .build();

        return new MapReactiveUserDetailsService(user, admin);
    }

    /**
     * Configures the security rules for the application using Spring WebFlux Security.
     * The rules specify:
     * <ul>
     *     <li>Access to "/patients" is allowed for users with the role "USER" or "ADMIN".</li>
     *     <li>Access to "/notes" and "/diabetes" is restricted to users with the "ADMIN" role.</li>
     *     <li>All other requests require authentication.</li>
     * </ul>
     * CSRF protection is disabled.
     *
     * @param serverHttpSecurity the {@link ServerHttpSecurity} to configure the security settings
     * @return a {@link SecurityWebFilterChain} that applies the defined security settings
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/patients").hasAnyRole("USER", "ADMIN")
                        .pathMatchers("/notes", "/diabetes").hasRole("ADMIN")
                        .anyExchange().authenticated())
                .httpBasic(withDefaults())
                .build();
    }
}

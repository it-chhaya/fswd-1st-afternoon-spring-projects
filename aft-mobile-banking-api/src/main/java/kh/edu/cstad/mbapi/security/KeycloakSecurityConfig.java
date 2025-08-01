package kh.edu.cstad.mbapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class KeycloakSecurityConfig {


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        Converter<Jwt, Collection<GrantedAuthority>> converter = jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        };
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);

        return jwtConverter;
    }


    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        // TODO
        // All requests must be authenticated
        http.authorizeHttpRequests(request
                -> request
                .requestMatchers(HttpMethod.POST, "/api/v1/customers/**")
                .hasAnyRole("ADMIN", "STAFF")
                .requestMatchers(HttpMethod.PUT, "/api/v1/customers/**")
                .hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**")
                .hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/customers/**")
                .hasAnyRole("ADMIN", "STAFF", "CUSTOMER")
                .requestMatchers("/api/v1/accounts/**")
                .hasRole("USER")
                .requestMatchers("/media/**").permitAll()
                .anyRequest().authenticated()
        );

        // Disable form login default
        http.formLogin(form -> form.disable());
        http.csrf(token -> token.disable());

        // Set security mechanism
        // Basic Authentication (username & password)
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(Customizer.withDefaults())
        );

        // Set session to stateless
        http.sessionManagement(session
                -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

}

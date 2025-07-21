package kh.edu.cstad.mbapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // Create ADMIN
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin@123")
                .roles("ADMIN")
                .build();
        manager.createUser(admin);

        // Create STAFF
        UserDetails staff = User.builder()
                .username("staff")
                .password("{noop}staff@123")
                .roles("STAFF")
                .build();
        manager.createUser(staff);

        // Create CUSTOMER
        UserDetails customer = User.builder()
                .username("customer")
                .password("{noop}customer@123")
                .roles("CUSTOMER")
                .build();
        manager.createUser(customer);

        return manager;
    }

    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        // TODO
        // All requests must be authenticated
        http.authorizeHttpRequests(request
                -> request
                .anyRequest().authenticated()
        );

        // Disable form login default
        http.formLogin(form -> form.disable());
        http.csrf(token -> token.disable());

        // Set security mechanism
        // Basic Authentication (username & password)
        http.httpBasic(Customizer.withDefaults());

        // Set session to stateless
        http.sessionManagement(session
                -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

}

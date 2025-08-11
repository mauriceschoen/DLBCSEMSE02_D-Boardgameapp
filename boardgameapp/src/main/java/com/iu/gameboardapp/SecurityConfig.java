package com.iu.gameboardapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())  // Allow all requests
                .csrf(csrf -> csrf.disable());  // Optional: CSRF deactivate
        return http.build();
//        http
//                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for API usages
//                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
//                .httpBasic(withDefaults())     // Basic Auth activated
//                .formLogin(form -> form
//                        .defaultSuccessUrl("/api/games", true)
//                );
//        return http.build();
    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated()
//                )
//                 // .formLogin(withDefaults()); //default login form
//                .formLogin(form -> form
//                       // .loginPage("/login") // optional: if there were a custom login page
//                        .defaultSuccessUrl("/api/games", true) // redirecting after login
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User
//                .withUsername("user")
//                .password("{noop}f2e83299-6014-4f23-830e-9ae8058514c4") // {noop} no hashing, just for testing
//                .roles("USER")
//               .build();
//       return new InMemoryUserDetailsManager(user);
//    }
}

package jsl.security.config.userconfig;

import jsl.security.filter.RequestValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class UserManagerConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                            RequestValidationFilter requestValidationFilter) throws Exception {
        return httpSecurity
//                .formLogin(c -> c.defaultSuccessUrl("/person", true))
                .addFilterBefore(requestValidationFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(
                    c -> c.anyRequest().permitAll()
                ).csrf(
                    AbstractHttpConfigurer::disable
                ).build();
    }
}

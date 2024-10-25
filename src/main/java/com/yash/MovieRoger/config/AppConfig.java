package com.yash.MovieRoger.config;

import com.yash.MovieRoger.service.UserAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class AppConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserAuthService();
    }

}

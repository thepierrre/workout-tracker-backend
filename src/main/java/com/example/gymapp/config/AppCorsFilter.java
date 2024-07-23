package com.example.gymapp.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.Arrays;

//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns(
//                        "https://piotrs-workout-tracker.netlify.app",
//                        "https://workout-tracker-piotr-client-4d82d4dcd626.herokuapp.com/",
//                        "https://my-workout-tracker.work",
//                        "https://client.my-gym-tracker.work",
//                        "http://localhost:5173"
//                )
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
//}

@Component
public class AppCorsFilter extends CorsFilter {

    public AppCorsFilter(CorsConfigurationSource configSource) {
        super(configSource);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://workout-tracker-piotr-client-4d82d4dcd626.herokuapp.com", "https://client.my-gym-tracker.work", "http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST, DELETE, PUT, PATCH"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

package com.example.gymapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Bean
        public CorsFilter corsFilter() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();

            // Add allowed origins
            config.addAllowedOriginPattern("https://piotrs-workout-tracker.netlify.app");
            config.addAllowedOriginPattern("https://workout-tracker-piotr-client-4d82d4dcd626.herokuapp.com/");
            config.addAllowedOriginPattern("https://my-workout-tracker.work");
            config.addAllowedOriginPattern("https://client.my-gym-tracker.work");

            config.addAllowedMethod("*");
            config.addAllowedHeader("*");
            config.setAllowCredentials(true);
            source.registerCorsConfiguration("/**", config);
            return new CorsFilter(source);
        }
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns(
//                        "https://piotrs-workout-tracker.netlify.app",
//                        "https://workout-tracker-piotr-client-4d82d4dcd626.herokuapp.com",
//                        "https://my-workout-tracker.work",
//                        "https://client.my-gym-tracker.work"
//                )
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
//}

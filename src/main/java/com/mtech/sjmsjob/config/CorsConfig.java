package com.mtech.sjmsjob.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // Enable Web MVC configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow requests from any origin to the following endpoints
        registry.addMapping("/v1/jobs/**")
                .allowedOrigins("http://shiok-jobs-client-bucket.s3-website-ap-southeast-1.amazonaws.com") // You can specify specific origins instead of "*"
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(false) // You can set this to true if you need credentials (cookies, etc.) to be included in the request.
                .maxAge(3600); // Cache preflight request for 1 hour
    }
}

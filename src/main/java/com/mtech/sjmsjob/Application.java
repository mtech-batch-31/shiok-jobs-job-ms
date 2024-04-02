package com.mtech.sjmsjob;

import com.mtech.sjmsjob.config.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }

    @Bean
    public FilterRegistrationBean<LoggingFilter> loadBalancerHealthCheckHandler() {
        FilterRegistrationBean<LoggingFilter> registration = new FilterRegistrationBean();
        registration.setFilter(new LoggingFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

}

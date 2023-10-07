package com.mtech.sjmsjob.util;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@ComponentScan
@Configuration
public class AppProperties implements EnvironmentAware {

    private static Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public static String getCompanyLogoUrl(){
        String companyLogoUrl = "";
        if(environment != null)
            companyLogoUrl=environment.getRequiredProperty("sjmsjob.companyLogoUrl");
        return companyLogoUrl;
    }

}

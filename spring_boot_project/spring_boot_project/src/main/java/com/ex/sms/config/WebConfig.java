package com.ex.sms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Notes
        registry.addResourceHandler("/uploads/notes/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/notes/");
        
        // Assignments
        registry.addResourceHandler("/uploads/assignments/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/assignments/");
    }
}

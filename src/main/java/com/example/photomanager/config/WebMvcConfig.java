package com.example.photomanager.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 支持 CORS 跨域请求
 *
 * @author alex
 */
//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                .maxAge(3600)
////                .allowCredentials(true)
//        ;
//    }
}

//package com.sparta.jaejunproject.security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//@Configuration
//@EnableWebMvc
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
////                .allowedOriginPatterns("http://localhost:3000")
//                .allowedMethods(
//                        HttpMethod.GET.name(),
//                        HttpMethod.HEAD.name(),
//                        HttpMethod.POST.name(),
//                        HttpMethod.PUT.name(),
//                        HttpMethod.PATCH.name(),
//                        HttpMethod.DELETE.name(),
//                        HttpMethod.OPTIONS.name())
//                .allowedHeaders("Authorization", "Refresh-Token")   //
//                .exposedHeaders("Authorization", "Refresh-Token")
////                .allowCredentials(true)
//                .maxAge(3600L);   //쿠키 사용시 붙여줘야함
//    }
//}
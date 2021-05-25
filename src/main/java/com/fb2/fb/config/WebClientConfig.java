//package com.fb2.fb.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@RestController
//public class WebClientConfig {
//
//
//
//
//    @Bean
//    public WebClient createWebClient() {
//        return WebClient.builder().baseUrl("http://localhost:8080")
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//    }
//
//
//
//    @Bean
//    public WebClient.Builder getWebClientBuilder() {
//        return WebClient.builder();
//    }
//}

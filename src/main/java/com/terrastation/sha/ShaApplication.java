package com.terrastation.sha;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@EnableScheduling
@SpringBootApplication
public class ShaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShaApplication.class, args);
    }
//    private CorsConfiguration buildConfig() {
////        CorsConfiguration corsConfiguration = new CorsConfiguration();
////        corsConfiguration.addAllowedOrigin("*"); // 1
////        corsConfiguration.addAllowedHeader("*"); // 2
////        corsConfiguration.addAllowedMethod("*"); // 3
////        return corsConfiguration;
////    }
////
////    @Bean
////    public CorsFilter corsFilter() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", buildConfig()); // 4
////        return new CorsFilter(source);}



}

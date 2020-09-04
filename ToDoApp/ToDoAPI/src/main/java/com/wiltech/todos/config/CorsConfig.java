package com.wiltech.todos.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Cors config.
 * Implement the CORS headers that will authorize access by clients from any domain
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Cors filter cors filter.
     * @return the cors filter
     */
    @Bean
    public CorsFilter corsFilter() {
        final CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.setAllowedMethods(Arrays.asList(
                new String[] {"OPTIONS", "GET", "POST", "PUT", "DELETE"}));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    //    @Override
    //    public void addCorsMappings(final CorsRegistry registry) {
    //
    //        registry.addMapping("/**")
    //                .allowedOrigins("http://localhost:4200")
    //                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE", "HEAD")
    //                .allowCredentials(true)
    //        ;
    //    }
}

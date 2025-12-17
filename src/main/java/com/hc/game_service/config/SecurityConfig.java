package com.hc.game_service.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Value("${key-set-uri}")
    private String keySetURI;
	
	@Value("${role}")
	private String ROLE_VALUE;
	
	@Value("${permission}")
	private String PERMISSION_VALUE;
	
	@Value("${authorities-claim}")
	private String AUTHORITIES_CLAIM;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.cors(c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("*"));
                config.setAllowedMethods(List.of("*"));
                config.setAllowedHeaders(List.of("*"));
                return config;
            };
            
            c.configurationSource(source);
        });
    	
    	Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> jwtConfigurer =
                c -> c.jwt(
                j -> j.jwkSetUri(this.keySetURI)
        );
                
        http.oauth2ResourceServer(jwtConfigurer);       
    	http.authorizeHttpRequests(c -> c.anyRequest().authenticated());
        
        http.csrf(c -> c.disable());

        return http.build();
    }

    @Bean
    public ValidatorMapper validatorMapper() {
        return new ValidatorMapper(AUTHORITIES_CLAIM, ROLE_VALUE, PERMISSION_VALUE);
    }

}


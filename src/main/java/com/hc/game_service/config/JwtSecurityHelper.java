package com.hc.game_service.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component("jwtSec")
public class JwtSecurityHelper {
	@Value("${role}")
	private String ROLE_VALUE;
	
	@Value("${permission}")
	private String PERMISSION_VALUE;
	
	@Value("${authorities-claim}")
	private String AUTHORITIES_CLAIM;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtSecurityHelper.class);
    
    public boolean hasAuthority(Authentication auth, String key) {
    	boolean exists = false;
    	
    	if (auth instanceof JwtAuthenticationToken jwt) {
    		
            Map<String, Object> tokenAttributes = jwt.getTokenAttributes();
            
        	String authorities = (String)tokenAttributes.get(AUTHORITIES_CLAIM);
        	List<String> authoritiesList = Arrays.asList(authorities.substring(1, authorities.length() - 1).split(","));
        	
        	Optional<String> roleExists = Optional.ofNullable(null);
        	Optional<String> permissionExists = Optional.ofNullable(null);
        	
        	if(key.equals("create_piece")) {
        		roleExists = authoritiesList.stream()
                        .filter((s) -> s.trim().equals(ROLE_VALUE))
                        .findFirst();
        		
        		permissionExists = authoritiesList.stream()
                        .filter((s) -> s.trim().equals(PERMISSION_VALUE))
                        .findFirst();
        	}
        	
        	exists = roleExists.isPresent() && permissionExists.isPresent();
        	
        	logger.info("hasAuthority {} {}", roleExists.isPresent(), permissionExists.isPresent());
        }
    	
    	return exists;
    }
}

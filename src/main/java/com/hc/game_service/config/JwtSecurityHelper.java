package com.hc.game_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component("jwtSec")
public class JwtSecurityHelper {
	private final ValidatorMapper validatorMapper;
	
	@Autowired
	public JwtSecurityHelper(ValidatorMapper validatorMapper) {
		this.validatorMapper = validatorMapper;
	}
    
    public boolean hasAuthority(Authentication auth, String key) {
    	boolean exists = false;
    	
    	if (auth instanceof JwtAuthenticationToken jwt) {
        	exists = this.validatorMapper.getMap().get(key).verify(jwt);
        }
    	
    	return exists;
    }
}




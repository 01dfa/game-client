package com.hc.game_service.config;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

public class PreAuthorizeCreatePiece extends AbstractPreAuthorizeValidator {
	private static final Logger logger = LoggerFactory.getLogger(PreAuthorizeCreatePiece.class);
	
	public static final String KEY = "create_piece";
	
	private final String env_role;
	private final String env_permission;
	private final String env_authority_claim;
	
	public PreAuthorizeCreatePiece(String authority_claim, String role, String permission) {
		this.env_authority_claim = authority_claim;
		this.env_role = role;
		this.env_permission = permission;
	}

	@Override
	public <T extends AbstractAuthenticationToken> boolean verify(T data) {
		JwtAuthenticationToken jwt = (JwtAuthenticationToken)data;
		
		boolean exists = false;
		
    	Map<String, Object> tokenAttributes = jwt.getTokenAttributes();
		
		String authorities = (String)tokenAttributes.get(env_authority_claim);
    	List<String> authoritiesList = Arrays.asList(authorities.substring(1, authorities.length() - 1).split(","));
    	
    	Optional<String> role = Optional.ofNullable(null);
    	Optional<String> permission = Optional.ofNullable(null);
    	
		role = authoritiesList.stream()
                .filter((s) -> s.trim().equals(env_role))
                .findFirst();
		
		permission = authoritiesList.stream()
                .filter((s) -> s.trim().equals(env_permission))
                .findFirst();
    	
    	exists = role.isPresent() && permission.isPresent();
    	
    	logger.info("hasAuthority {} {}", role.isPresent(), permission.isPresent());
		
		return exists;
	}

}

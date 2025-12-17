package com.hc.game_service.config;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValidatorMapper {
	private final Map<String, PreAuthorizeValidator> validatorMapper;
	
	public ValidatorMapper(String authority, String role, String permission) {
		this.validatorMapper = new LinkedHashMap<>();
		this.validatorMapper.put(
				PreAuthorizeCreatePiece.KEY, 
				new PreAuthorizeCreatePiece(authority, role, permission));
	}
	
	public Map<String, PreAuthorizeValidator> getMap() { return this.validatorMapper; }
}

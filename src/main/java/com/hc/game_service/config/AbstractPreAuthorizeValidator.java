package com.hc.game_service.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public abstract class AbstractPreAuthorizeValidator implements PreAuthorizeValidator {
	public abstract <T extends AbstractAuthenticationToken> boolean verify(T data);
}

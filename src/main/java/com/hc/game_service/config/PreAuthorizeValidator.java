package com.hc.game_service.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public interface PreAuthorizeValidator {
	<T extends AbstractAuthenticationToken> boolean verify(T data);
}

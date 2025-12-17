package com.hc.game_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.hc.game_service.model.PieceMovementInputDto;

@Service
public class PieceService {
	@Value("${game-session-api}")
	private String GAME_SESSION_API;
	
	@Value("${game-session-create_piece_ep}")
	private String GAME_SESSION_CREATE_PIECE_EP;
	
	public String create(PieceMovementInputDto piece) {
		WebClient webClient = WebClient.builder().baseUrl(GAME_SESSION_API).build();
    	
    	String result = webClient.post()
    			.uri(GAME_SESSION_CREATE_PIECE_EP)
    			.bodyValue(piece)
    			.retrieve()
    			.bodyToMono(String.class)
    			.block();
    	
    	return result;
	}
}

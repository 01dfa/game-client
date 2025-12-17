package com.hc.game_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hc.game_service.config.PreAuthorizeCreatePiece;
import com.hc.game_service.model.PieceMovementInputDto;
import com.hc.game_service.model.PieceMovementOutputDto;
import com.hc.game_service.service.PieceService;

@RestController
public class PieceController {
	private static final Logger logger = LoggerFactory.getLogger(PieceController.class);
	private PieceService pieceService;
	
	public PieceController(PieceService pieceService) {
		this.pieceService = pieceService;
	}

	@PostMapping("/piece")
	@PreAuthorize("@jwtSec.hasAuthority(authentication, '"+PreAuthorizeCreatePiece.KEY+"')")
	@ResponseStatus(code = HttpStatus.CREATED)
    ResponseEntity<PieceMovementOutputDto> create (
    		@RequestBody PieceMovementInputDto pieceInput, JwtAuthenticationToken auth) {
		
    	String result = this.pieceService.create(new PieceMovementInputDto(
    							auth.getName(),
    			    			pieceInput.piece_id(),
    			    			pieceInput.origin_position(),
    			    	    	pieceInput.destination_position(),
    			    	    	pieceInput.opponent_user_id(),
    			    	    	pieceInput.opponent_piece_id(),
    			    	    	pieceInput.capture()));
    	
    	return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new PieceMovementOutputDto(pieceInput.piece_id()));
    }
}

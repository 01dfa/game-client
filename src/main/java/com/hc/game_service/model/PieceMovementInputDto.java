package com.hc.game_service.model;

public record PieceMovementInputDto (
		String current_user_id,
		String piece_id,
        String origin_position,
        String destination_position,
        String opponent_user_id,
        String opponent_piece_id,
        String capture) {}

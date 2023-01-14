package com.game.exception;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(long id) {
        super(String.format("player with id=%s not found", id));
    }
}

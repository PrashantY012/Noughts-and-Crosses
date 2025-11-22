package com.example.tictactoebackend.models;

public class MakeMoveRequest {
    private String gameId;
    private String playerId;
    private Position position;

    // Getters and Setters
    public String getGameId() { return gameId; }
    public void setGameId(String gameId) { this.gameId = gameId; }
    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
}

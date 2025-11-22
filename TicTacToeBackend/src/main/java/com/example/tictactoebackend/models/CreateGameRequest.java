package com.example.tictactoebackend.models;

import com.example.tictactoebackend.models.enums.PlayerType;

public class CreateGameRequest {
    private int gridSize;
    private String player1Name;
    private String player2Name;
    private PlayerType player2Type;

    // Getters and Setters
    public int getGridSize() { return gridSize; }
    public void setGridSize(int gridSize) { this.gridSize = gridSize; }
    public String getPlayer1Name() { return player1Name; }
    public void setPlayer1Name(String player1Name) { this.player1Name = player1Name; }
    public String getPlayer2Name() { return player2Name; }
    public void setPlayer2Name(String player2Name) { this.player2Name = player2Name; }
    public PlayerType getPlayer2Type() { return player2Type; }
    public void setPlayer2Type(PlayerType player2Type) { this.player2Type = player2Type; }
}

package com.example.tictactoebackend.models;

import com.example.tictactoebackend.models.enums.PlayerType;

public class PlayerInfo {
    private String id;
    private String name;
    private char symbol;
    private PlayerType type;

    public PlayerInfo() {}

    public PlayerInfo(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.symbol = player.getBoardSymbol().getSymbol();
        this.type = player.getType();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public char getSymbol() { return symbol; }
    public void setSymbol(char symbol) { this.symbol = symbol; }
    public PlayerType getType() { return type; }
    public void setType(PlayerType type) { this.type = type; }
}
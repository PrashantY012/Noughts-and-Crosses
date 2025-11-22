package com.example.tictactoebackend.models;

import com.example.tictactoebackend.models.enums.BoardSymbol;
import com.example.tictactoebackend.models.enums.PlayerType;

import java.util.UUID;

public class Player {
    private String id;
    private String name;
    private BoardSymbol boardSymbol;
    private PlayerType type;

    public Player() {}

    public Player(String name, BoardSymbol symbol, PlayerType type) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.boardSymbol = symbol;
        this.type = type;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BoardSymbol getBoardSymbol() { return boardSymbol; }
    public void setBoardSymbol(BoardSymbol boardSymbol) { this.boardSymbol = boardSymbol; }
    public PlayerType getType() { return type; }
    public void setType(PlayerType type) { this.type = type; }
}

package com.example.tictactoebackend.models;

import com.example.tictactoebackend.models.enums.GameStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameStateResponse {
    private String gameId;
    private char[][] board;
    private int gridSize;
    private PlayerInfo player1;
    private PlayerInfo player2;
    private PlayerInfo currentPlayer;
    private GameStatus status;
    private String message;
    private String winnerId;

}

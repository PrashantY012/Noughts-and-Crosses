package com.example.tictactoebackend.models;

import com.example.tictactoebackend.models.enums.GameStatus;

import java.util.UUID;

public class GameState {
    private String gameId;
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GameStatus status;
    private String winnerId;
    private int moveCount;

    public GameState() {}

    public GameState(int gridSize, Player player1, Player player2) {
        this.gameId = UUID.randomUUID().toString();
        this.board = new Board(gridSize);
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.status = GameStatus.IN_PROGRESS;
        this.moveCount = 0;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer.getId().equals(player1.getId())) ? player2 : player1;
    }

    // Getters and Setters
    public String getGameId() { return gameId; }
    public void setGameId(String gameId) { this.gameId = gameId; }
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    public Player getPlayer1() { return player1; }
    public void setPlayer1(Player player1) { this.player1 = player1; }
    public Player getPlayer2() { return player2; }
    public void setPlayer2(Player player2) { this.player2 = player2; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(Player currentPlayer) { this.currentPlayer = currentPlayer; }
    public GameStatus getStatus() { return status; }
    public void setStatus(GameStatus status) { this.status = status; }
    public String getWinnerId() { return winnerId; }
    public void setWinnerId(String winnerId) { this.winnerId = winnerId; }
    public int getMoveCount() { return moveCount; }
    public void setMoveCount(int moveCount) { this.moveCount = moveCount; }
    public void incrementMoveCount() { this.moveCount++; }
}

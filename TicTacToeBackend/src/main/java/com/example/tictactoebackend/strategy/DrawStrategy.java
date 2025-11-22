package com.example.tictactoebackend.strategy;


import com.example.tictactoebackend.models.Board;
import com.example.tictactoebackend.models.Player;

/**
 * Strategy interface for checking if game can continue
 * Determines if a draw is inevitable
 */
public interface DrawStrategy {
    /**
     * Check if the game can still continue (no draw yet)
     * @param player1 First player
     * @param player2 Second player
     * @param board Current game board
     * @return true if game can continue, false if it's a draw
     */
    boolean canGameContinue(Player player1, Player player2, Board board);
}
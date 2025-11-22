package com.example.tictactoebackend.strategy;


import com.example.tictactoebackend.models.Board;
import com.example.tictactoebackend.models.Player;

/**
 * Strategy interface for checking win conditions
 * Follows Strategy Pattern - allows different win rules
 */
public interface WinningStrategy {
    /**
     * Check if the given player has won the game
     * @param player The player to check for win
     * @param board The current game board
     * @return true if player has won, false otherwise
     */
    boolean checkWin(Player player, Board board);
}

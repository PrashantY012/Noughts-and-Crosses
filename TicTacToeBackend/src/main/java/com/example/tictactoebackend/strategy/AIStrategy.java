package com.example.tictactoebackend.strategy;


import com.example.tictactoebackend.models.Board;
import com.example.tictactoebackend.models.Player;
import com.example.tictactoebackend.models.Position;

/**
 * Strategy interface for AI move selection
 * Allows different AI algorithms to be plugged in
 */
public interface AIStrategy {
    /**
     * Calculate the next move for the AI player
     * @param board Current game board
     * @param aiPlayer The AI player
     * @return Position where AI should move, or null if no valid moves
     */
    Position getNextMove(Board board, Player aiPlayer);
}


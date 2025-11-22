package com.example.tictactoebackend.strategy;

import com.example.tictactoebackend.models.Board;
import com.example.tictactoebackend.models.Player;
import com.example.tictactoebackend.models.Position;
import org.springframework.stereotype.Component;

/**
 * Smart AI strategy that:
 * 1. Takes winning move if available
 * 2. Blocks opponent from winning
 * 3. Takes center if available
 * 4. Takes a corner
 * 5. Takes any available position
 */
@Component
public class SmartAIStrategy implements AIStrategy {

    @Override
    public Position getNextMove(Board board, Player aiPlayer) {
        return null;
    }

    /**
     * Find a move that would result in a win for the given symbol
     */

}
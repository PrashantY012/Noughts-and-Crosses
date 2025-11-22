package com.example.tictactoebackend.strategy;


import com.example.tictactoebackend.models.Board;
import com.example.tictactoebackend.models.Player;
import org.springframework.stereotype.Component;

/**
 * Standard draw strategy for Tic Tac Toe
 * Checks if there's any possible winning line left for either player
 */
@Component
public class StandardDrawStrategy implements DrawStrategy {

    @Override
    public boolean canGameContinue(Player player1, Player player2, Board board) {
        int gridSize = board.getGridSize();
        char p1Symbol = player1.getBoardSymbol().getSymbol();
        char p2Symbol = player2.getBoardSymbol().getSymbol();

        // Check if any row can still be won
        for (int i = 0; i < gridSize; i++) {
            if (canWinInLine(board, i, -1, p1Symbol, p2Symbol, gridSize, true)) {
                return true;
            }
        }

        // Check if any column can still be won
        for (int j = 0; j < gridSize; j++) {
            if (canWinInLine(board, -1, j, p1Symbol, p2Symbol, gridSize, false)) {
                return true;
            }
        }

        // Check main diagonal
        if (canWinInDiagonal(board, p1Symbol, p2Symbol, gridSize, true)) {
            return true;
        }

        // Check anti-diagonal
        if (canWinInDiagonal(board, p1Symbol, p2Symbol, gridSize, false)) {
            return true;
        }

        // No possible winning lines left - it's a draw
        return false;
    }

    /**
     * Check if a line (row or column) can still be won by either player
     * A line can be won if it only contains one player's symbol (and empties)
     */
    private boolean canWinInLine(Board board, int row, int col, char p1, char p2,
                                 int gridSize, boolean isRow) {
        int p1Count = 0, p2Count = 0;

        for (int i = 0; i < gridSize; i++) {
            char cell = isRow ? board.getCell(row, i) : board.getCell(i, col);
            if (cell == p1) p1Count++;
            else if (cell == p2) p2Count++;
        }

        // Line can be won if only one player has marks in it
        return (p1Count > 0 && p2Count == 0) || (p2Count > 0 && p1Count == 0);
    }

    /**
     * Check if a diagonal can still be won by either player
     */
    private boolean canWinInDiagonal(Board board, char p1, char p2,
                                     int gridSize, boolean isMain) {
        int p1Count = 0, p2Count = 0;

        for (int i = 0; i < gridSize; i++) {
            char cell = isMain ? board.getCell(i, i) :
                    board.getCell(i, gridSize - i - 1);
            if (cell == p1) p1Count++;
            else if (cell == p2) p2Count++;
        }

        // Diagonal can be won if only one player has marks in it
        return (p1Count > 0 && p2Count == 0) || (p2Count > 0 && p1Count == 0);
    }
}

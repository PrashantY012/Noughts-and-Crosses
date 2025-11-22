package com.example.tictactoebackend.strategy;


import com.example.tictactoebackend.models.Board;
import com.example.tictactoebackend.models.Player;
import org.springframework.stereotype.Component;

/**
 * Standard winning strategy for Tic Tac Toe
 * Checks rows, columns, and diagonals for a win
 */
@Component
public class StandardWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWin(Player player, Board board) {
        int gridSize = board.getGridSize();
        char symbol = player.getBoardSymbol().getSymbol();

        // Check all rows
        for (int i = 0; i < gridSize; i++) {
            if (checkRow(board, i, symbol, gridSize)) {
                return true;
            }
        }

        // Check all columns
        for (int j = 0; j < gridSize; j++) {
            if (checkColumn(board, j, symbol, gridSize)) {
                return true;
            }
        }

        // Check main diagonal (top-left to bottom-right)
        if (checkMainDiagonal(board, symbol, gridSize)) {
            return true;
        }

        // Check anti-diagonal (top-right to bottom-left)
        if (checkAntiDiagonal(board, symbol, gridSize)) {
            return true;
        }

        return false;
    }

    /**
     * Check if a row has all the same symbols
     */
    private boolean checkRow(Board board, int row, char symbol, int gridSize) {
        for (int j = 0; j < gridSize; j++) {
            if (board.getCell(row, j) != symbol) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if a column has all the same symbols
     */
    private boolean checkColumn(Board board, int col, char symbol, int gridSize) {
        for (int i = 0; i < gridSize; i++) {
            if (board.getCell(i, col) != symbol) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check main diagonal (top-left to bottom-right)
     */
    private boolean checkMainDiagonal(Board board, char symbol, int gridSize) {
        for (int i = 0; i < gridSize; i++) {
            if (board.getCell(i, i) != symbol) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check anti-diagonal (top-right to bottom-left)
     */
    private boolean checkAntiDiagonal(Board board, char symbol, int gridSize) {
        for (int i = 0; i < gridSize; i++) {
            if (board.getCell(i, gridSize - i - 1) != symbol) {
                return false;
            }
        }
        return true;
    }
}

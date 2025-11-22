package com.example.tictactoebackend.models;

import com.example.tictactoebackend.models.enums.BoardSymbol;

public class Board {
    private int gridSize;
    private char[][] grid;

    public Board() {}

    public Board(int gridSize) {
        this.gridSize = gridSize;
        this.grid = new char[gridSize][gridSize];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = BoardSymbol.EMPTY.getSymbol();
            }
        }
    }

    public boolean isValidMove(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            return false;
        }
        return grid[row][col] == BoardSymbol.EMPTY.getSymbol();
    }

    public void setPosition(int row, int col, char symbol) {
        grid[row][col] = symbol;
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    // Getters and Setters
    public int getGridSize() { return gridSize; }
    public void setGridSize(int gridSize) { this.gridSize = gridSize; }
    public char[][] getGrid() { return grid; }
    public void setGrid(char[][] grid) { this.grid = grid; }
}

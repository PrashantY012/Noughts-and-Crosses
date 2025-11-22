package com.example.tictactoebackend.models;

public class Position {
    private int row;
    private int col;

    public Position() {}

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Getters and Setters
    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }
    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
}

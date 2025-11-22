package com.example.tictactoebackend.models.enums;

public enum BoardSymbol {
    X('X'), O('O'), EMPTY('_');

    private final char symbol;

    BoardSymbol(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}

package com.shoopy.minesweeper.model;

public enum Difficulty {
    //use it like this: Difficulty difficulty = Difficulty.EASY; to get the rows, cols, and mines for the easy level
    EASY(8,8,10),
    MEDIUM(10,10,30),
    HARD(12,12,60);

    private final int rows;
    private final int cols;
    private final int mines;

    Difficulty(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMines() {
        return mines;
    }

}

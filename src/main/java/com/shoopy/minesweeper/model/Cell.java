package com.shoopy.minesweeper.model;

public class Cell {
    private boolean mine;
    private boolean revealed;
    private boolean flagged;
    private int neighboringMines;

    public Cell() {
        this.mine = false;
        this.revealed = false;
        this.flagged = false;
        this.neighboringMines = 0;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public int getNeighboringMines() {
        return neighboringMines;
    }

    public void setNeighboringMines(int neighboringMines) {
        this.neighboringMines = neighboringMines;
    }
}

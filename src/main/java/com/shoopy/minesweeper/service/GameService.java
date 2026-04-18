package com.shoopy.minesweeper.service;

import com.shoopy.minesweeper.model.Cell;
import com.shoopy.minesweeper.model.Difficulty;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final BoardGenerator boardGenerator;
    private Cell[][] board;
    private int rows;
    private int cols;
    private int totalMines;
    private boolean gameOver;
    private boolean won;
    private Difficulty currentDifficulty;

    public GameService(BoardGenerator boardGenerator) {
        this.boardGenerator = boardGenerator;
        this.currentDifficulty = Difficulty.EASY;
        initializeGame(Difficulty.EASY);
    }

    private void initializeGame(Difficulty difficulty) {
        this.currentDifficulty = difficulty;
        this.rows = difficulty.getRows();
        this.cols = difficulty.getCols();
        this.totalMines = difficulty.getMines();
        this.board = boardGenerator.generateBoard(difficulty);
        this.gameOver = false;
        this.won = false;
    }

    public void resetGame(Difficulty difficulty) {
        initializeGame(difficulty);
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void revealCell(int row, int col) {
        if (gameOver || won || !isValidCell(row, col)) return;

        Cell cell = board[row][col];
        
        if (cell.isRevealed() || cell.isFlagged()) return;

        cell.setRevealed(true);

        // If mine, game over
        if (cell.isMine()) {
            gameOver = true;
            revealAllMines();
            return;
        }

        // Flood fill for empty cells (neighboringMines == 0)
        if (cell.getNeighboringMines() == 0) {
            floodFill(row, col);
        }

        checkWin();
    }

    // opens up all neighboring cells recursively if they have 0 neighboring mines
    private void floodFill(int row, int col) {
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                int newRow = row + r;
                int newCol = col + c;
                if (isValidCell(newRow, newCol)) {
                    Cell neighbor = board[newRow][newCol];
                    if (!neighbor.isRevealed() && !neighbor.isFlagged()) {
                        neighbor.setRevealed(true);
                        if (neighbor.getNeighboringMines() == 0) {
                            floodFill(newRow, newCol);
                        }
                    }
                }
            }
        }
    }

    public void toggleFlag(int row, int col) {
        if (gameOver || won || !isValidCell(row, col)) return;

        Cell cell = board[row][col];
        if (!cell.isRevealed()) {
            cell.setFlagged(!cell.isFlagged());
            checkWin();
        }
    }

    private void revealAllMines() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c].isMine()) {
                    board[r][c].setRevealed(true);
                }
            }
        }
    }

    private void checkWin() {
        // Win condition: all non-mine cells revealed
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = board[r][c];
                if (!cell.isMine() && !cell.isRevealed()) {
                    return; // Game not won yet
                }
            }
        }
        won = true;
        gameOver = true;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWon() {
        return won;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public int getFlaggedCount() {
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c].isFlagged()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getCellsLeftCount() {
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = board[r][c];
                if (!cell.isMine() && !cell.isRevealed()) {
                    count++;
                }
            }
        }
        return count;
    }

    public String getGameStatus() {
        if (gameOver && won) {
            return "won";
        } else if (gameOver) {
            return "lost";
        } else {
            return "playing";
        }
    }

}

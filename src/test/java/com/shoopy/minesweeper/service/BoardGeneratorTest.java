package com.shoopy.minesweeper.service;

import com.shoopy.minesweeper.model.Cell;
import com.shoopy.minesweeper.model.Difficulty;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardGeneratorTest {

    private final BoardGenerator generator = new BoardGenerator();

    @Test
    void testEasyBoardSize() {
        Cell[][] board = generator.generateBoard(Difficulty.EASY);
        assertEquals(8, board.length);
        assertEquals(8, board[0].length);
    }

    @Test
    void testMediumBoardSize() {
        Cell[][] board = generator.generateBoard(Difficulty.MEDIUM);
        assertEquals(10, board.length);
        assertEquals(10, board[0].length);
    }

    @Test
    void testHardBoardSize() {
        Cell[][] board = generator.generateBoard(Difficulty.HARD);
        assertEquals(12, board.length);
        assertEquals(12, board[0].length);
    }

    @Test
    void shouldPlaceCorrectNumberOfMinesEasy() {
        Cell[][] board = generator.generateBoard(Difficulty.EASY);
        int mineCount = countMines(board);
        assertEquals(10, mineCount);
    }

    @Test
    void shouldPlaceCorrectNumberOfMinesMedium() {
        Cell[][] board = generator.generateBoard(Difficulty.MEDIUM);
        int mineCount = countMines(board);
        assertEquals(30, mineCount);
    }

    @Test
    void shouldPlaceCorrectNumberOfMinesHard() {
        Cell[][] board = generator.generateBoard(Difficulty.HARD);
        int mineCount = countMines(board);
        assertEquals(60, mineCount);
    }

    @Test
    void allCellsShouldBeInitializedAsNotRevealed() {
        Cell[][] board = generator.generateBoard(Difficulty.EASY);
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                assertFalse(board[r][c].isRevealed(), "Cell at [" + r + "][" + c + "] should not be revealed");
            }
        }
    }

    @Test
    void allCellsShouldBeInitializedAsNotFlagged() {
        Cell[][] board = generator.generateBoard(Difficulty.EASY);
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                assertFalse(board[r][c].isFlagged(), "Cell at [" + r + "][" + c + "] should not be flagged");
            }
        }
    }

    @Test
    void shouldCalculateNeighboringMinesCorrectly() {
        Cell[][] board = generator.generateBoard(Difficulty.EASY);
        
        // Check all non-mine cells have correct neighboring mine count
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (!board[r][c].isMine()) {
                    int expectedCount = countNeighboringMines(board, r, c);
                    assertEquals(expectedCount, board[r][c].getNeighboringMines(),
                            "Cell at [" + r + "][" + c + "] has incorrect neighboring mine count");
                }
            }
        }
    }

    @Test
    void mineCellsShouldHaveNeighboringMinesZero() {
        Cell[][] board = generator.generateBoard(Difficulty.EASY);
        
        // All mines should have neighboringMines = 0
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c].isMine()) {
                    assertEquals(0, board[r][c].getNeighboringMines(),
                            "Mine at [" + r + "][" + c + "] should have 0 neighboring mines");
                }
            }
        }
    }

    // Helper method to count total mines on the board
    private int countMines(Cell[][] board) {
        int mineCount = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c].isMine()) {
                    mineCount++;
                }
            }
        }
        return mineCount;
    }

    // Helper method to count neighboring mines for a given cell
    private int countNeighboringMines(Cell[][] board, int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int newRow = row + dr;
                int newCol = col + dc;
                if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length) {
                    if (board[newRow][newCol].isMine()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}

package com.shoopy.minesweeper.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

public class BoardGeneratorTest {

    @Test
    void testBoardSize() {
        BoardGenerator generator = new BoardGenerator();
        int rows = 4;
        int cols = 6;
        int mines = 5;
        int[][] board = generator.generateBoard(rows, cols, mines);

        assert(board.length == rows);
        assert(board[0].length == cols);
    }

    @Test
    void shouldPlaceCorrectNumberOfMines() {
        BoardGenerator generator = new BoardGenerator();
        int rows = 5;
        int cols = 5;
        int mines = 10;
        int[][] board = generator.generateBoard(rows, cols, mines);

        int mineCount = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == -1) mineCount++;
            }
        }

        assert(mineCount == mines);

    }

    @Test
    void shouldCalculateNeighboringMinesCorrectly() {
        BoardGenerator generator = new BoardGenerator();
        int rows = 3;
        int cols = 3;
        int mines = 1;
        int[][] board = generator.generateBoard(rows, cols, mines);

        // Count the number of mines around the center cell (1,1)
        int expectedCount = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (board[1 + dr][1 + dc] == -1) expectedCount++;
            }
        }

        assert(board[1][1] == expectedCount);
    }
}

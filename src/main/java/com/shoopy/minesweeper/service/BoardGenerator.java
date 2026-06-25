package com.shoopy.minesweeper.service;

import com.shoopy.minesweeper.model.Cell;
import com.shoopy.minesweeper.model.Difficulty;
import org.springframework.stereotype.Service;

@Service
public class BoardGenerator {

        public Cell[][] generateBoard(Difficulty difficulty) {
            return generateBoardWithSafeCell(difficulty, -1, -1); // No safe cell specified
        }

        // ensures that no bombs are placed at the safe cell; used when the user clicks on a cell for the first time
        public Cell[][] generateBoardWithSafeCell(Difficulty difficulty, int safeRow, int safeCol) {
            int rows = difficulty.getRows();
            int cols = difficulty.getCols();
            int mines = difficulty.getMines();

            Cell[][] board = new Cell[rows][cols];

            // Initialize all cells
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    board[r][c] = new Cell();
                }
            }

            // Place mines randomly, but avoid the safe cell and its neighbors
            for (int i = 0; i < mines; ) {
                int r = (int) (Math.random() * rows);
                int c = (int) (Math.random() * cols);
                // Check if the cell is the safe cell or its neighbors
                if (board[r][c].isMine() || (r == safeRow && c == safeCol) || (Math.abs(r - safeRow) <= 1 && Math.abs(c - safeCol) <= 1)) {
                    continue; // Skip if it's already a mine or if it's the safe cell or its neighbors
                }
                board[r][c].setMine(true);
                i++;

            }
            // Calculate neighboring mines for each cell
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (board[r][c].isMine()) continue; // Skip mines
                    int count = 0;
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            if (r + dr >= 0 && r + dr < rows && c + dc >= 0 && c + dc < cols) {
                                if (board[r + dr][c + dc].isMine()) count++;
                            }
                        }
                    }
                    board[r][c].setNeighboringMines(count);
                }
            }

            return board;

        }
}

package com.shoopy.minesweeper.service;

import com.shoopy.minesweeper.model.Cell;
import com.shoopy.minesweeper.model.Difficulty;
import org.springframework.stereotype.Service;

@Service
public class BoardGenerator {

        public Cell[][] generateBoard(Difficulty difficulty) {
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

            // Place mines randomly
            for (int i = 0; i < mines; ) {
                int r = (int) (Math.random() * rows);
                int c = (int) (Math.random() * cols);
                if (!board[r][c].isMine()) { // No mine here yet
                    board[r][c].setMine(true);
                    i++;
                }
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

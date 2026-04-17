package com.shoopy.minesweeper.service;

import org.springframework.stereotype.Service;

@Service
public class BoardGenerator {

        public int[][] generateBoard(int rows, int cols, int mines) {
            int[][] board = new int[rows][cols];

            // Place mines randomly
            for (int i = 0; i < mines; ) {
                int r = (int) (Math.random() * rows);
                int c = (int) (Math.random() * cols);
                if (board[r][c] == 0) { // No mine here yet
                    board[r][c] = -1; // Place a mine
                    i++;
                }
            }

            // Calculate neighboring mines for each cell
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (board[r][c] == -1) continue; // Skip mines
                    int count = 0;
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            if (r + dr >= 0 && r + dr < rows && c + dc >= 0 && c + dc < cols) {
                                if (board[r + dr][c + dc] == -1) count++;
                            }
                        }
                    }
                    board[r][c] = count;
                }
            }

            return board;
        }
}

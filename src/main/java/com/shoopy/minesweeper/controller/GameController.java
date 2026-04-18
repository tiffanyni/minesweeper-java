package com.shoopy.minesweeper.controller;

import com.shoopy.minesweeper.model.Cell;
import com.shoopy.minesweeper.model.Difficulty;
import com.shoopy.minesweeper.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/game")
    public String game() {
        return "game"; // -> templates/game.html
    }

    // POST endpoint to reset the game with a specified difficulty
    @PostMapping("/api/game/reset")
    @ResponseBody
    public Map<String, Object> resetGame(@RequestParam(defaultValue = "easy") String difficulty) {
        Difficulty diff = Difficulty.valueOf(difficulty.toUpperCase());
        gameService.resetGame(diff);
        return getBoardState();
    }

    // POST endpoint for left-click to reveal a cell
    @PostMapping("/api/game/reveal")
    @ResponseBody
    public Map<String, Object> revealCell(@RequestParam int row, @RequestParam int col) {
        gameService.revealCell(row, col);
        return getBoardState();
    }

    // POST endpoint for right-click to toggle flag on a cell
    @PostMapping("/api/game/flag")
    @ResponseBody
    public Map<String, Object> toggleFlag(@RequestParam int row, @RequestParam int col) {
        gameService.toggleFlag(row, col);
        return getBoardState();
    }

    // GET endpoint to return the current game state as JSON
    @GetMapping("/api/game/state")
    @ResponseBody
    public Map<String, Object> getBoardState() {
        Map<String, Object> state = new HashMap<>();
        
        // Board state
        Cell[][] board = gameService.getBoard();
        Cell[][] boardState = new Cell[gameService.getRows()][gameService.getCols()];
        
        for (int r = 0; r < gameService.getRows(); r++) {
            for (int c = 0; c < gameService.getCols(); c++) {
                boardState[r][c] = board[r][c];
            }
        }
        
        state.put("board", boardState);
        state.put("rows", gameService.getRows());
        state.put("cols", gameService.getCols());
        state.put("totalMines", gameService.getTotalMines());
        state.put("flaggedCount", gameService.getFlaggedCount());
        state.put("cellsLeft", gameService.getCellsLeftCount());
        state.put("gameOver", gameService.isGameOver());
        state.put("won", gameService.isWon());
        state.put("status", gameService.getGameStatus());
        
        return state;
    }
}

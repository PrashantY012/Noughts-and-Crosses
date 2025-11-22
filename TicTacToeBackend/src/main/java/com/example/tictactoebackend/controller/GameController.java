package com.example.tictactoebackend.controller;

import com.example.tictactoebackend.models.CreateGameRequest;
import com.example.tictactoebackend.models.GameStateResponse;
import com.example.tictactoebackend.models.MakeMoveRequest;
import com.example.tictactoebackend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*") // Enable CORS for frontend
public class GameController {

    @Autowired
    private GameService gameService;
    /**
     * Create a new game
     * POST /api/game/create
     */
    @PostMapping("/create")
    public ResponseEntity<GameStateResponse> createGame(
            @RequestBody CreateGameRequest request) {
        try {
            GameStateResponse response = gameService.createGame(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Make a move
     * POST /api/game/move
     */
    @PostMapping("/move")
    public ResponseEntity<GameStateResponse> makeMove(
            @RequestBody MakeMoveRequest request) {
        try {
            GameStateResponse response = gameService.makeMove(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get game state
     * GET /api/game/{gameId}
     */
    @GetMapping("/{gameId}")
    public ResponseEntity<GameStateResponse> getGameState(
            @PathVariable String gameId) {
        try {
            GameStateResponse response = gameService.getGameState(gameId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete game
     * DELETE /api/game/{gameId}
     */
    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable String gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.ok(null);
    }

    /**
     * Health check
     * GET /api/game/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Tic Tac Toe API is running");
    }
}

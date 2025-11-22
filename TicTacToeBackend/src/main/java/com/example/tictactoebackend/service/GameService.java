package com.example.tictactoebackend.service;


import com.example.tictactoebackend.models.*;
import com.example.tictactoebackend.models.enums.BoardSymbol;
import com.example.tictactoebackend.models.enums.GameStatus;
import com.example.tictactoebackend.models.enums.PlayerType;
import com.example.tictactoebackend.strategy.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Core game service that handles all game logic
 * Uses Strategy Pattern for win/draw/AI logic
 */
@Service
public class GameService {

    // In-memory storage of active games (game ID -> game state)
    private final Map<String, GameState> games = new ConcurrentHashMap<>();
    private WinningStrategy winningStrategy;
    private DrawStrategy drawStrategy;
    private AIStrategy aiStrategy;

   GameService(WinningStrategy winningStrategy, DrawStrategy drawStrategy, @Qualifier("randomAIStrategy") AIStrategy aiStrategy ){
        this.winningStrategy = winningStrategy;
        this.drawStrategy = drawStrategy;
        this.aiStrategy = aiStrategy;
   }

    /**
     * Create a new game
     * @param request Game configuration (grid size, player names, player types)
     * @return Initial game state
     */
    public GameStateResponse createGame(CreateGameRequest request) {
        validateGridSize(request.getGridSize());

        // Create Player 1 (always human, always X)
        Player player1 = new Player(
                request.getPlayer1Name() != null ? request.getPlayer1Name() : "Player 1",
                BoardSymbol.X,
                PlayerType.HUMAN
        );

        // Create Player 2 (can be human or AI, always O)
        //TODO: verify if player 2 type is correct or not?
        Player player2 = new Player(
                request.getPlayer2Name() != null ? request.getPlayer2Name() : "Player 2",
                BoardSymbol.O,
                request.getPlayer2Type() != null ? request.getPlayer2Type() : PlayerType.HUMAN
        );

        // Create game state with empty board
        GameState gameState = new GameState(request.getGridSize(), player1, player2);

        // Store in memory TODO: use database
        games.put(gameState.getGameId(), gameState);

        return buildResponse(gameState, "Game created successfully");
    }

    /**
     * Make a move in the game
     * If next player is AI, AI move is made automatically in the same call
     * @param request Move details (game ID, player ID, position)
     * @return Updated game state
     */
    public GameStateResponse makeMove(MakeMoveRequest request) {
        GameState gameState = games.get(request.getGameId());

        // Validation: Game exists
        if (gameState == null) {
            throw new IllegalArgumentException("Game not found");
        }

        // Validation: Game is still in progress
        if (gameState.getStatus() != GameStatus.IN_PROGRESS) {
            return buildResponse(gameState, "Game is already over");
        }

        // Validation: Correct player's turn
        if (!gameState.getCurrentPlayer().getId().equals(request.getPlayerId())) {
            return buildResponse(gameState, "It's not your turn");
        }

        Position pos = request.getPosition();

        // Validation: Move is valid (not occupied, in bounds)
        //TODO: shouldnt we set different http response for this?
        if (!gameState.getBoard().isValidMove(pos.getRow(), pos.getCol())) {
            return buildResponse(gameState, "Invalid move");
        }

        // Make the move
        gameState.getBoard().setPosition(
                pos.getRow(),
                pos.getCol(),
                gameState.getCurrentPlayer().getBoardSymbol().getSymbol()
        );
        gameState.incrementMoveCount();

        // Check win condition
        if (winningStrategy.checkWin(gameState.getCurrentPlayer(), gameState.getBoard())) {
            gameState.setStatus(
                    gameState.getCurrentPlayer().getId().equals(gameState.getPlayer1().getId())
                            ? GameStatus.PLAYER1_WON
                            : GameStatus.PLAYER2_WON
            );
            gameState.setWinnerId(gameState.getCurrentPlayer().getId());
            return buildResponse(gameState,
                    gameState.getCurrentPlayer().getName() + " wins!");
        }

        // Check draw condition
        if (!drawStrategy.canGameContinue(
                gameState.getPlayer1(),
                gameState.getPlayer2(),
                gameState.getBoard())) {
            gameState.setStatus(GameStatus.DRAW);
            return buildResponse(gameState, "Game ended in a draw!");
        }

        // Switch to next player
        gameState.switchPlayer();

        // If next player is AI, make AI move automatically
        // TODO: maybe should happen after some time break, should be handled at fronEnd
        if (gameState.getCurrentPlayer().getType() == PlayerType.AI) {
            return makeAIMove(gameState);
        }

        return buildResponse(gameState, "Move successful");
    }

    /**
     * AI makes its move (called automatically after human move)
     * @param gameState Current game state
     * @return Updated game state with AI move
     */
    private GameStateResponse makeAIMove(GameState gameState) {
        // AI calculates best move
        Position aiMove = aiStrategy.getNextMove(
                gameState.getBoard(),
                gameState.getCurrentPlayer()
        );

        if (aiMove == null) {
            gameState.setStatus(GameStatus.DRAW);
            return buildResponse(gameState, "No valid moves available");
        }

        // Make AI move
        gameState.getBoard().setPosition(
                aiMove.getRow(),
                aiMove.getCol(),
                gameState.getCurrentPlayer().getBoardSymbol().getSymbol()
        );
        gameState.incrementMoveCount();

        // Check if AI won
        if (winningStrategy.checkWin(gameState.getCurrentPlayer(), gameState.getBoard())) {
            gameState.setStatus(GameStatus.PLAYER2_WON);
            gameState.setWinnerId(gameState.getCurrentPlayer().getId());
            return buildResponse(gameState,
                    gameState.getCurrentPlayer().getName() + " wins!");
        }

        // Check draw
        if (!drawStrategy.canGameContinue(
                gameState.getPlayer1(),
                gameState.getPlayer2(),
                gameState.getBoard())) {
            gameState.setStatus(GameStatus.DRAW);
            return buildResponse(gameState, "Game ended in a draw!");
        }

        // Switch back to human player
        gameState.switchPlayer();

        return buildResponse(gameState, "AI made its move");
    }

    /**
     * Get current game state
     * @param gameId Game identifier
     * @return Current game state
     */
    public GameStateResponse getGameState(String gameId) {
        GameState gameState = games.get(gameId);
        if (gameState == null) {
            throw new IllegalArgumentException("Game not found");
        }
        return buildResponse(gameState, null);
    }

    /**
     * Delete a game
     * @param gameId Game identifier
     */
    public void deleteGame(String gameId) {
        games.remove(gameId);
    }

    /**
     * Build response DTO from game state
     * Converts internal domain model to API response format
     */
    private GameStateResponse buildResponse(GameState gameState, String message) {
        GameStateResponse response = new GameStateResponse();
        response.setGameId(gameState.getGameId());
        response.setBoard(gameState.getBoard().getGrid());
        response.setGridSize(gameState.getBoard().getGridSize());
        response.setPlayer1(new PlayerInfo(gameState.getPlayer1()));
        response.setPlayer2(new PlayerInfo(gameState.getPlayer2()));
        response.setCurrentPlayer(new PlayerInfo(gameState.getCurrentPlayer()));
        response.setStatus(gameState.getStatus());
        response.setWinnerId(gameState.getWinnerId());
        response.setMessage(message);
        return response;
    }

    /**
     * Validate grid size is within acceptable range
     */
    private void validateGridSize(int gridSize) {
        if (gridSize < 3 || gridSize > 10) {
            throw new IllegalArgumentException("Grid size must be between 3 and 10");
        }
    }
}

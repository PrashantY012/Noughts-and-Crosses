// Tic Tac Toe Frontend JavaScript
class TicTacToeGame {
    constructor() {
        this.apiBaseUrl = 'http://localhost:8080/api/game';
        this.currentGameId = null;
        this.currentGameState = null;
        this.isLoading = false;
        
        this.initializeEventListeners();
    }

    initializeEventListeners() {
        // Setup form submission
        document.getElementById('setupForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.startNewGame();
        });

        // Game control buttons
        document.getElementById('newGameBtn').addEventListener('click', () => {
            this.showGameSetup();
        });

        document.getElementById('resetGameBtn').addEventListener('click', () => {
            this.resetCurrentGame();
        });

        // Message close buttons
        document.getElementById('closeError').addEventListener('click', () => {
            this.hideMessage('errorMessage');
        });

        document.getElementById('closeSuccess').addEventListener('click', () => {
            this.hideMessage('successMessage');
        });

        // Auto-hide messages after 5 seconds
        this.setupMessageAutoHide();
    }

    setupMessageAutoHide() {
        const messages = ['errorMessage', 'successMessage'];
        messages.forEach(messageId => {
            const element = document.getElementById(messageId);
            if (element) {
                element.addEventListener('animationend', () => {
                    setTimeout(() => {
                        this.hideMessage(messageId);
                    }, 5000);
                });
            }
        });
    }

    async startNewGame() {
        if (this.isLoading) return;

        const formData = new FormData(document.getElementById('setupForm'));
        const gameConfig = {
            gridSize: parseInt(formData.get('gridSize')),
            player1Name: formData.get('player1Name'),
            player2Name: formData.get('player2Name'),
            player2Type: formData.get('player2Type')
        };

        this.showLoading(true);
        
        try {
            const response = await this.makeApiCall('/create', 'POST', gameConfig);
            
            if (response.ok) {
                this.currentGameState = await response.json();
                this.currentGameId = this.currentGameState.gameId;
                this.initializeGame();
                this.showGameInterface();
                this.showMessage('successMessage', 'Game started successfully!');
            } else {
                throw new Error('Failed to create game');
            }
        } catch (error) {
            console.error('Error starting game:', error);
            this.showMessage('errorMessage', 'Failed to start game. Please try again.');
        } finally {
            this.showLoading(false);
        }
    }

    async makeMove(row, col) {
        if (this.isLoading || !this.currentGameId || !this.currentGameState) return;

        // Check if it's the current player's turn
        const currentPlayer = this.currentGameState.currentPlayer;
        if (!currentPlayer || currentPlayer.type === 'AI') {
            this.showMessage('errorMessage', 'Wait for your turn!');
            return;
        }

        // Check if the cell is already occupied
        if (this.currentGameState.board[row][col] !== '\u0000') {
            this.showMessage('errorMessage', 'This cell is already occupied!');
            return;
        }

        this.showLoading(true);

        try {
            const moveRequest = {
                gameId: this.currentGameId,
                playerId: currentPlayer.id,
                position: { row, col }
            };

            const response = await this.makeApiCall('/move', 'POST', moveRequest);
            
            if (response.ok) {
                this.currentGameState = await response.json();
                this.updateGameDisplay();
                this.checkGameEnd();
                
                // If it's AI's turn, make AI move after a short delay
                if (this.currentGameState.currentPlayer && 
                    this.currentGameState.currentPlayer.type === 'AI' && 
                    this.currentGameState.status === 'IN_PROGRESS') {
                    setTimeout(() => this.makeAIMove(), 1000);
                }
            } else {
                const errorData = await response.json().catch(() => ({}));
                throw new Error(errorData.message || 'Invalid move');
            }
        } catch (error) {
            console.error('Error making move:', error);
            this.showMessage('errorMessage', error.message || 'Failed to make move. Please try again.');
        } finally {
            this.showLoading(false);
        }
    }

    async makeAIMove() {
        if (this.isLoading || !this.currentGameId || !this.currentGameState) return;

        this.showLoading(true);

        try {
            const currentPlayer = this.currentGameState.currentPlayer;
            if (!currentPlayer || currentPlayer.type !== 'AI') {
                this.showLoading(false);
                return;
            }

            // Get a random empty cell for AI move
            const emptyCells = this.getEmptyCells();
            if (emptyCells.length === 0) {
                this.showLoading(false);
                return;
            }

            const randomCell = emptyCells[Math.floor(Math.random() * emptyCells.length)];
            
            const moveRequest = {
                gameId: this.currentGameId,
                playerId: currentPlayer.id,
                position: { row: randomCell.row, col: randomCell.col }
            };

            const response = await this.makeApiCall('/move', 'POST', moveRequest);
            
            if (response.ok) {
                this.currentGameState = await response.json();
                this.updateGameDisplay();
                this.checkGameEnd();
            } else {
                throw new Error('AI move failed');
            }
        } catch (error) {
            console.error('Error making AI move:', error);
            this.showMessage('errorMessage', 'AI move failed. Please try again.');
        } finally {
            this.showLoading(false);
        }
    }

    getEmptyCells() {
        const emptyCells = [];
        for (let row = 0; row < this.currentGameState.board.length; row++) {
            for (let col = 0; col < this.currentGameState.board[row].length; col++) {
                if (this.currentGameState.board[row][col] === '\u0000') {
                    emptyCells.push({ row, col });
                }
            }
        }
        return emptyCells;
    }

    async resetCurrentGame() {
        if (!this.currentGameId || this.isLoading) return;

        this.showLoading(true);

        try {
            await this.makeApiCall(`/${this.currentGameId}`, 'DELETE');
            this.currentGameId = null;
            this.currentGameState = null;
            this.showGameSetup();
            this.showMessage('successMessage', 'Game reset successfully!');
        } catch (error) {
            console.error('Error resetting game:', error);
            this.showMessage('errorMessage', 'Failed to reset game.');
        } finally {
            this.showLoading(false);
        }
    }

    initializeGame() {
        this.createGameBoard();
        this.updateGameDisplay();
    }

    createGameBoard() {
        const gameBoard = document.getElementById('gameBoard');
        gameBoard.innerHTML = '';
        
        const gridSize = this.currentGameState.gridSize;
        gameBoard.className = `game-board grid-${gridSize}`;

        for (let row = 0; row < gridSize; row++) {
            for (let col = 0; col < gridSize; col++) {
                const cell = document.createElement('button');
                cell.className = 'cell';
                cell.dataset.row = row;
                cell.dataset.col = col;
                cell.addEventListener('click', () => this.makeMove(row, col));
                gameBoard.appendChild(cell);
            }
        }
    }

    updateGameDisplay() {
        // Update board
        const cells = document.querySelectorAll('.cell');
        cells.forEach(cell => {
            const row = parseInt(cell.dataset.row);
            const col = parseInt(cell.dataset.col);
            const symbol = this.currentGameState.board[row][col];
            
            cell.textContent = symbol === '\u0000' ? '' : symbol;
            cell.className = 'cell';
            
            if (symbol === 'X') {
                cell.classList.add('x');
            } else if (symbol === 'O') {
                cell.classList.add('o');
            }
        });

        // Update player info
        this.updatePlayerInfo();
        
        // Update current player
        this.updateCurrentPlayer();
        
        // Update game status
        this.updateGameStatus();
    }

    updatePlayerInfo() {
        const player1Info = document.getElementById('player1Info');
        const player2Info = document.getElementById('player2Info');
        
        if (this.currentGameState.player1) {
            player1Info.querySelector('.player-name').textContent = this.currentGameState.player1.name;
            player1Info.querySelector('.player-symbol').textContent = this.currentGameState.player1.symbol;
        }
        
        if (this.currentGameState.player2) {
            player2Info.querySelector('.player-name').textContent = this.currentGameState.player2.name;
            player2Info.querySelector('.player-symbol').textContent = this.currentGameState.player2.symbol;
        }
    }

    updateCurrentPlayer() {
        const currentPlayerInfo = document.getElementById('currentPlayerInfo');
        const currentPlayerName = document.getElementById('currentPlayerName');
        
        if (this.currentGameState.currentPlayer) {
            currentPlayerName.textContent = this.currentGameState.currentPlayer.name;
            
            // Update active player styling
            document.querySelectorAll('.player').forEach(player => {
                player.classList.remove('active');
            });
            
            if (this.currentGameState.currentPlayer.id === this.currentGameState.player1.id) {
                document.getElementById('player1Info').classList.add('active');
            } else if (this.currentGameState.currentPlayer.id === this.currentGameState.player2.id) {
                document.getElementById('player2Info').classList.add('active');
            }
        } else {
            currentPlayerName.textContent = 'Game Over';
        }
    }

    updateGameStatus() {
        const gameStatus = document.getElementById('gameStatus');
        const status = this.currentGameState.status;
        
        gameStatus.className = 'game-status';
        
        switch (status) {
            case 'IN_PROGRESS':
                gameStatus.textContent = 'Game in progress';
                break;
            case 'PLAYER1_WON':
                gameStatus.textContent = `${this.currentGameState.player1.name} wins!`;
                gameStatus.classList.add('winner');
                this.highlightWinningCells();
                break;
            case 'PLAYER2_WON':
                gameStatus.textContent = `${this.currentGameState.player2.name} wins!`;
                gameStatus.classList.add('winner');
                this.highlightWinningCells();
                break;
            case 'DRAW':
                gameStatus.textContent = 'It\'s a draw!';
                gameStatus.classList.add('draw');
                break;
            case 'WAITING_FOR_PLAYER':
                gameStatus.textContent = 'Waiting for player...';
                break;
            default:
                gameStatus.textContent = this.currentGameState.message || 'Unknown status';
        }
    }

    highlightWinningCells() {
        // This is a simplified version - in a real implementation,
        // you might want to get the winning line from the backend
        const cells = document.querySelectorAll('.cell');
        cells.forEach(cell => {
            if (cell.textContent) {
                cell.classList.add('winning');
            }
        });
    }

    checkGameEnd() {
        const status = this.currentGameState.status;
        if (status === 'PLAYER1_WON' || status === 'PLAYER2_WON' || status === 'DRAW') {
            // Disable all cells
            document.querySelectorAll('.cell').forEach(cell => {
                cell.disabled = true;
            });
            
            // Show appropriate message
            if (status === 'DRAW') {
                this.showMessage('successMessage', 'Game ended in a draw!');
            } else {
                const winner = status === 'PLAYER1_WON' ? 
                    this.currentGameState.player1.name : 
                    this.currentGameState.player2.name;
                this.showMessage('successMessage', `🎉 ${winner} wins!`);
            }
        }
    }

    showGameSetup() {
        document.getElementById('gameSetup').style.display = 'block';
        document.getElementById('gameInterface').style.display = 'none';
        this.currentGameId = null;
        this.currentGameState = null;
    }

    showGameInterface() {
        document.getElementById('gameSetup').style.display = 'none';
        document.getElementById('gameInterface').style.display = 'block';
    }

    showLoading(show) {
        this.isLoading = show;
        document.getElementById('loadingSpinner').style.display = show ? 'flex' : 'none';
    }

    showMessage(messageId, text) {
        const messageElement = document.getElementById(messageId);
        const textElement = document.getElementById(messageId.replace('Message', 'Text'));
        
        if (messageElement && textElement) {
            textElement.textContent = text;
            messageElement.style.display = 'block';
            
            // Auto-hide after 5 seconds
            setTimeout(() => {
                this.hideMessage(messageId);
            }, 5000);
        }
    }

    hideMessage(messageId) {
        const messageElement = document.getElementById(messageId);
        if (messageElement) {
            messageElement.style.display = 'none';
        }
    }

    async makeApiCall(endpoint, method = 'GET', data = null) {
        const url = `${this.apiBaseUrl}${endpoint}`;
        const options = {
            method,
            headers: {
                'Content-Type': 'application/json',
            },
        };

        if (data && method !== 'GET') {
            options.body = JSON.stringify(data);
        }

        try {
            const response = await fetch(url, options);
            return response;
        } catch (error) {
            console.error('API call failed:', error);
            throw new Error('Network error. Please check if the backend server is running.');
        }
    }
}

// Initialize the game when the page loads
document.addEventListener('DOMContentLoaded', () => {
    new TicTacToeGame();
});

// Add some utility functions for better user experience
document.addEventListener('DOMContentLoaded', () => {
    // Add keyboard navigation support
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            // Close any open messages
            document.querySelectorAll('[id$="Message"]').forEach(msg => {
                if (msg.style.display === 'block') {
                    msg.style.display = 'none';
                }
            });
        }
    });

    // Add form validation
    const form = document.getElementById('setupForm');
    const inputs = form.querySelectorAll('input[required], select[required]');
    
    inputs.forEach(input => {
        input.addEventListener('blur', () => {
            if (!input.value.trim()) {
                input.style.borderColor = '#dc3545';
            } else {
                input.style.borderColor = '#e1e5e9';
            }
        });
    });

    // Add smooth scrolling for better UX
    document.documentElement.style.scrollBehavior = 'smooth';
});

// Add service worker registration for offline capability (optional)
if ('serviceWorker' in navigator) {
    window.addEventListener('load', () => {
        // Uncomment the following lines if you want to add offline support
        // navigator.serviceWorker.register('/sw.js')
        //     .then(registration => console.log('SW registered'))
        //     .catch(registrationError => console.log('SW registration failed'));
    });
}


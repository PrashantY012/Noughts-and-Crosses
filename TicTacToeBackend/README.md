# Tic Tac Toe Frontend

A modern, responsive web frontend for the Tic Tac Toe backend API built with HTML, CSS, and JavaScript.

## Features

- 🎮 **Interactive Game Interface**: Clean, modern UI with smooth animations
- 🤖 **AI Support**: Play against AI or another human player
- 📱 **Responsive Design**: Works perfectly on desktop, tablet, and mobile devices
- ⚡ **Real-time Updates**: Instant game state updates and move validation
- 🎨 **Beautiful Styling**: Gradient backgrounds, smooth transitions, and modern design
- ♿ **Accessibility**: Keyboard navigation and screen reader support
- 🔧 **Configurable**: Choose grid size (3x3, 4x4, 5x5) and player types

## How to Use

### 1. Start the Backend Server
Make sure your Spring Boot backend is running on `http://localhost:8080`

### 2. Open the Frontend
Simply open `index.html` in your web browser, or serve it using a local web server:

```bash
# Using Python (if installed)
python -m http.server 8000

# Using Node.js (if installed)
npx serve .

# Or just double-click index.html to open in browser
```

### 3. Play the Game
1. **Setup**: Choose grid size, enter player names, and select player 2 type (Human or AI)
2. **Start**: Click "Start Game" to begin
3. **Play**: Click on empty cells to make your moves
4. **Controls**: Use "New Game" to start over or "Reset" to clear current game

## Game Modes

- **Human vs Human**: Two players take turns on the same device
- **Human vs AI**: Play against the computer AI

## Grid Sizes

- **3x3**: Classic Tic Tac Toe
- **4x4**: Extended version with more strategy
- **5x5**: Advanced version for longer games

## API Integration

The frontend communicates with the backend through these endpoints:

- `POST /api/game/create` - Create a new game
- `POST /api/game/move` - Make a move
- `GET /api/game/{gameId}` - Get game state
- `DELETE /api/game/{gameId}` - Delete/reset game
- `GET /api/game/health` - Health check

## Browser Compatibility

- ✅ Chrome 60+
- ✅ Firefox 55+
- ✅ Safari 12+
- ✅ Edge 79+

## File Structure

```
├── index.html          # Main HTML file
├── styles.css          # CSS styling and responsive design
├── script.js           # JavaScript game logic and API integration
└── README.md           # This file
```

## Customization

### Changing the Backend URL
Edit the `apiBaseUrl` in `script.js`:

```javascript
this.apiBaseUrl = 'http://your-backend-url:port/api/game';
```

### Styling
Modify `styles.css` to customize:
- Colors and gradients
- Fonts and typography
- Layout and spacing
- Animations and transitions

### Game Logic
Extend `script.js` to add:
- Different AI strategies
- Game statistics
- Sound effects
- Local storage for game history

## Troubleshooting

### Backend Connection Issues
- Ensure the Spring Boot backend is running on port 8080
- Check browser console for CORS errors
- Verify the backend has `@CrossOrigin(origins = "*")` enabled

### Game Not Loading
- Check browser console for JavaScript errors
- Ensure all files (HTML, CSS, JS) are in the same directory
- Try refreshing the page

### Mobile Issues
- The game is fully responsive, but very small screens might need landscape mode
- Touch events are automatically handled by the browser

## Development

To extend or modify the frontend:

1. **HTML Structure**: Modify `index.html` for layout changes
2. **Styling**: Update `styles.css` for visual changes
3. **Functionality**: Edit `script.js` for game logic and API integration

The code is well-commented and follows modern JavaScript practices for easy maintenance and extension.


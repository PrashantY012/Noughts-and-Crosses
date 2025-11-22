import configObj from './config.js';
document.addEventListener("DOMContentLoaded", () => {
  const path = window.location.pathname;

  if (path.endsWith("self.html") || path.endsWith("self") || path === "/") {
    initHomePage();
  } else if (path.endsWith("game.html") || path.endsWith("game")) {
    const gameData = JSON.parse(localStorage.getItem("gameData"));
    initGamePage(gameData);
  }
  // initSharedStuff();
});


function initHomePage() {
  document.getElementById("ticTacToeForm").addEventListener("submit", async function (event) {
    event.preventDefault();
    const player1Name = document.getElementById("player1Name").value;
    const player2Name = document.getElementById("player2Name").value;
    const player2Type = document.getElementById("player2Type").value;
    const gridSize = document.getElementById("gridSize").value;
    console.log("Player 2 Type:", player2Type);
    console.log("Player 1:", player1Name);
    console.log("Player 2:", player2Name);
    console.log("Grid Size:", parseInt(gridSize));
    console.log("createUrl is: "+`${configObj.backendUrl}/api/game/create`);
    const response = await fetch(`${configObj.backendUrl}/api/game/create`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        player1Name: player1Name,
        player2Name: player2Name,
        player2Type: player2Type,
        gridSize: gridSize
      }),
    });

    if (response.ok) {
      const data = await response.json();
      console.log("Response data:", data);
      localStorage.setItem("gameData", JSON.stringify(data));
      window.location.href = "game.html";
    } else {
      console.error("Error during POST request:", response.statusText);
      alert("Failed to start the game. Please try again.");
    }
  });
}

function initGamePage(gameData) {
  console.log("darkduel Game Data:", gameData);
    
    if (!gameData) {
      alert("No game data found!");
      window.location.href = "index.html";
      return;
    }
    
    //Add Event Listener to Restart Button
    document.getElementById("restartButton").addEventListener("click",()=>{
      localStorage.removeItem("gameData");
      window.location.href = "self.html";
    })

    // Clear any old content
    const container = document.getElementById("gameContainer");
    container.innerHTML = "";

    // Player info
    const info = document.getElementById("gameInfo");
    info.textContent = `${gameData.player1.name} (X) vs ${gameData.player2.name} (O)`;

    const gameStatus = gameData.status;
    const currentPlayer = gameData.currentPlayer;
   if(gameStatus ==="IN_PROGRESS"){
    document.getElementById("gameStatus").textContent = `Current turn: ${currentPlayer.name} (${currentPlayer.symbol})`;
   } else if(gameStatus ==="PLAYER1_WON" || gameStatus ==="PLAYER2_WON" || gameStatus ==="DRAW"){
    document.getElementById("gameStatus").textContent = `Game Over! ${gameData.winnerId ?"Winner: "+  gameData.status : "It's a Draw"}`;
    document.getElementById("restartButton").style.display = "block";
   }

    // Create grid dynamically
    const grid = document.createElement("div");
    grid.classList.add("grid");
    grid.style.display = "grid";
    grid.style.gridTemplateColumns = `repeat(${gameData.gridSize}, 80px)`;
    grid.style.gap = "5px";

    console.log("darkduel Board Data:", gameData.board);
    // Fill the grid with board data
    gameData.board.forEach((row, i) => {
      row.split("").forEach((cell, j) => {
        const cellDiv = document.createElement("div");
        cellDiv.classList.add("cell");
        cellDiv.textContent = cell === "_" ? "_" : cell;
        grid.appendChild(cellDiv);

        // 👉 store row and col info
        cellDiv.dataset.row = i;
        cellDiv.dataset.col = j;

        // add click listener
        cellDiv.addEventListener("click", async (e) => {
          const row = e.target.dataset.row;
          const col = e.target.dataset.col;
          console.log(`Clicked cell: Row ${row}, Col ${col}`);
          console.log("move url is: "+`${configObj.backendUrl}/api/game/move`);
          const response = await fetch(`${configObj.backendUrl}/api/game/move`, {
            'method': "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(
              {
                gameId: gameData.gameId,
                playerId: gameData.currentPlayer.id,
                position: {
                  'row': row,
                  'col': col
                }
              }
            )
          })
          if (response.ok) {
            const data = await response.json();
            localStorage.setItem("gameData", JSON.stringify(data));
            console.log("Response data:", data, " response:", response);
            initGamePage(data); // Refresh the game page with updated data
          } else {
            console.error("Error during POST request:", response);
            alert("Failed to make the move. Please try again.");
          }

        });

      });
    });
    console.log("darkduel reached end of game js Data");

    container.appendChild(grid);
}










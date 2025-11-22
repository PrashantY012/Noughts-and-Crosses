document.addEventListener("DOMContentLoaded", function () {
  document.getElementById("cell1").addEventListener("click", async function () {
    console.log("Cell 1 clicked");
    try {
      const response = await fetch("https://example.org/post", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ row: 0, col: 0 }),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = await response.json();
      console.log("Response data:", data);
    } catch (error) {
      console.error("Error during POST request:", error);
    }
  });

  document
    .getElementById("gameForm")
    .addEventListener("submit", async function (e) {
      e.preventDefault();
      const gameType = document.getElementById("gameType").value;
      const player1 = document.getElementById("player1").value;
      const player2 = document.getElementById("player2").value;

      try {
        const response = await fetch("https://example.org/api/start-game", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            gameType,
            player1,
            player2,
          }),
        });

        if (!response.ok) {
          throw new Error("Failed to start game");
        }

        const result = await response.json();
        console.log("Game started:", result);
        window.location.href = "tictac.html";

        // You can now update the UI or redirect, etc.
      } catch (error) {
        console.error("Error:", error);
      }
    });
});

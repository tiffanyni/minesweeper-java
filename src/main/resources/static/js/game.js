let game = {
    board: [],
    rows: 8,
    cols: 8,
    totalMines: 10,
    flaggedCount: 0,
    cellsLeft: 0,
    gameOver: false,
    won: false,
    difficulty: 'easy',
    status: 'playing'
};

// Initialize game by calling backend reset endpoint
async function initGame() {
    try {
        const response = await fetch(`/api/game/reset?difficulty=${game.difficulty}`, {
            method: 'POST'
        });
        const data = await response.json();
        updateGameState(data);
        updateUI();
        renderBoard();
    } catch (error) {
        console.error('Error initializing game:', error);
        document.getElementById('gameStatus').textContent = '❌ Error loading game';
    }
}

// Call backend to reveal a cell
async function revealCell(row, col) {
    if (game.gameOver || game.won) return;

    try {
        const response = await fetch(`/api/game/reveal?row=${row}&col=${col}`, {
            method: 'POST'
        });
        const data = await response.json();
        updateGameState(data);
        updateUI();
        renderBoard();

        // Update game status message
        if (data.status === 'won') {
            document.getElementById('gameStatus').textContent = '🎉 You Won!';
            document.getElementById('gameStatus').className = 'game-status win';
        } else if (data.status === 'lost') {
            document.getElementById('gameStatus').textContent = '💥 Game Over! You hit a mine!';
            document.getElementById('gameStatus').className = 'game-status lose';
        }
    } catch (error) {
        console.error('Error revealing cell:', error);
    }
}

// call backend to chord reveal a cell (reveal neighbors if conditions met)
async function chordReveal(row, col) {
    if (game.gameOver || game.won) return;

    try {
        const response = await fetch(`/api/game/chord?row=${row}&col=${col}`, {
            method: 'POST'
        });
        const data = await response.json();
        updateGameState(data);
        updateUI();
        renderBoard();

        if (data.status === 'won') {
            document.getElementById('gameStatus').textContent = '🎉 You Won!';
            document.getElementById('gameStatus').className = 'game-status win';
        } else if (data.status === 'lost') {
            document.getElementById('gameStatus').textContent = '💥 Game Over! You hit a mine!';
            document.getElementById('gameStatus').className = 'game-status lose';
        }
    } catch (error) {
        console.error('Error chord revealing cell:', error);
    }
}

// Call backend to toggle flag on a cell
async function toggleFlag(row, col, e) {
    e.preventDefault();
    if (game.gameOver || game.won) return;

    try {
        const response = await fetch(`/api/game/flag?row=${row}&col=${col}`, {
            method: 'POST'
        });
        const data = await response.json();
        updateGameState(data);
        updateUI();
        renderBoard();
    } catch (error) {
        console.error('Error toggling flag:', error);
    }
}

// Update local game state with server response
function updateGameState(data) {
    game.board = data.board;
    game.rows = data.rows;
    game.cols = data.cols;
    game.totalMines = data.totalMines;
    game.flaggedCount = data.flaggedCount;
    game.cellsLeft = data.cellsLeft;
    game.gameOver = data.gameOver;
    game.won = data.won;
    game.status = data.status;
}

// Update UI information from game state
function updateUI() {
    document.getElementById('mineCount').textContent = game.totalMines;
    document.getElementById('flagCount').textContent = game.flaggedCount;
    document.getElementById('cellsLeft').textContent = game.cellsLeft;
}

// Render the game board from backend Cell[][] array
function renderBoard() {
    const boardEl = document.getElementById('gameBoard');
    boardEl.innerHTML = '';
    boardEl.style.gridTemplateColumns = `repeat(${game.cols}, 1fr)`;

    for (let r = 0; r < game.rows; r++) {
        for (let c = 0; c < game.cols; c++) {
            const cellData = game.board[r][c];
            const cell = document.createElement('div');
            cell.className = 'cell';
            cell.dataset.row = r;
            cell.dataset.col = c;

            if (cellData.flagged) {
                cell.classList.add('flagged');
                cell.textContent = '🚩';
            } else if (cellData.revealed) {
                cell.classList.add('revealed');
                if (cellData.mine) {
                    cell.classList.add('mine');
                    cell.textContent = '💣';
                } else if (cellData.neighboringMines === 0) {
                    cell.classList.add('empty');
                    cell.textContent = '';
                } else {
                    cell.classList.add(`num-${cellData.neighboringMines}`);
                    cell.textContent = cellData.neighboringMines;
                }
            }

            cell.addEventListener('click', () => {
                if (cellData.revealed && cellData.neighboringMines > 0) {
                    chordReveal(r, c);
                } else {
                    revealCell(r, c);
                }
            });

            cell.addEventListener('contextmenu', (e) => toggleFlag(r, c, e));

            boardEl.appendChild(cell);
        }
    }
}

document.addEventListener('DOMContentLoaded', async () => {

    // Event listeners
    document.getElementById('resetBtn').addEventListener('click', initGame);

    // Change difficulty if user clicks on difficulty buttons
    document.querySelectorAll('.difficulty-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            document.querySelectorAll('.difficulty-btn').forEach(b => b.classList.remove('active'));
            e.target.classList.add('active');
            game.difficulty = e.target.dataset.difficulty;
            document.getElementById('gameStatus').textContent = '';
            document.getElementById('gameStatus').className = 'game-status';
            initGame();
        });
    });

    // Start the game
    initGame();
});



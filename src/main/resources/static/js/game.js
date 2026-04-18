const DIFFICULTIES = {
    easy: { size: 8, mines: 10 },
    medium: { size: 10, mines: 30 },
    hard: { size: 12, mines: 60 }
};

let game = {
    size: 8,
    mines: 10,
    board: [],
    revealed: [],
    flagged: [],
    gameOver: false,
    won: false,
    difficulty: 'easy'
};

// Initialize game
function initGame() {
    const diffConfig = DIFFICULTIES[game.difficulty];
    game.size = diffConfig.size;
    game.mines = diffConfig.mines;
    game.board = Array(game.size * game.size).fill(0);
    game.revealed = Array(game.size * game.size).fill(false);
    game.flagged = Array(game.size * game.size).fill(false);
    game.gameOver = false;
    game.won = false;

    placeMines();
    calculateNumbers();
    updateUI();
    renderBoard();
}

// Place mines randomly
function placeMines() {
    let placed = 0;
    while (placed < game.mines) {
        const idx = Math.floor(Math.random() * (game.size * game.size));
        if (game.board[idx] !== 'M') {
            game.board[idx] = 'M';
            placed++;
        }
    }
}

// Calculate adjacent mine counts
function calculateNumbers() {
    for (let i = 0; i < game.board.length; i++) {
        if (game.board[i] === 'M') continue;

        let count = 0;
        const row = Math.floor(i / game.size);
        const col = i % game.size;

        for (let r = -1; r <= 1; r++) {
            for (let c = -1; c <= 1; c++) {
                const newRow = row + r;
                const newCol = col + c;
                if (newRow >= 0 && newRow < game.size && newCol >= 0 && newCol < game.size) {
                    const idx = newRow * game.size + newCol;
                    if (game.board[idx] === 'M') count++;
                }
            }
        }
        game.board[i] = count;
    }
}

// Reveal a cell
function revealCell(idx) {
    if (game.gameOver || game.won || game.revealed[idx] || game.flagged[idx]) return;

    game.revealed[idx] = true;

    if (game.board[idx] === 'M') {
        game.gameOver = true;
        revealAllMines();
        updateUI();
        renderBoard();
        document.getElementById('gameStatus').textContent = '💥 Game Over! You hit a mine!';
        document.getElementById('gameStatus').className = 'game-status lose';
        return;
    }

    // Flood fill for empty cells
    if (game.board[idx] === 0) {
        const row = Math.floor(idx / game.size);
        const col = idx % game.size;

        for (let r = -1; r <= 1; r++) {
            for (let c = -1; c <= 1; c++) {
                const newRow = row + r;
                const newCol = col + c;
                if (newRow >= 0 && newRow < game.size && newCol >= 0 && newCol < game.size) {
                    const nIdx = newRow * game.size + newCol;
                    if (!game.revealed[nIdx]) {
                        revealCell(nIdx);
                    }
                }
            }
        }
    }

    checkWin();
    updateUI();
    renderBoard();
}

// Toggle flag on a cell
function toggleFlag(idx, e) {
    e.preventDefault();
    if (game.gameOver || game.won || game.revealed[idx]) return;

    game.flagged[idx] = !game.flagged[idx];
    checkWin();
    updateUI();
    renderBoard();
}

// Reveal all mines when game over
function revealAllMines() {
    for (let i = 0; i < game.board.length; i++) {
        if (game.board[i] === 'M') {
            game.revealed[i] = true;
        }
    }
}

// Check if player won
function checkWin() {
    let cellsToReveal = 0;
    for (let i = 0; i < game.board.length; i++) {
        if (game.board[i] !== 'M' && !game.revealed[i]) {
            cellsToReveal++;
        }
    }

    if (cellsToReveal === 0) {
        game.won = true;
        game.gameOver = true;
        updateUI();
        renderBoard();
        document.getElementById('gameStatus').textContent = '🎉 You Won!';
        document.getElementById('gameStatus').className = 'game-status win';
    }
}

// Update UI information
function updateUI() {
    let cellsLeft = 0;
    for (let i = 0; i < game.board.length; i++) {
        if (game.board[i] !== 'M' && !game.revealed[i]) {
            cellsLeft++;
        }
    }

    document.getElementById('mineCount').textContent = game.mines;
    document.getElementById('flagCount').textContent = game.flagged.filter(f => f).length;
    document.getElementById('cellsLeft').textContent = cellsLeft;
}

// Render the game board
function renderBoard() {
    const boardEl = document.getElementById('gameBoard');
    boardEl.innerHTML = '';
    boardEl.style.gridTemplateColumns = `repeat(${game.size}, 1fr)`;

    for (let i = 0; i < game.board.length; i++) {
        const cell = document.createElement('div');
        cell.className = 'cell';
        cell.dataset.index = i;

        if (game.flagged[i]) {
            cell.classList.add('flagged');
            cell.textContent = '🚩';
        } else if (game.revealed[i]) {
            cell.classList.add('revealed');
            if (game.board[i] === 'M') {
                cell.classList.add('mine');
                cell.textContent = '💣';
            } else if (game.board[i] === 0) {
                cell.classList.add('empty');
                cell.textContent = '';
            } else {
                cell.classList.add(`num-${game.board[i]}`);
                cell.textContent = game.board[i];
            }
        }

        cell.addEventListener('click', () => revealCell(i));
        cell.addEventListener('contextmenu', (e) => toggleFlag(i, e));

        boardEl.appendChild(cell);
    }
}

document.addEventListener('DOMContentLoaded', async () => {

    // Event listeners
    document.getElementById('resetBtn').addEventListener('click', initGame);

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



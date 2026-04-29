// login.js

document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.querySelector('.login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent default form submission
            login();
        });
    }
});

function login() {
    const username = document.querySelector('input[name="username"]').value;
    const password = document.querySelector('input[name="password"]').value;

    // Send login request to backend API
    fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // Login successful - redirect to game page
            window.location.href = '/game';
        } else {
            // Login failed - show error message
            const errorDiv = document.getElementById('loginError');
            errorDiv.textContent = data.message;
            errorDiv.style.display = 'block';
            document.querySelector('input[name="username"]').value = '';
            document.querySelector('input[name="password"]').value = '';
            document.querySelector('input[name="username"]').focus();
        }
    })
    .catch(error => {
        console.error('Login error:', error);
        const errorDiv = document.getElementById('loginError');
        errorDiv.textContent = 'An error occurred during login. Please try again.';
        errorDiv.style.display = 'block';
    });
}

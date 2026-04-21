// login.js

document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.querySelector('.login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent actual form submission
            login();
        });
    }
});

function login() {
    // TODO: Replace with real API request
    const username = document.querySelector('input[name="username"]');
    const password = document.querySelector('input[name="password"]');
    const userVal = username.value;
    const passVal = password.value;
    console.log('Fake login with:', { username: userVal, password: passVal });
    // Simulate API call
    setTimeout(() => {
        if (userVal === 'user' && passVal === 'pass') {
            // Success: redirect to /game
            window.location.href = '/game';
        } else {
            // Failure: show error, clear fields
            const errorDiv = document.getElementById('loginError');
            errorDiv.textContent = 'Login failed. Your username or password was incorrect.';
            errorDiv.style.display = 'block';
            username.value = '';
            password.value = '';
            username.focus();
        }
    }, 500);
}

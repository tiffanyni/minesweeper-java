// register.js

document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.querySelector('.register-form');
    if (registerForm) {
        registerForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent default form submission
            register();
        });
    }
});

function register() {
    const username = document.querySelector('input[name="username"]').value;
    const password = document.querySelector('input[name="password"]').value;
    const confirmPassword = document.querySelector('input[name="confirmPassword"]').value;

    // Validate passwords match
    if (password !== confirmPassword) {
        const errorDiv = document.getElementById('registerError');
        errorDiv.textContent = 'Passwords do not match';
        errorDiv.style.display = 'block';
        return;
    }

    // Send register request to backend API
    fetch('/api/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // Registration successful - redirect to game page (auto-logged in)
            window.location.href = '/game';
        } else {
            // Registration failed - show error message
            const errorDiv = document.getElementById('registerError');
            errorDiv.textContent = data.message;
            errorDiv.style.display = 'block';
            document.querySelector('input[name="username"]').value = '';
            document.querySelector('input[name="password"]').value = '';
            document.querySelector('input[name="confirmPassword"]').value = '';
            document.querySelector('input[name="username"]').focus();
        }
    })
    .catch(error => {
        console.error('Registration error:', error);
        const errorDiv = document.getElementById('registerError');
        errorDiv.textContent = 'An error occurred during registration. Please try again.';
        errorDiv.style.display = 'block';
    });
}


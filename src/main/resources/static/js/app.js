// Global utilities
document.addEventListener('DOMContentLoaded', () => {
    // Example: Basic form validation (expand as needed)
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', (e) => {
            // Simple required field check
            const inputs = form.querySelectorAll('input[required]');
            let valid = true;
            inputs.forEach(input => {
                if (!input.value) {
                    valid = false;
                    input.style.border = '1px solid red';
                }
            });
            if (!valid) {
                e.preventDefault();
                alert('Please fill all required fields');
            }
        });
    });
});

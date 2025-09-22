// Admin JS: Handle user lists, role changes, etc.
document.addEventListener('DOMContentLoaded', () => {
    // Example: Confirm deactivation
    const deactivateButtons = document.querySelectorAll('.deactivate-btn');
    deactivateButtons.forEach(btn => {
        btn.addEventListener('click', (e) => {
            if (!confirm('Are you sure you want to deactivate this user?')) {
                e.preventDefault();
            }
        });
    });

    // TODO: Add ag-Grid for admin offers/users if needed
});

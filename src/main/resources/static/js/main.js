/**
 * Main JavaScript for SpringProjectSM7
 */
document.addEventListener('DOMContentLoaded', function() {
    // Mobile navigation toggle
    const mobileToggle = document.querySelector('.navbar-mobile-toggle');
    if (mobileToggle) {
        mobileToggle.addEventListener('click', function() {
            const navbar = document.querySelector('.navbar');
            navbar.classList.toggle('mobile-open');
        });
    }

    // Form validation
    const forms = document.querySelectorAll('form.needs-validation');
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });

    // Success alerts auto-dismiss
    const alerts = document.querySelectorAll('.alert-success, .alert-info');
    if (alerts.length > 0) {
        setTimeout(() => {
            alerts.forEach(alert => {
                const alertInstance = new bootstrap.Alert(alert);
                alertInstance.close();
            });
        }, 5000);
    }

    // DataTable initialization (if the library is available)
    if (typeof $.fn.DataTable !== 'undefined') {
        $('.data-table').DataTable({
            responsive: true,
            language: {
                search: "Buscar:",
                lengthMenu: "Mostrar _MENU_ registres",
                info: "Mostrant registres del _START_ al _END_ d'un total de _TOTAL_ registres",
                infoEmpty: "No hi ha registres disponibles",
                infoFiltered: "(filtrat de _MAX_ registres totals)",
                paginate: {
                    first: "Primer",
                    last: "Últim",
                    next: "Següent",
                    previous: "Anterior"
                }
            }
        });
    }

    // Tooltips initialization (if Bootstrap is available)
    if (typeof bootstrap !== 'undefined' && typeof bootstrap.Tooltip !== 'undefined') {
        const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        tooltipTriggerList.map(function (tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl);
        });
    }

    // Popovers initialization (if Bootstrap is available)
    if (typeof bootstrap !== 'undefined' && typeof bootstrap.Popover !== 'undefined') {
        const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
        popoverTriggerList.map(function (popoverTriggerEl) {
            return new bootstrap.Popover(popoverTriggerEl);
        });
    }

    // Confirm deletion
    const deleteButtons = document.querySelectorAll('.btn-delete, [data-confirm]');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            if (!confirm('Estàs segur que vols eliminar aquest element? Aquesta acció no es pot desfer.')) {
                e.preventDefault();
            }
        });
    });
}); 
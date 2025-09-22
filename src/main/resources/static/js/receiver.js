// Fixed Receiver JS: Initialize ag-Grid for active offers
document.addEventListener('DOMContentLoaded', () => {
    // Check if ag-Grid is loaded
    if (typeof agGrid === 'undefined') {
        console.error('ag-Grid library not loaded');
        return;
    }

    const gridOptions = {
        columnDefs: [
            { headerName: 'Title', field: 'title', sortable: true, filter: true },
            { headerName: 'Donor', field: 'donorName', sortable: true, filter: true },
            { headerName: 'Quantity', field: 'quantity', sortable: true },
            { headerName: 'Location', field: 'pickupLocation', sortable: true, filter: true },
            { headerName: 'Available From', field: 'availableFrom', sortable: true },
            { headerName: 'Expiry', field: 'expiryAt', sortable: true },
            { headerName: 'Tags', field: 'tags', filter: true },
            {
                headerName: 'Actions',
                cellRenderer: (params) => {
                    return `<a href="/offers/${params.data.id}" class="btn btn-sm btn-primary">
                                <i class="fas fa-eye"></i> View Details
                            </a>`;
                },
                width: 150
            }
        ],
        rowData: [],
        pagination: true,
        paginationPageSize: 10,
        defaultColDef: {
            resizable: true,
            sortable: true
        },
        onGridReady: (params) => {
            params.api.sizeColumnsToFit();
            loadOffers(params.api);
        }
    };

    // Use correct ag-Grid constructor
    const gridDiv = document.querySelector('#myGrid');
    if (gridDiv) {
        agGrid.createGrid(gridDiv, gridOptions); // Updated constructor for ag-Grid v30+
    }

    // Function to load offers
    function loadOffers(gridApi) {
        fetch('/offers/data')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Loaded offers:', data);
                gridApi.setGridOption('rowData', data.rows || data);
            })
            .catch(error => {
                console.error('Error loading offers:', error);
                // Show fallback message
                document.querySelector('#myGrid').innerHTML =
                    '<div class="alert alert-warning">Unable to load offers. Please try again later.</div>';
            });
    }
});

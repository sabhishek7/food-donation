// Donor JS: Initialize ag-Grid for offers list
document.addEventListener('DOMContentLoaded', () => {
    const gridOptions = {
        columnDefs: [
            { headerName: 'Title', field: 'title' },
            { headerName: 'Quantity', field: 'quantity' },
            { headerName: 'Expiry', field: 'expiryAt' },
            { headerName: 'Actions', cellRenderer: (params) => {
                return `<a href="/donor/offers/${params.data.id}/edit">Edit</a> | <a href="/donor/offers/${params.data.id}/delete">Delete</a>`;
            }}
        ],
        rowData: [],  // Populated via fetch
        pagination: true,
        paginationPageSize: 10
    };

    new agGrid.Grid(document.querySelector('#myGrid'), gridOptions);

    // Fetch data from server (server-side pagination)
    fetch('/donor/offers/data')  // Assume backend endpoint returns {rows: [], total: 0}
        .then(response => response.json())
        .then(data => {
            gridOptions.api.setRowData(data.rows);
        })
        .catch(error => console.error('Error fetching offers:', error));
});

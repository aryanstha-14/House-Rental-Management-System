// Replace with your backend API URL
const API_URL = 'http://localhost:9090/api';

// Load tenants on page load
if (document.getElementById('tenantTable')) {
  fetch(`${API_URL}/tenants`)
    .then((response) => response.json())
    .then((data) => {
      const tableBody = document.querySelector('#tenantTable tbody');
      data.forEach((tenant) => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${tenant.id}</td>
          <td>${tenant.user.username}</td>
          <td>${tenant.user.email}</td>
          <td>${tenant.user.address}</td>
        `;
        tableBody.appendChild(row);
      });
    });
}

// Base URL for the REST API
const apiBaseUrl = 'http://localhost:8080/MyWebsite-0.0.1-SNAPSHOT/rest';

// Endpoints relative to the base URL
const endpoints = {
    patients: '/patients',
    patientById: (id) => `/patients/${id}`
};

// Now, when constructing the URL for each AJAX call, you can use these constants:

// Get all patients
function getAllPatients() {
    return $.ajax({
        url: apiBaseUrl + endpoints.patients,
        type: 'GET',
        dataType: 'json',
        success: function(patients) {
            // Handle success
        },
        error: function(xhr) {
            // Handle error
        }
    });
}

// Add a new patient
function addPatient(patientData) {
    return $.ajax({
        url: apiBaseUrl + endpoints.patients,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(patientData),
        success: function(response) {
            alert('Patient added successfully!');
        },
        error: function(xhr, status, error) {
            alert('Error adding patient: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Update an existing patient
function updatePatient(patientId, patientData) {
    return $.ajax({
        url: apiBaseUrl + endpoints.patientById(patientId),
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(patientData),
        success: function() {
            alert('Patient updated successfully!');        },
        error: function(xhr) {
            alert('Error updating patient: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Delete a patient
function deletePatient(patientId) {
    return $.ajax({
        url: apiBaseUrl + endpoints.patientById(patientId),
        type: 'DELETE',
        success: function() {
            alert('Patient deleted successfully!');        },
        error: function(xhr) {
            alert('Error deleting patient: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Get a single patient by ID
function getPatient(patientId) {
    return $.ajax({
        url: apiBaseUrl + endpoints.patientById(patientId),
        type: 'GET',
        dataType: 'json',
        success: function(patient) {
            // Handle success
        },
        error: function(xhr) {
            // Handle error
        }
    });
}
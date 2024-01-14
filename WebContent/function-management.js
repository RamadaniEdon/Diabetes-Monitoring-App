// Base URL for the REST API
const apiBaseUrl = 'http://localhost:8080/MyWebsite-0.0.1-SNAPSHOT/rest';

// Endpoints relative to the base URL
const endpoints = {

    users: '/users',
    userById: (id) => `/users/${id}`,
    loginUser: '/users/login',

    patients: '/patients',
    patientById: (id) => `/patients/${id}`,

    medications: '/medications',
    medicationById: (id) => `/medications/${id}`,

    dailyRecords: '/dailyrecords',
    dailyRecordById: (id) => `/dailyrecords/${id}`,
    
};


// Get all users
function getAllUsers() {
    return $.ajax({
        url: apiBaseUrl + endpoints.users,
        type: 'GET',
        dataType: 'json',
        success: function(users) {
            // Handle success
        },
        error: function(xhr) {
            // Handle error
        }
    });
}

// Add a new user
function addUser(userData) {
    return $.ajax({
        url: apiBaseUrl + endpoints.users,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(userData),
        success: function(response) {
            alert('User added successfully!');
        },
        error: function(xhr) {
            alert('Error adding user: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Update an existing user
function updateUser(userId, userData) {
    return $.ajax({
        url: apiBaseUrl + endpoints.userById(userId),
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(userData),
        success: function() {
            alert('User updated successfully!');
        },
        error: function(xhr) {
            alert('Error updating user: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Delete a user
function deleteUser(userId) {
    return $.ajax({
        url: apiBaseUrl + endpoints.userById(userId),
        type: 'DELETE',
        success: function() {
            alert('User deleted successfully!');
        },
        error: function(xhr) {
            alert('Error deleting user: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Get a single user by ID
function getUser(userId) {
    return $.ajax({
        url: apiBaseUrl + endpoints.userById(userId),
        type: 'GET',
        dataType: 'json',
        success: function(user) {
            // Handle success
        },
        error: function(xhr) {
            // Handle error
        }
    });
}

// User login
function loginUser(userData) {
    return $.ajax({
        url: apiBaseUrl + endpoints.loginUser,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(userData),
        success: function(response) {
            // Handle success (e.g., store the token)
        },
        error: function(xhr) {
            // Handle error
        }
    });
}

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



// Get all medications
function getAllMedications() {
    return $.ajax({
        url: apiBaseUrl + endpoints.medications,
        type: 'GET',
        dataType: 'json',
        success: function(medications) {
            // Handle success
        },
        error: function(xhr) {
            // Handle error
        }
    });
}

// Add a new medication
function addMedication(medicationData) {
    return $.ajax({
        url: apiBaseUrl + endpoints.medications,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(medicationData),
        success: function(response) {
            alert('Medication added successfully!');
        },
        error: function(xhr) {
            alert('Error adding MED: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Update an existing medication
function updateMedication(medicationId, medicationData) {
    return $.ajax({
        url: apiBaseUrl + endpoints.medicationById(medicationId),
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(medicationData),
        success: function() {
            alert('Medication updated successfully!');
        },
        error: function(xhr) {
            alert('Error updating medication: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Delete a medication
function deleteMedication(medicationId) {
    return $.ajax({
        url: apiBaseUrl + endpoints.medicationById(medicationId),
        type: 'DELETE',
        success: function() {
            alert('Medication deleted successfully!');
        },
        error: function(xhr) {
            alert('Error deleting medication: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Get a single medication by ID
function getMedication(medicationId) {
    return $.ajax({
        url: apiBaseUrl + endpoints.medicationById(medicationId),
        type: 'GET',
        dataType: 'json',
        success: function(medication) {
            // Handle success
        },
        error: function(xhr) {
            // Handle error
        }
    });
}

// Get all daily records
function getAllDailyRecords() {
    return $.ajax({
        url: apiBaseUrl + endpoints.dailyRecords,
        type: 'GET',
        dataType: 'json',
        success: function(records) {
            // Handle success
        },
        error: function(xhr) {
            // Handle error
        }
    });
}

// Add a new daily record
function addDailyRecord(recordData) {
    return $.ajax({
        url: apiBaseUrl + endpoints.dailyRecords,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(recordData),
        success: function(response) {
            alert('Daily record added successfully!');
        },
        error: function(xhr) {
            alert('Error adding daily record: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Update an existing daily record
function updateDailyRecord(recordId, recordData) {
    return $.ajax({
        url: apiBaseUrl + endpoints.dailyRecordById(recordId),
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(recordData),
        success: function() {
            alert('Daily record updated successfully!');
        },
        error: function(xhr) {
            alert('Error updating daily record: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Delete a daily record
function deleteDailyRecord(recordId) {
    return $.ajax({
        url: apiBaseUrl + endpoints.dailyRecordById(recordId),
        type: 'DELETE',
        success: function() {
            alert('Daily record deleted successfully!');
        },
        error: function(xhr) {
            alert('Error deleting daily record: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

// Get a single daily record by ID
function getDailyRecord(recordId) {
    return $.ajax({
        url: apiBaseUrl + endpoints.dailyRecordById(recordId),
        type: 'GET',
        dataType: 'json',
        success: function(record) {
            // Handle success
        },
        error: function(xhr) {
            // Handle error
        }
    });
}

// Uncomment and use the following functions as needed for additional features
/*
// Get daily records within a specified period
function getDailyRecordsInPeriod(startDate, endDate) {
    return $.ajax({
        url: apiBaseUrl + endpoints.dailyRecordsInPeriod,
        type: 'GET',
        data: { startDate: startDate, endDate: endDate },
        dataType: 'json',
        success: function(records) {
            // Handle success
        },
        error: function(xhr) {
            // Handle error
        }
    });
}

// Get the average blood glucose level over a specified period
function getAverageBloodGlucose(startDate, endDate) {
    return $.ajax({
        url: apiBaseUrl + endpoints.averageBloodGlucose,
        type: 'GET',
        data: { startDate: startDate, endDate: endDate },
        dataType: 'json',
        success: function(averageGlucose) {
            // Handle success
        },
        error: function(xhr) {
            // Handle error
        }
    });
}

// Get the average carb intake over a specified period
function getAverageCarbIntake(startDate, endDate) {
    return $.ajax({
        url: apiBaseUrl + endpoints.averageCarbIntake,
        type: 'GET',
        data: { startDate: startDate, endDate: endDate },
        dataType: 'json',
        success: function(averageCarb) {
            // Handle success
        },
        error: function(xhr) {
            // Handle error
        }
    });
}
*/
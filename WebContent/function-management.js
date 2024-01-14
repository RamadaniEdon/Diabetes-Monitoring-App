
// Base URL for the REST API
const apiBaseUrl = 'http://localhost:8080/MyWebsite-0.0.1-SNAPSHOT/rest';

// Endpoints relative to the base URL
const endpoints = {
    users: '/users',
    profile: '/users/myprofile',
    userById: (id) => `/users/${id}`,
    loginUser: '/users/login',
    patients: '/patients',
    patientById: (id) => `/patients/${id}`,
    medications: '/medications',
    medicationById: (id) => `/medications/${id}`,
    dailyRecords: '/dailyrecords',
    dailyRecordById: (id) => `/dailyrecords/${id}`,
    patientRecords: (id) => `/patients/${id}/dailyrecords`,
    patientRecordAverages: (id) => `/patients/${id}/dailyrecords/average` 
};

function ajaxRequest(url, method, data = null, headersParams = null) {
    if(!getCookie('token')) {
        alert("Login First");
        window.location.href = './login.html';
        return;
    }
    var token = getCookie('token');
    if(!headersParams){
        headersParams = {
            'Authorization': token
        }
    }
    else{
        headersParams["Authorization"] = token;
    }
    return $.ajax({
        url: apiBaseUrl + url,
        type: method,
        contentType: 'application/json',
        dataType: 'json',
        data: data ? JSON.stringify(data) : null,
        headers: {...headersParams},
        success: function(response) {
        },
        error: function(xhr) {
        }
    });
}

function loginUser(username, password) {
    var data = {
        "username": username,
        "password": password
    };
    return $.ajax({
        url: apiBaseUrl + endpoints.loginUser,
        type: "POST",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        success: function(response) {
            setCookie('token', response.token, 1);
            localStorage.setItem('token', response.token); 
            window.location.href = './patients.html'; 
        },
        error: function(xhr) {
            alert('Error: ' + xhr.status + ' ' + xhr.responseText);
        }
    });

}
function getAllUsers() { return ajaxRequest(endpoints.users, 'GET'); }
function addUser(userData) { return ajaxRequest(endpoints.users, 'POST', userData); }
function updateUser(userId, userData) { return ajaxRequest(endpoints.userById(userId), 'PUT', userData); }
function deleteUser(userId) { return ajaxRequest(endpoints.userById(userId), 'DELETE'); }
function getUser(userId) { return ajaxRequest(endpoints.userById(userId), 'GET'); }

function getAllPatients() { return ajaxRequest(endpoints.patients, 'GET'); }
function addPatient(patientData) { 
    return ajaxRequest(endpoints.patients, 'POST', patientData);
}
function updatePatient(patientId, patientData) {
    return ajaxRequest(endpoints.patientById(patientId), 'PUT', patientData);
}
function deletePatient(patientId) { return ajaxRequest(endpoints.patientById(patientId), 'DELETE'); }
function getPatient(patientId) { return ajaxRequest(endpoints.patientById(patientId), 'GET'); }

function getAllMedications() { return ajaxRequest(endpoints.medications, 'GET'); }
function addMedication(medicationData) { return ajaxRequest(endpoints.medications, 'POST', medicationData); }
function updateMedication(medicationId, medicationData) { return ajaxRequest(endpoints.medicationById(medicationId), 'PUT', medicationData); }
function deleteMedication(medicationId) { 
    return ajaxRequest(endpoints.medicationById(medicationId), 'DELETE'); 
}
function getMedication(medicationId) { return ajaxRequest(endpoints.medicationById(medicationId), 'GET'); }

function getAllDailyRecords() { return ajaxRequest(endpoints.dailyRecords, 'GET'); }
function addDailyRecord(recordData) { return ajaxRequest(endpoints.dailyRecords, 'POST', recordData); }
function updateDailyRecord(recordId, recordData) { 
    return ajaxRequest(endpoints.dailyRecordById(recordId), 'PUT', recordData); }
function deleteDailyRecord(recordId) { return ajaxRequest(endpoints.dailyRecordById(recordId), 'DELETE'); }
function getDailyRecord(recordId) { return ajaxRequest(endpoints.dailyRecordById(recordId), 'GET'); }

function getPatientRecords(patientId = 8, startDate = "2011-10-10", endDate = "2011-10-10") { 
    return ajaxRequest(endpoints.patientRecords(patientId), 'GET',null,{"startDate": startDate, "endDate": endDate});
}


function getPatientAverages(patientId){
    return ajaxRequest(endpoints.patientRecordAverages(patientId), 'GET');

}

function getMyProfile() { return ajaxRequest(endpoints.profile, 'GET'); }

// Function to set a cookie
function setCookie(name, value, hours) {
    var expirationDate = new Date();
    expirationDate.setTime(expirationDate.getTime() + (hours * 60 * 60 * 1000)); // Convert hours to milliseconds
    var expires = "expires=" + expirationDate.toUTCString();
    document.cookie = name + "=" + encodeURIComponent(value) + ";" + expires + ";path=/";
}

// Example: Set a cookie named "exampleCookie" with value "exampleValue" and expiration time 1 hour

// Function to retrieve a cookie by name
function getCookie(name) {
    var decodedCookie = decodeURIComponent(document.cookie);
    var cookies = decodedCookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.indexOf(name + "=") == 0) {
            return decodeURIComponent(cookie.substring(name.length + 1));
        }
    }
    return null;
}


// Function to delete a cookie
function deleteCookie(name) {
    document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}

function logoutUser() {
    deleteCookie('token'); 
    localStorage.removeItem('token'); 
    window.location.href = './login.html'; 
}

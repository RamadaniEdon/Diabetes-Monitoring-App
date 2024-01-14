
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

// Generic AJAX request function
function ajaxRequest(url, method, data = null, headersParams = null) {
    if(!getCookie('token')) {
        // console.log(localStorage.getItem('token'))
        // console.log(getCookie('token'))
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
    console.log("EDONIIII")
    console.log(headersParams)
    return $.ajax({
        url: apiBaseUrl + url,
        type: method,
        contentType: 'application/json',
        dataType: 'json',
        data: data ? JSON.stringify(data) : null,
        headers: {...headersParams},
        success: function(response) {
            // Handle success
            console.log(response)
        },
        error: function(xhr) {
            alert('Error: ' + xhr.status + ' ' + xhr.responseText);
        }
    });
}

function loginUser(username, password) {
    var data = {
        "username": username,
        "password": password
    };
    console.log(data)
    return $.ajax({
        url: apiBaseUrl + endpoints.loginUser,
        type: "POST",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        success: function(response) {
            // Handle success (e.g., store the token and redirect)
            console.log(response)
            console.log(localStorage.getItem('token'))
            setCookie('token', response.token, 1);
            localStorage.setItem('token', response.token); // Replace with your actual token key
            window.location.href = './patients.html'; // Replace with your actual redirect page
        },
        error: function(xhr) {
            alert('Error: ' + xhr.status + ' ' + xhr.responseText);
        }
    });

}
// .then(function(response) {
//     localStorage.setItem('token', response.token);
// })
// Usage of ajaxRequest for different operations
function getAllUsers() { return ajaxRequest(endpoints.users, 'GET'); }
function addUser(userData) { return ajaxRequest(endpoints.users, 'POST', userData); }
function updateUser(userId, userData) { return ajaxRequest(endpoints.userById(userId), 'PUT', userData); }
function deleteUser(userId) { return ajaxRequest(endpoints.userById(userId), 'DELETE'); }
function getUser(userId) { return ajaxRequest(endpoints.userById(userId), 'GET'); }

function getAllPatients() { return ajaxRequest(endpoints.patients, 'GET'); }
function addPatient(patientData) { 
    // console.log(patientData)
    return ajaxRequest(endpoints.patients, 'POST', patientData);
}
function updatePatient(patientId, patientData) {
    console.log("GENTIIIII")
    console.log(patientData)
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
    console.log(recordData)
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
    deleteCookie('token'); // Delete the 'token' cookie
    localStorage.removeItem('token'); // Remove token from localStorage if it's there
    window.location.href = './login.html'; // Redirect to the login page
}

// Example: Delete a cookie named "exampleCookie"
// deleteCookie("exampleCookie");

// // Example: Retrieve the value of the "exampleCookie" cookie
// var cookieValue = getCookie("exampleCookie");
// console.log(cookieValue);

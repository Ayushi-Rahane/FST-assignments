/* ============================================================
   Assignment 8A - Google REST APIs Explorer — JavaScript
   ============================================================
   This file contains ALL the logic for:
   1. Initializing the Google API client library (gapi)
   2. Handling Google OAuth 2.0 sign-in
   3. Calling Google REST APIs (Drive, Calendar, Slides, Sheets, Docs)
   4. Displaying JSON responses on the page
   5. Logging HTTP requests and responses
   ============================================================ */

/* ---------------------------------------------------------------
   CONFIGURATION — API KEY & CLIENT ID
   ---------------------------------------------------------------
   To use Google APIs, you need two things from Google Cloud Console:
   1. API_KEY    — Identifies your project to Google (for quota tracking)
   2. CLIENT_ID  — Identifies your app for OAuth 2.0 (user sign-in)

   HOW TO GET THESE:
   a) Go to https://console.cloud.google.com/
   b) Create a new project (or select existing one)
   c) Enable these APIs: Drive, Calendar, Slides, Sheets, Docs
   d) Go to "APIs & Services" > "Credentials"
   e) Create an "API Key" → paste below as API_KEY
   f) Create an "OAuth 2.0 Client ID" (type: Web application)
      - Add http://127.0.0.1:5500 (or your local URL) to
        "Authorized JavaScript origins"
      - Paste the Client ID below as CLIENT_ID
   --------------------------------------------------------------- */

// YOUR Google Cloud API Key — replace the placeholder with your actual key
const API_KEY = 'AIzaSyARVRsvck4JvLHLorJmgARQ5Sfce3JnHnM';

// YOUR Google Cloud OAuth 2.0 Client ID — replace the placeholder with your actual client ID
const CLIENT_ID = '595508879706-7s5t1obpecdb770l0cbac8quhll38gpa.apps.googleusercontent.com';

/* ---------------------------------------------------------------
   SCOPES — What permissions we are asking from the user
   ---------------------------------------------------------------
   Scopes tell Google what data we want to access.
   Each scope is a URL that represents a specific permission.
   The user will see these permissions on the consent screen.
   --------------------------------------------------------------- */

// We request access to Drive (files), Calendar (read), Slides, Sheets, and Docs
const SCOPES = [
  'https://www.googleapis.com/auth/drive',                  // Full access to Google Drive files
  'https://www.googleapis.com/auth/calendar.readonly',      // Read-only access to calendars
  'https://www.googleapis.com/auth/presentations',          // Create/edit Google Slides
  'https://www.googleapis.com/auth/spreadsheets',           // Create/edit Google Sheets
  'https://www.googleapis.com/auth/documents',              // Create/edit Google Docs
].join(' ');
// .join(' ') combines the array into a single space-separated string
// because the Google Identity Services library expects scopes as one string

/* ---------------------------------------------------------------
   GLOBAL VARIABLES
   ---------------------------------------------------------------
   These variables are used throughout the file to track state.
   --------------------------------------------------------------- */

// tokenClient will hold the Google Identity Services token client object
// It is used to request OAuth 2.0 access tokens from Google
let tokenClient = null;

// accessToken stores the OAuth 2.0 token received after the user signs in
// This token is sent with every API request to prove the user authorized us
let accessToken = null;

// gapiLoaded tracks whether the Google API client library (gapi) has finished loading
let gapiLoaded = false;

// gisLoaded tracks whether the Google Identity Services (GIS) library has finished loading
let gisLoaded = false;


/* ================================================================
   INITIALIZATION FUNCTIONS
   ================================================================
   These functions run when the external Google libraries finish
   loading (triggered by the "onload" attributes in index.html).
   ================================================================ */

/**
 * initGapiClient() — Called when the Google API client library (gapi) loads.
 *
 * gapi.load('client', callback) loads the "client" module of gapi.
 * Once loaded, gapi.client.init() initializes it with our API key.
 * The API key is needed so Google knows which project is making requests.
 */
function initGapiClient() {
  // Load the 'client' module from gapi (Google API client)
  gapi.load('client', function () {
    // Initialize the client with our API key
    gapi.client.init({
      apiKey: API_KEY,  // Our project's API key for identification
    }).then(function () {
      // If initialization succeeds, set gapiLoaded to true
      gapiLoaded = true;
      // Log a message to the browser console for debugging
      console.log('✅ GAPI client library loaded and initialized successfully.');
    });
  });
}

/**
 * initGisClient() — Called when Google Identity Services (GIS) library loads.
 *
 * google.accounts.oauth2.initTokenClient() creates a "token client"
 * that can request OAuth 2.0 access tokens. We configure it with:
 * - client_id: Our OAuth Client ID
 * - scope: What permissions we need
 * - callback: Function to run after the user signs in
 */
function initGisClient() {
  // Create a token client using Google Identity Services
  tokenClient = google.accounts.oauth2.initTokenClient({
    client_id: CLIENT_ID,  // Our OAuth 2.0 Client ID from Google Cloud Console
    scope: SCOPES,         // The permissions we are requesting (Drive, Calendar, etc.)
    // callback is the function that runs AFTER the user completes sign-in
    callback: function (tokenResponse) {
      // tokenResponse contains the access token and other info

      // Check if we received an access_token (meaning sign-in was successful)
      if (tokenResponse && tokenResponse.access_token) {
        // Store the access token in our global variable for later use
        accessToken = tokenResponse.access_token;

        // Update the sign-in button text to show the user is signed in
        document.getElementById('btn-sign-in').innerHTML = '<i class="fa-solid fa-check"></i> Signed In';
        // Disable the button since the user is already signed in
        document.getElementById('btn-sign-in').disabled = true;
        // Show a success message next to the button
        document.getElementById('auth-status').textContent = 'Authenticated! You can now call APIs.';

        // Log success to console
        console.log('SUCCESS: OAuth 2.0 access token received successfully.');
      }
    },
  });

  // Mark GIS as loaded
  gisLoaded = true;
  // Log to console for debugging
  console.log('SUCCESS: Google Identity Services (GIS) loaded and token client initialized.');
}


/* ================================================================
   SIGN-IN HANDLER
   ================================================================
   This function is called when the user clicks the "Sign in" button.
   ================================================================ */

/**
 * handleSignIn() — Triggers the Google OAuth 2.0 sign-in popup.
 *
 * When the user clicks "Sign in with Google", this function:
 * 1. Checks that both libraries (gapi and GIS) are loaded
 * 2. Calls tokenClient.requestAccessToken() to open the Google sign-in popup
 * 3. The user selects their Google account and grants permissions
 * 4. Google returns an access token to our callback function (defined above)
 */
function handleSignIn() {
  // Check if both Google libraries have finished loading
  if (!gapiLoaded || !gisLoaded) {
    // If not loaded yet, show an alert to the user
    alert('Google libraries are still loading. Please wait a moment and try again.');
    return; // Stop execution — don't try to sign in yet
  }

  // Request an access token — this opens the Google sign-in popup window
  // The user will see a consent screen listing the permissions (scopes) we requested
  tokenClient.requestAccessToken();
}


/* ================================================================
   HELPER FUNCTIONS
   ================================================================
   Reusable utility functions used by the API-calling functions below.
   ================================================================ */

/**
 * checkAuth() — Verifies the user is signed in before making an API call.
 *
 * Every API call requires an access token. If the user hasn't signed in,
 * this function shows an alert and returns false.
 *
 * @returns {boolean} true if authenticated, false otherwise
 */
function checkAuth() {
  // If accessToken is null/undefined, the user hasn't signed in
  if (!accessToken) {
    // Show an alert telling the user to sign in first
    alert('Please sign in with Google first (Step 1).');
    // Return false to indicate authentication failed
    return false;
  }
  // Return true — the user is authenticated
  return true;
}

/**
 * showResponse(elementId, data) — Displays a JSON response in a card's response area.
 *
 * @param {string} elementId — The ID of the response-area div (e.g., "response-drive")
 * @param {object} data — The JavaScript object (API response) to display as JSON
 */
function showResponse(elementId, data) {
  // Get the response area div by its ID
  const el = document.getElementById(elementId);
  // Add the 'active' class to expand the response area (CSS transition)
  el.classList.add('active');
  // Set the innerHTML to a <pre> tag containing the formatted JSON
  // JSON.stringify(data, null, 2) converts the object to a pretty-printed JSON string
  // - data: the object to convert
  // - null: no custom replacer function
  // - 2: indent with 2 spaces for readability
  el.innerHTML = '<pre>' + JSON.stringify(data, null, 2) + '</pre>';
}

/**
 * addLogEntry(method, url, data) — Adds an entry to the HTTP Request/Response Log.
 *
 * This function creates a visual log entry showing:
 * - The HTTP method (GET/POST)
 * - The URL that was called
 * - The timestamp of the call
 * - The JSON response body
 *
 * @param {string} method — The HTTP method used ("GET" or "POST")
 * @param {string} url — The API endpoint URL that was called
 * @param {object} data — The JSON response from the API
 */
function addLogEntry(method, url, data) {
  // Get the log container div
  const log = document.getElementById('http-log');

  // Remove the "No API calls made yet" placeholder if it exists
  const placeholder = log.querySelector('.log-placeholder');
  if (placeholder) {
    placeholder.remove(); // Remove the placeholder element from the DOM
  }

  // Determine the CSS class for the method badge color
  // GET methods get green, POST methods get blue
  const methodClass = method === 'GET' ? 'get' : 'post';

  // Get the current time as a readable string (e.g., "5:30:00 PM")
  const time = new Date().toLocaleTimeString();

  // Create a new div element for this log entry
  const entry = document.createElement('div');
  // Add the 'log-entry' class for styling
  entry.className = 'log-entry';

  // Set the inner HTML of the log entry with the method, URL, time, and JSON response
  entry.innerHTML =
    '<h4>' +
    '<span class="method-badge ' + methodClass + '">' + method + '</span> ' +
    '<code>' + url + '</code> ' +
    '<span class="log-time">' + time + '</span>' +
    '</h4>' +
    '<pre>' + JSON.stringify(data, null, 2) + '</pre>';

  // Insert the new entry at the TOP of the log (most recent first)
  // prepend() adds the element as the first child
  log.prepend(entry);
}


/* ================================================================
   GOOGLE DRIVE API — List Files
   ================================================================
   HTTP Method: GET
   Endpoint:    https://www.googleapis.com/drive/v3/files
   Purpose:     Retrieve a list of files from the user's Google Drive
   Response:    JSON object with "kind", "files" array (name, id, mimeType)
   ================================================================ */

/**
 * listDriveFiles() — Calls the Google Drive API to list the user's files.
 *
 * This function:
 * 1. Checks if the user is authenticated (has an access token)
 * 2. Builds the API endpoint URL with query parameters
 * 3. Sends a GET request using the Fetch API
 * 4. Parses the JSON response
 * 5. Displays the response in the card and the log
 *
 * REST Details:
 * - Method: GET (we are reading/fetching data, not creating anything)
 * - URL: https://www.googleapis.com/drive/v3/files
 * - Query Params: pageSize=10 (limit to 10 files), fields (which data to return)
 * - Header: Authorization: Bearer <access_token> (proves we have permission)
 */
function listDriveFiles() {
  // Step 1: Check if user is signed in; if not, show alert and stop
  if (!checkAuth()) return;

  // Step 2: Define the API endpoint URL
  // We add query parameters:
  // - pageSize=10: Only return the first 10 files (not all of them)
  // - fields: Specify exactly which fields we want in the response
  //   (nextPageToken, files with id, name, and mimeType)
  const url = 'https://www.googleapis.com/drive/v3/files'
    + '?pageSize=10'
    + '&fields=nextPageToken,files(id,name,mimeType)';

  // Step 3: Send the HTTP GET request using the Fetch API
  // fetch() is the modern JavaScript way to make HTTP requests
  fetch(url, {
    method: 'GET',  // HTTP method — we are reading data
    headers: {
      // The Authorization header sends our OAuth access token
      // "Bearer" is the token type used by OAuth 2.0
      'Authorization': 'Bearer ' + accessToken,
    },
  })
    // Step 4: When we get a response, convert it from raw text to a JavaScript object
    // .json() parses the response body as JSON
    .then(function (response) {
      return response.json();
    })
    // Step 5: Once we have the parsed JSON data, display it
    .then(function (data) {
      // Show the JSON response in the Drive card's response area
      showResponse('response-drive', data);
      // Also add an entry to the HTTP log section
      addLogEntry('GET', 'https://www.googleapis.com/drive/v3/files', data);
      // Log to browser console for debugging
      console.log('Drive API response:', data);
    })
    // If any error occurs during the request, catch it and display it
    .catch(function (error) {
      // Show the error in the response area
      showResponse('response-drive', { error: error.message });
      // Log the error to the console
      console.error('Drive API error:', error);
    });
}


/* ================================================================
   GOOGLE CALENDAR API — Get Calendar Metadata
   ================================================================
   HTTP Method: GET
   Endpoint:    https://www.googleapis.com/calendar/v3/calendars/primary
   Purpose:     Retrieve metadata (name, timezone, etc.) of the user's
                primary calendar
   Response:    JSON object with "kind", "summary", "timeZone", "id"
   ================================================================ */

/**
 * getCalendarMetadata() — Calls the Google Calendar API to get
 * metadata about the user's primary (main) calendar.
 *
 * REST Details:
 * - Method: GET (reading data)
 * - URL: https://www.googleapis.com/calendar/v3/calendars/primary
 * - "primary" is a special keyword meaning the user's main calendar
 * - Header: Authorization: Bearer <access_token>
 */
function getCalendarMetadata() {
  // Check authentication first
  if (!checkAuth()) return;

  // The Calendar API endpoint — "primary" refers to the user's default calendar
  const url = 'https://www.googleapis.com/calendar/v3/calendars/primary';

  // Send HTTP GET request to the Calendar API
  fetch(url, {
    method: 'GET',  // GET because we're reading calendar info
    headers: {
      'Authorization': 'Bearer ' + accessToken, // Send our OAuth token
    },
  })
    // Parse the response body as JSON
    .then(function (response) {
      return response.json();
    })
    // Display the parsed data
    .then(function (data) {
      // Show response in the Calendar card
      showResponse('response-calendar', data);
      // Add to the HTTP log
      addLogEntry('GET', 'https://www.googleapis.com/calendar/v3/calendars/primary', data);
      console.log('Calendar API response:', data);
    })
    // Handle any errors
    .catch(function (error) {
      showResponse('response-calendar', { error: error.message });
      console.error('Calendar API error:', error);
    });
}


/* ================================================================
   GOOGLE SLIDES API — Create a Blank Presentation
   ================================================================
   HTTP Method: POST
   Endpoint:    https://slides.googleapis.com/v1/presentations
   Purpose:     Create a new blank Google Slides presentation
   Request Body: JSON with "title" field
   Response:    JSON with "presentationId", "title", "slides", etc.
   ================================================================ */

/**
 * createSlide() — Calls the Google Slides API to create a new presentation.
 *
 * REST Details:
 * - Method: POST (we are CREATING a new resource — a presentation)
 * - URL: https://slides.googleapis.com/v1/presentations
 * - Body: JSON object with a "title" for the new presentation
 * - Content-Type: application/json (tells the server the body is JSON)
 * - After creation, the presentation appears in Google Drive
 */
function createSlide() {
  // Check authentication first
  if (!checkAuth()) return;

  // The Slides API endpoint for creating presentations
  const url = 'https://slides.googleapis.com/v1/presentations';

  // Send HTTP POST request to create a new presentation
  fetch(url, {
    method: 'POST',  // POST because we are CREATING a new resource
    headers: {
      'Authorization': 'Bearer ' + accessToken,  // OAuth token for authorization
      'Content-Type': 'application/json',         // Tell server we're sending JSON
    },
    // The request body — a JSON string with the title for the new presentation
    // JSON.stringify() converts a JavaScript object to a JSON string
    body: JSON.stringify({
      title: 'Assignment 8A — Created via REST API',  // Title of the new presentation
    }),
  })
    .then(function (response) {
      return response.json(); // Parse response as JSON
    })
    .then(function (data) {
      // Show the response in the Slides card
      showResponse('response-slides', data);
      // Add to HTTP log
      addLogEntry('POST', 'https://slides.googleapis.com/v1/presentations', data);
      console.log('Slides API response:', data);

      // If the presentation was created successfully, show a link to open it
      if (data.presentationId) {
        // Construct the URL to open the presentation in Google Slides
        const slideUrl = 'https://docs.google.com/presentation/d/' + data.presentationId;
        // Alert the user with the link (they can also see it in Drive)
        alert('Success! Presentation created!\nOpen it: ' + slideUrl);
      }
    })
    .catch(function (error) {
      showResponse('response-slides', { error: error.message });
      console.error('Slides API error:', error);
    });
}


/* ================================================================
   GOOGLE SHEETS API — Create a New Spreadsheet
   ================================================================
   HTTP Method: POST
   Endpoint:    https://sheets.googleapis.com/v4/spreadsheets
   Purpose:     Create a new Google Sheets spreadsheet
   Request Body: JSON with "properties.title"
   Response:    JSON with "spreadsheetId", "spreadsheetUrl", "properties"
   ================================================================ */

/**
 * createSheet() — Calls the Google Sheets API to create a new spreadsheet.
 *
 * REST Details:
 * - Method: POST (creating a new spreadsheet)
 * - URL: https://sheets.googleapis.com/v4/spreadsheets
 * - Body: JSON with spreadsheet properties (title)
 * - The response includes a "spreadsheetUrl" that you can open in a browser
 */
function createSheet() {
  // Check authentication first
  if (!checkAuth()) return;

  // The Sheets API endpoint for creating spreadsheets
  const url = 'https://sheets.googleapis.com/v4/spreadsheets';

  // Send HTTP POST request
  fetch(url, {
    method: 'POST',  // POST = create new resource
    headers: {
      'Authorization': 'Bearer ' + accessToken,
      'Content-Type': 'application/json',
    },
    // Request body: specifies the title under "properties"
    body: JSON.stringify({
      properties: {
        title: 'Assignment 8A — Created via REST API',  // Name of the new spreadsheet
      },
    }),
  })
    .then(function (response) {
      return response.json();
    })
    .then(function (data) {
      // Display the response
      showResponse('response-sheets', data);
      addLogEntry('POST', 'https://sheets.googleapis.com/v4/spreadsheets', data);
      console.log('Sheets API response:', data);

      // If created successfully, show the spreadsheet URL
      if (data.spreadsheetUrl) {
        // data.spreadsheetUrl is the direct link to open the new spreadsheet
        alert('Success! Spreadsheet created!\nOpen it: ' + data.spreadsheetUrl);
      }
    })
    .catch(function (error) {
      showResponse('response-sheets', { error: error.message });
      console.error('Sheets API error:', error);
    });
}


/* ================================================================
   GOOGLE DOCS API — Create a New Document
   ================================================================
   HTTP Method: POST
   Endpoint:    https://docs.googleapis.com/v1/documents
   Purpose:     Create a new blank Google Docs document
   Request Body: JSON with "title" field
   Response:    JSON with "documentId", "title", etc.
   ================================================================ */

/**
 * createDoc() — Calls the Google Docs API to create a new document.
 *
 * REST Details:
 * - Method: POST (creating a new document)
 * - URL: https://docs.googleapis.com/v1/documents
 * - Body: JSON with the title for the new document
 * - After creation, the document appears in Google Drive
 */
function createDoc() {
  // Check authentication first
  if (!checkAuth()) return;

  // The Docs API endpoint for creating documents
  const url = 'https://docs.googleapis.com/v1/documents';

  // Send HTTP POST request
  fetch(url, {
    method: 'POST',  // POST = create new resource
    headers: {
      'Authorization': 'Bearer ' + accessToken,
      'Content-Type': 'application/json',
    },
    // Request body: JSON with the document title
    body: JSON.stringify({
      title: 'Assignment 8A — Created via REST API',  // Title of the new document
    }),
  })
    .then(function (response) {
      return response.json();
    })
    .then(function (data) {
      // Display response in the Docs card
      showResponse('response-docs', data);
      addLogEntry('POST', 'https://docs.googleapis.com/v1/documents', data);
      console.log('Docs API response:', data);

      // If created successfully, show a link to open the document
      if (data.documentId) {
        // Construct the Google Docs URL using the document ID
        const docUrl = 'https://docs.google.com/document/d/' + data.documentId;
        alert('Success! Document created!\nOpen it: ' + docUrl);
      }
    })
    .catch(function (error) {
      showResponse('response-docs', { error: error.message });
      console.error('Docs API error:', error);
    });
}


/* ================================================================
   END OF FILE
   ================================================================
   Summary of what this file does:
   1. Loads and initializes Google API client (gapi) and Google
      Identity Services (GIS) for OAuth 2.0 authentication.
   2. Provides handleSignIn() to open the Google sign-in popup
      and obtain an access token.
   3. Provides 5 API-calling functions:
      - listDriveFiles()      → GET  → Lists Google Drive files
      - getCalendarMetadata() → GET  → Gets primary calendar info
      - createSlide()         → POST → Creates a Google Slides presentation
      - createSheet()         → POST → Creates a Google Sheets spreadsheet
      - createDoc()           → POST → Creates a Google Docs document
   4. Each function uses the Fetch API to send HTTP requests with
      the OAuth access token in the Authorization header.
   5. Responses are displayed as formatted JSON on the page and
      logged in the HTTP Request/Response Log section.
   ================================================================ */

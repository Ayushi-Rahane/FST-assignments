# 🛒 Amazon Admin Panel — Assignment 3

A web-based Admin Panel for an Online Shopping Application. An administrator can select a customer from a dropdown and view their full details (shipping address, contact info, order history, etc.) — all fetched dynamically using **AJAX** from an **XML** file, without refreshing the page.

---

## 📁 Project Structure

```
assignment3/
│
├── index.html        → Main web page (HTML structure)
├── style.css         → All visual styling (CSS)
├── script.js         → Logic for fetching & displaying data (JavaScript + AJAX)
├── customers.xml     → Customer data storage (XML)
└── images/           → Customer profile photos
    ├── 1.png
    ├── 2.png
    ├── 3.png
    └── 6.png
```

---

## 🚀 How to Run

1. Make sure **Apache Tomcat** is running.
2. Deploy the `assignment3` folder inside `webapps/`.
3. Open your browser and go to:
   ```
   http://localhost:8080/assignment3/index.html
   ```
4. Select a customer from the dropdown to see their details appear!

---

## 🧠 Key Concepts (For Beginners)

### What is JavaScript (JS)?
JavaScript is the **"brain"** of a webpage. HTML builds the structure, CSS adds styling, and JS makes things **interactive** — like responding when you click a button or select from a dropdown.

### What is XML?
XML (eXtensible Markup Language) is a file format used to **store and organize data** using custom tags. Example:
```xml
<customer>
    <name>Chota Bheem</name>
    <email>bheem@dholakpur.com</email>
</customer>
```
It's like a digital filing cabinet — every piece of data has a label (tag) around it.

### What is AJAX?
AJAX = **Asynchronous JavaScript and XML**.
It's a technique where JavaScript **fetches data from a file or server in the background**, without reloading the whole page. Like a waiter taking your order and bringing food — the restaurant never closes while you wait.

---

## 📄 `script.js` — Line-by-Line Explanation

### The Function Declaration
```js
function fetchCustomerDetails() {
```
> Creates a reusable block of code called `fetchCustomerDetails`. This "recipe" runs every time the user picks a customer from the dropdown.

---

### Getting References to HTML Elements
```js
var select = document.getElementById("customer-select");
var selectedName = select.value;
var card = document.getElementById("customer-card");
var loader = document.getElementById("loader");
```
> `document.getElementById(...)` finds a specific element on the page by its `id` attribute.
- `select` → the dropdown menu
- `selectedName` → the name the user picked (e.g., "Chota Bheem")
- `card` → the customer details card
- `loader` → the spinning loading circle

---

### Remove the Animation Class
```js
card.classList.remove("active");
```
> Hides the card so it can re-appear with a smooth animation when new data loads.

---

### If Nothing is Selected, Stop
```js
if (!selectedName) {
    setTimeout(function () {
        card.style.display = "none";
    }, 500);
    return;
}
```
> If the user selects the blank default option, hide the card and exit the function. `return` means "stop here, don't do anything more."

---

### Show Loader, Hide Card
```js
loader.style.display = "block";
card.style.display = "none";
```
> Show the spinning loader and hide the card while data is being fetched — gives visual feedback to the user.

---

### The Delay (for smooth UX)
```js
setTimeout(function () {
    // ... AJAX code inside ...
}, 400);
```
> Wait 400 milliseconds before fetching. This small pause makes the loading animation look smooth and intentional.

---

### Create the AJAX Messenger
```js
var xhr = new XMLHttpRequest();
```
> `XMLHttpRequest` is the AJAX tool built into every browser. Think of it as creating a **messenger** who will go fetch our `customers.xml` file. `xhr` is just what we name this messenger.

---

### Tell the Messenger Where to Go
```js
xhr.open("GET", "customers.xml?t=" + new Date().getTime(), true);
```
> Tell the messenger: "Go GET the file `customers.xml`."
- `"GET"` → we're *reading* data, not sending anything
- `?t=" + new Date().getTime()` → adds a timestamp to force a fresh fetch (avoids browser cache)
- `true` → fetch in the background (asynchronous)

---

### Set Up the "On Return" Handler
```js
xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
```
> "When the messenger's status changes, run this code." AJAX goes through stages (1→2→3→4). Stage 4 = **completely finished**.

---

### Check if Successful
```js
if (xhr.status === 200 && xhr.responseXML) {
```
> Only continue if:
- `status === 200` → HTTP code for "Everything is OK!"
- `xhr.responseXML` → We actually got valid XML back

---

### Read the XML Data
```js
var xmlDoc = xhr.responseXML;
var customers = xmlDoc.getElementsByTagName("customer");
```
> Store the XML file in `xmlDoc`, then get a list of all `<customer>` entries inside it.

---

### Loop Through Customers to Find the Match
```js
for (var i = 0; i < customers.length; i++) {
    var nameNode = customers[i].getElementsByTagName("name")[0];
    if (nameNode && nameNode.textContent === selectedName) {
```
> Go through each customer one by one. When the customer's `<name>` matches what the user selected, stop and use that customer's data.

---

### Extract All Details
```js
var name = customer.getElementsByTagName("name")[0]?.textContent || "N/A";
var email = customer.getElementsByTagName("email")[0]?.textContent || "N/A";
// ... and so on for phone, address, city, etc.
```
> Pull out each piece of data from the matching customer's XML entry.
- `?.textContent` → get the text inside the tag
- `|| "N/A"` → if the data is missing, display "N/A" instead of crashing

---

### Display Data on the Page
```js
document.getElementById("detail-name").textContent = name;
document.getElementById("detail-email").textContent = email;
// ...
```
> Find each display element on the page by its `id` and fill it with the fetched data. This is how the card magically populates!

---

### Style the Membership Badge
```js
if (membership.toLowerCase().includes("prime")) {
    memBadge.classList.add("prime");
} else {
    memBadge.classList.remove("prime");
}
```
> If the membership level contains "prime", apply a green badge. Otherwise, use the default orange. CSS handles the color; JS just adds/removes the class.

---

### Show Customer Image
```js
if (image) {
    imageEl.src = image;
} else {
    imageEl.src = "data:image/svg+xml;..."; // fallback silhouette
}
```
> If the XML has an image path, show it. Otherwise, show a generic person silhouette icon as a fallback.

---

### Trigger the Fade-in Animation
```js
setTimeout(function () {
    card.classList.add("active");
}, 50);
```
> After a tiny 50ms pause (to let the browser process the display change), add the `active` CSS class — which triggers the smooth fade-in animation defined in `style.css`.

---

### Send the Request
```js
xhr.send();
```
> The messenger actually **departs** to fetch the XML file. Everything above just set up the instructions; this line fires it off.

---

## ❓ Viva Q&A Prep

| Question | Answer |
|----------|--------|
| **What is AJAX?** | Asynchronous JavaScript and XML — fetches data from a server in the background without reloading the page. |
| **What is `XMLHttpRequest`?** | A built-in JS object used to send/receive data from a server. It's the core tool of AJAX. |
| **What does `readyState === 4` mean?** | The server has completed the response and data is fully received. |
| **What does `status === 200` mean?** | HTTP status 200 = "OK" — the request was successful. |
| **Why do we use XML?** | XML stores data in a structured, tag-based format that is easy to parse using `getElementsByTagName`. |
| **What is `getElementsByTagName`?** | A method that finds all elements with a given tag name and returns them as a list. |
| **What is `textContent`?** | Gets the text written inside an opening and closing tag, e.g., `<name>Chota Bheem</name>` returns `"Chota Bheem"`. |
| **Why use `setTimeout`?** | To introduce a small delay — either for a loading animation effect or to let the browser process changes before animating. |
| **What does `classList.add/remove` do?** | Dynamically adds or removes a CSS class from an HTML element, changing its appearance. |
| **What happens if no customer is selected?** | The function returns early with `return;` and hides the card — nothing is fetched. |
| **What is the difference between synchronous and asynchronous?** | Synchronous = one thing at a time, blocks the page. Asynchronous = happens in the background, page stays responsive. |
| **What does `GET` mean in `xhr.open("GET", ...)`?** | It means we are *reading/requesting* data from the server, not submitting data. |

---

## 🎨 Features

- ✅ Dynamic customer details via AJAX (no page refresh!)
- ✅ Customer profile photos
- ✅ Email, phone, shipping address, zipcode display
- ✅ Membership badge (Prime = green, Regular = orange)
- ✅ Total orders & last order date stats
- ✅ Smooth fade-in card animations
- ✅ Loading spinner for feedback
- ✅ Glassmorphism dark-themed design
- ✅ Google Fonts (Inter) for premium typography

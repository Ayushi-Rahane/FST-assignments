function validateName(inputId, errorId) {
    const name = document.getElementById(inputId).value;
    const error = document.getElementById(errorId);

    if (!/^[A-Za-z ]*$/.test(name)) {
        error.textContent = "Only alphabets allowed.";
    } else {
        error.textContent = "";
    }
}

function validateformid() {
    const formid = document.getElementById("formid");
    if (formid.value.length != 6) {
        window.alert("Enter 6 digit Form ID.")
        formid.value = "";


    }
}
var student_branch;

function validateUCN() {
    const selectedBranch = document.getElementById("selectBranchID");
    ucnid = document.getElementById("ucnid");
    if (ucnid.value.length != 10) {
        alert("Invalid UCN! Enter 10 Digit properly.");
        ucnid.value = "";
    }
    if (selectedBranch.value === "COMP" && !(/^UCE/.test(ucnid.value)) || (selectedBranch.value === "ENTC" && !(/^UEN/.test(ucnid.value))) || (selectedBranch.value === "MECH" && !(/^UME/.test(ucnid.value))) || (selectedBranch.value === "INSTRU" && !(/^UIN/.test(ucnid.value)))) {
        alert("Invalid UCN! Enter UCN according to selected branch.");
        ucnid.value = "";
    }
}

function validatephone() {
    const phone = document.getElementById("phone");
    const error = document.getElementById("phoneError");
    if (!(/^[6-9]\d{9}$/.test(phone.value))) {
        error.textContent = "Invalid Phone Number! Enter 10 Digit properly.";
        phone.value = "";
    }
    else {
        error.textContent = "";
    }
}

function validateEmail() {
    const email = document.getElementById("email").value;
    const error = document.getElementById("emailError");
    if (!(/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email))) {
        error.textContent = "Invalid Email! Enter properly.";
        email.value = "";
    }
    else {
        error.textContent = "";
    }
}

function showUCNHint() {
    const selectedBranch = document.getElementById("selectBranchID").value;
    const hint = document.getElementById("ucnid");
    if (selectedBranch === "COMP") {
        hint.textContent = "UCN starts with UCE";
    } else if (selectedBranch === "ENTC") {
        hint.textContent = "UCN starts with UEN";
    } else if (selectedBranch === "MECH") {
        hint.textContent = "UCN starts with UME";
    } else if (selectedBranch === "INSTRU") {
        hint.textContent = "UCN starts with UIN";
    }
}


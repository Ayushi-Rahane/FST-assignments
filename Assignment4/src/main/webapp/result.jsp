<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.model.Student_data_container"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Details</title>

<!-- Google Font -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: 'Poppins', sans-serif;
    }

    body {
        height: 100vh;
        background: linear-gradient(135deg, #667eea, #764ba2);
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .card {
        background: white;
        padding: 40px;
        border-radius: 15px;
        width: 350px;
        text-align: center;
        box-shadow: 0 10px 30px rgba(0,0,0,0.3);
    }

    .icon {
        font-size: 50px;
        color: #667eea;
        margin-bottom: 10px;
    }

    h2 {
        margin-bottom: 20px;
        color: #333;
    }

    .info {
        text-align: left;
        margin-top: 20px;
    }

    .info p {
        margin: 10px 0;
        font-size: 15px;
        color: #444;
    }

    .info i {
        margin-right: 10px;
        color: #667eea;
    }

    .not-found {
        color: red;
        font-weight: 600;
        margin-top: 20px;
    }

    .btn {
        margin-top: 20px;
        display: inline-block;
        padding: 10px 20px;
        background: linear-gradient(135deg, #667eea, #764ba2);
        color: white;
        border-radius: 8px;
        text-decoration: none;
        transition: 0.3s;
    }

    .btn:hover {
        transform: scale(1.05);
        box-shadow: 0 5px 15px rgba(0,0,0,0.2);
    }
</style>

</head>
<body>

<div class="card">

    <div class="icon">
        <i class="fas fa-user-circle"></i>
    </div>

    <h2>Student Details</h2>

<%
Student_data_container s = (Student_data_container) request.getAttribute("student");

if(s != null){
%>

    <div class="info">
        <p><i class="fas fa-id-badge"></i><b>Roll No:</b> <%= s.getrollno() %></p>
        <p><i class="fas fa-user"></i><b>Name:</b> <%= s.getStud_name() %></p>
        <p><i class="fas fa-calendar"></i><b>Age:</b> <%= s.getAge() %></p>
    </div>

<%
} else {
%>

    <div class="not-found">
        <i class="fas fa-exclamation-circle"></i> No Student Found!
    </div>

<%
}
%>

    <a href="index.html" class="btn">
        <i class="fas fa-arrow-left"></i> Back
    </a>

</div>

</body>
</html>
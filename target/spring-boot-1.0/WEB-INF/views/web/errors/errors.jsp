<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container text-center">
    <h1 class="display-3 text-danger">Oops!</h1>
    <p class="lead">Something went wrong. Please try again later.</p>
    <p><strong>${status}</strong> - ${error}</p>
    <p>${message}</p>
    <a href="/" class="btn btn-primary">Go to Home</a>
</div>
</body>
</html>

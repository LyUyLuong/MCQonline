<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Course Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1>${course.name}</h1>
    <p>${course.description}</p>
    <h3>Price: ${course.price} VND</h3>
<%--    <p>Status: ${course.status ? "Available" : "Unavailable"}</p>--%>
    <button class="btn btn-primary" onclick="addToCart(${course.id})">Thêm vào giỏ hàng</button>
    <a href="/courses" class="btn btn-secondary">Back to Courses</a>
</div>
<script>
    function addToCart(courseId) {
        $.ajax({
            url: '/api/cart/add/' + courseId,
            type: 'POST',
            success: function (response) {
                // Display the success message from the server
                alert(response);
            },
            error: function (xhr) {
                if (xhr.responseText) {
                    // Display the error message if available
                    alert(xhr.responseText);
                } else {
                    // Fallback generic error message
                    alert('Có lỗi xảy ra!');
                }
                console.log(xhr); // Log the response for debugging
            }
        });
    }
</script>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>
</body>
</html>
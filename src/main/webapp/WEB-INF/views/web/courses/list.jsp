<%-- Created by IntelliJ IDEA. User: PC Date: 1/13/2025 Time: 7:44 PM To change this template use File | Settings | File Templates. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Danh sách khóa học</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1 class="my-4">Danh sách khóa học</h1>
    <div class="row">
        <c:forEach var="course" items="${courses}">
            <div class="col-md-4">
                <div class="card mb-4">
                    <c:if test="${not empty course.image}">
                        <c:set var="imagePath" value="${course.image}"/>
                        <img src="/repository${course.image}" id="viewImage" width="200px" height="150" style="margin-top: 50px">
                    </c:if>
                    <c:if test="${empty course.image}">
                        <img src="/repository/course/default.png" id="viewImage" width="200px" height="150" style="margin-top: 50px">
                    </c:if>
                    <div class="card-body">
                        <h5 class="card-title">${course.name}</h5>
                        <p class="card-text">${course.description}</p>
                        <p class="text-success">Giá: ${course.price} VND</p>
                        <a href="/courses/${course.id}" class="btn btn-info">Chi tiết</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>
</body>
</html>

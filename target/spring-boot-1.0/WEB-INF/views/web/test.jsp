<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.javaweb.security.utils.SecurityUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thư viện đề thi</title>
</head>
<body>
<!-- MENU  -->
<div class="page-wrapper mt-4 ml-4 mr-4 mb-5">
    <div class="content-header pb-0 gray-bg">
        <div class="container">
            <div class="row d-flex align-items-center">
                <div class="col-md-9 col-12">
                    <h1>thư viện đề thi</h1>
                    <br>
                    <div class="test-exams">
                        <ul class="nav nav-pills flex-wrap">
                            <li class="nav-item">
                                <a class="nav-link active" href="/test">Tất cả</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/test">About</a>
                            </li>
                        </ul>

                    </div>
                </div>
                <div class="col-md-3 col-12">
                    <security:authorize access="isAuthenticated()">
                        <h1>Phần User</h1>
                        <div class="user-info user-info">
                            <div class="user-name">
                                Xin chào, <%=SecurityUtils.getPrincipal().getUsername()%>
                            </div>
                            <hr>
                            <div class="mt-4">
                                <a class="btn btn-sm btn-block btn-round btn-outline-secondary" href="/test/analytics">
                                    <i class="fa-solid fa-chart-simple"></i>
                                    Thống kê kết quả
                                </a>
                            </div>
                        </div>
                    </security:authorize>

                </div>
            </div>
        </div>

    </div>
    <div class="content-wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12 col-md-9 mt-4">
                    <div class="row" id="testTable">
                        <c:forEach var="item" items="${tests}">
                            <!-- Card 1 -->
                            <div class="col-6 col-md-3 mb-3">
                                <div class="card">
                                    <div class="card-body">
                                        <a href="" class="text-decoration-none text-dark">
                                            <h5 class="card-title">${item.name}</h5>
                                            <p class="card-text">With supporting text below as a natural lead-in to
                                                additional
                                                content.</p>
                                        </a>
                                        <div class="mt-4">
                                            <a href="/test/${item.id}" class="btn btn-primary">Go somewhere</a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>

                    </div>
                </div>
                <div class="col-12 col-md-3">
                    <!-- Phần trống hoặc sidebar -->
                </div>
            </div>
        </div>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>

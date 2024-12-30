<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ</title>
	<link rel="icon" type="image/x-icon" href="/img/study4_new_logo_sm.webp">


	<!-- Bootstrap core CSS -->
	<link href="${pageContext.request.contextPath}/web/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="${pageContext.request.contextPath}/web/css/small-business.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/web/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<!-- Navigation -->
	<%@ include file="/common/web/header.jsp" %>

	<dec:body/>

	<!-- Footer -->
	<%@ include file="/common/web/footer.jsp" %>

	<!-- Bootstrap core JavaScript -->
	<script src="${pageContext.request.contextPath}/web/vendor/jquery/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/web/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/web/vendor/jquery/jquery.min.js"></script>
</body>
</html>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ page import="com.javaweb.security.utils.SecurityUtils" %>
	<nav class="navbar navbar-expand-lg bg-body-tertiary">
		<div class="container-fluid">
			<span class="navbar-brand">
				<a class="topnav-brand" href="/">
					<img class="favicon" src="/img/study4_new_logo_sm.webp" alt="Bootstrap">
				</a>
			</span>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
					<li class="nav-item">
						<a class="nav-link" href="/test">
							Đề thi online
						</a>
					</li>
				</ul>
				<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
					<li class="nav-item">
						<a class="nav-link" href="/courses">
							Các khóa học
						</a>
					</li>
				</ul>
				<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
					<li class="nav-item">
						<a class="nav-link" href="/cart">
							Giỏ hàng
						</a>
					</li>
				</ul>
				<ul class="navbar-nav">
					<security:authorize access="isAnonymous()">
						<li class="nav-item"><a class="nav-link" href="<c:url value='/login'/>">Đăng nhập</a></li>
					</security:authorize>

					<security:authorize access="isAuthenticated()">
						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown">Xin chào, <%=SecurityUtils.getPrincipal().getUsername()%>
							</a>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item" href="#">Action</a></li>
								<li><a class="dropdown-item" href="#">Another action</a></li>
								<li>
									<hr class="dropdown-divider">
								</li>
								<li class="nav-item"><a class="nav-link" href="<c:url value='/logout'/>">Thoát</a></li>
							</ul>
						</li>
					</security:authorize>
				</ul>
			</div>
		</div>
	</nav>
<%--	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>--%>
<%--	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>--%>


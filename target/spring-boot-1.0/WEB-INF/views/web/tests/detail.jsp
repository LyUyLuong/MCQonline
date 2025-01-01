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
    <div class="content-wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12 col-md-9 mt-4">
                    <div class="container mt-5">
                        <div class="card shadow">
                            <div class="card-body">
                                <h1 class="card-title">${test.name}</h1>
                                <div class="mb-3 text-muted">
                                    <p class="mb-1">⏱ Thời gian làm bài: 120 phút | 7 phần thi | 200 câu hỏi | 304 bình
                                        luận</p>
                                    <p>👥 513,492 người đã luyện tập đề thi này</p>
                                    <p>${test.description}</p>
                                </div>
                                <div class="alert alert-danger" role="alert">
                                    Chú ý: để được quy đổi sang scaled score (ví dụ trên thang điểm 990 cho TOEIC hoặc
                                    9.0 cho IELTS),
                                    vui lòng chọn chế độ làm FULL TEST.
                                </div>
                                <ul class="nav nav-tabs mb-3" id="myTab" role="tablist">
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link active" id="practice-tab" data-bs-toggle="tab"
                                                data-bs-target="#practice" type="button" role="tab"
                                                aria-controls="practice" aria-selected="true">Luyện tập
                                        </button>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link" id="fulltest-tab" data-bs-toggle="tab"
                                                data-bs-target="#fulltest" type="button" role="tab"
                                                aria-controls="fulltest" aria-selected="false">Làm full test
                                        </button>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link" id="discussion-tab" data-bs-toggle="tab"
                                                data-bs-target="#discussion" type="button" role="tab"
                                                aria-controls="discussion" aria-selected="false">Thảo luận
                                        </button>
                                    </li>
                                </ul>
                                <div class="alert alert-warning" role="alert">
                                    📢 Sẵn sàng để bắt đầu làm full test? Để đạt được kết quả tốt nhất, bạn cần dành ra
                                    120 phút cho bài
                                    test này.
                                </div>
                                <div class="tab-content" id="myTabContent">
                                    <div class="tab-pane fade show active" id="practice" role="tabpanel"
                                         aria-labelledby="practice-tab">
                                        <div class="row">
                                            <form id="formPractice" action="/test/${test.id}/practice" method="GET">
                                                <c:forEach items="${partTestEntities}" var="part">
                                                    <div class="col-12">
                                                        <div class="form-check mb-2">
                                                            <input class="form-check-input" type="checkbox"
                                                                   id="part-${part.id}" value="${part.id}" name="part">
                                                            <label class="form-check-label" for="part-${part.id}">
                                                                    ${part.partName}
                                                            </label>
                                                        </div>
                                                    </div>
                                                </c:forEach>

                                                <button id="practiceButton" type="button" class="btn btn-primary">Bắt
                                                    đầu thi
                                                </button>
                                            </form>


                                        </div>


                                    </div>
                                    <div class="tab-pane fade" id="fulltest" role="tabpanel"
                                         aria-labelledby="fulltest-tab">
                                        <p>Nội dung phần làm full test.</p>
                                        <a href="/test/${test.id}/start">
                                            <button class="btn btn-primary">Bắt đầu thi</button>
                                        </a>

                                    </div>
                                    <div class="tab-pane fade" id="discussion" role="tabpanel"
                                         aria-labelledby="discussion-tab">
                                        <p>Nội dung phần thảo luận.</p>
                                    </div>
                                </div>


                            </div>
                        </div>
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
                                <a class="btn btn-sm btn-block btn-round btn-outline-secondary" href="/test">
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

</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>
<script>


    const practiceButton = document.getElementById('practiceButton');
    practiceButton.addEventListener('click', () => {

        const checkboxes = document.querySelectorAll('.form-check-input');
        const selectedParts = [];

        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                selectedParts.push(checkbox.id);
            }
        });

        if (selectedParts.length > 0) {
            // Convert selected parts list to a comma-separated string

            $(`#formPractice`).submit();
        } else {
            alert('Bạn chưa chọn phần nào!');
        }
    });


</script>
</body>
</html>
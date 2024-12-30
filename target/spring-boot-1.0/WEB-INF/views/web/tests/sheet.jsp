<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.javaweb.security.utils.SecurityUtils" %>
<%@ page import="com.javaweb.security.utils.SecurityUtils" %>
<html>
<head>
    <title>Test with Timer</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1>Test: ${test.name}</h1>
    <p>${test.description}</p>
    <hr>

    <!-- Timer -->
    <div class="alert alert-info text-center" role="alert" id="timer-container">
        <strong>Thời gian còn lại:</strong> <span id="timer">00:00</span>
    </div>

    <div class="row">
        <div class="col-md-9">
            <!-- Navigation Tabs -->
            <ul class="nav nav-pills nav-horizontal mb-4" id="pills-tab" role="tablist">
                <c:forEach items="${partTestEntities}" var="part" varStatus="status">
                    <li class="nav-item" role="presentation">
                        <a class="nav-link ${status.first ? 'active' : ''}"
                           id="pills-${part.id}-tab"
                           data-bs-toggle="pill"
                           href="#partcontent-${part.id}"
                           role="tab"
                           aria-controls="pills-${part.id}"
                           aria-selected="${status.first}">
                            ${part.partName}
                        </a>
                    </li>
                </c:forEach>
            </ul>

            <!-- Tab Content -->
            <form:form id="answerForm">
                <div class="tab-content" id="pills-tabContent">
                    <!-- Global question counter -->
                    <c:set var="globalQuestionCounter" value="0" scope="page"/>

                    <c:forEach items="${partTestEntities}" var="part" varStatus="status">
                        <div class="tab-pane fade ${status.first ? 'show active' : ''}"
                             id="partcontent-${part.id}"
                             role="tabpanel"
                             aria-labelledby="pills-${part.id}-tab">
                            <h2>${part.partName}</h2>
                            <p>Type: ${part.partType}</p>
                            <c:forEach items="${questionTestEntities[part]}" var="question" varStatus="qStatus">
                                <!-- Increment global counter -->
                                <c:set var="globalQuestionCounter" value="${globalQuestionCounter + 1}"/>

                                <div class="question-wrapper" data-qid="${question.id}"
                                     id="question-wrapper-${question.id}">
                                    <div class="question-number">
                                        <!-- Use global counter -->
                                        <strong>Câu hỏi ${globalQuestionCounter}</strong>
                                    </div>

                                    <div class="question-content">
                                        <p>${question.content}</p>
                                        <div class="question-answers">
                                            <c:forEach items="${question.answerEntityList}" var="answer">
                                                <div class="form-check">
                                                    <input class="form-check-input"
                                                           type="radio"
                                                           name="qid-${question.id}"
                                                           id="question-${question.id}-${answer.id}"
                                                           value="${answer.id}">
                                                    <label class="form-check-label"
                                                           for="question-${question.id}-${answer.id}">
                                                            ${answer.content}
                                                    </label>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:forEach>

                </div>
            </form:form>
        </div>

        <div class="col-md-3">
            <div class="container mt-5">
                <button type="submit" id="submitAnswerForm" class="btn btn-primary btn-block">Submit</button>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>

<script>
    // Thời gian làm bài (ví dụ: 20 phút)
    var totalTimeInSeconds = 20 * 60; // Thời gian 20 phút

    function startTimer(duration, display) {
        var timer = duration, minutes, seconds;

        var countdown = setInterval(function () {
            minutes = parseInt(timer / 60, 10);
            seconds = parseInt(timer % 60, 10);

            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            display.textContent = minutes + ":" + seconds;

            if (--timer < 0) {
                clearInterval(countdown);
                alert("Hết thời gian! Bài làm sẽ được nộp tự động.");
                $("#submitAnswerForm").click(); // Tự động submit form khi hết giờ
            }
        }, 1000);
    }

    // Bắt đầu khi tải trang
    window.onload = function () {
        var display = document.querySelector('#timer');
        startTimer(totalTimeInSeconds, display);
    };

    $("#submitAnswerForm").click(function (e) {
        e.preventDefault();

        var formData = document.getElementById("answerForm");
        var formElements = formData.elements;
        var userAnswers = []; // Array để chứa danh sách các câu trả lời

        // Lấy các giá trị từ form
        for (var i = 0; i < formElements.length; i++) {
            var element = formElements[i];
            if (element.type === "radio" && element.checked) {
                userAnswers.push({
                    questionId: parseInt(element.name.replace("qid-", "")), // Lấy ID câu hỏi
                    answerId: parseInt(element.value) // Lấy ID câu trả lời
                });
            }
        }

        // Kiểm tra kết quả
        console.log("Payload gửi đi:", JSON.stringify(userAnswers));

        // Gửi danh sách JSON tới server
        var apiUrl = `/api/result/${test.id}`;
        $.ajax({
            url: apiUrl,
            type: "POST",
            data: JSON.stringify(userAnswers), // Gửi payload dưới dạng danh sách
            contentType: "application/json",
            success: function (response) {
                console.log("Success:", response);
            },
            error: function (error) {
                console.log("Error:", error.responseJSON);
                alert("Error: " + error.responseJSON.message);
            }
        });
    });
</script>
</body>
</html>

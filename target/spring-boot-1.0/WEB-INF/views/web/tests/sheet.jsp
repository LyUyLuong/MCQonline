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
            <form:form id="answerForm" method="post">
                <div class="tab-content" id="pills-tabContent">
                    <input type="hidden" id="completeTime" name="completeTime" value="0">
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

    var totalTimeInSeconds =${timeLimit} *60;
    var completeTime = 0;

    function startTimer(duration, display) {
        var timer = 0, minutes, seconds;

        var countdown = setInterval(function () {
            completeTime = timer;
            minutes = parseInt(timer / 60, 10);
            seconds = parseInt(timer % 60, 10);

            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            display.textContent = minutes + ":" + seconds;

            if (++timer > duration) { // Nếu vượt quá thời gian đặt trước
                clearInterval(countdown);
                alert("Hết thời gian! Bài làm sẽ được nộp tự động.");
                submitFormWithElapsedTime(timer);
            }
        }, 1000);
    }

    function submitFormWithcompleteTime(actualcompleteTime) {
        const hiddenInput = document.getElementById('completeTime');
        hiddenInput.value = actualcompleteTime; // Cập nhật thời gian thực tế đã làm

        $("#submitAnswerForm").submit(); // Tự động submit form
    }

    // Bắt đầu khi tải trang
    window.onload = function () {
        var display = document.querySelector('#timer');
        startTimer(totalTimeInSeconds, display);
    };

    // Biến để kiểm tra trạng thái form
    let isFormSubmitted = false;

    // Gắn sự kiện trước khi rời trang
    window.addEventListener('beforeunload', function (e) {
        if (!isFormSubmitted) {
            const confirmationMessage = "Bạn có chắc chắn muốn rời trang không? Những thay đổi của bạn sẽ không được lưu.";
            e.preventDefault(); // Cần thiết cho trình duyệt cũ
            e.returnValue = confirmationMessage; // Hiển thị thông báo
            return confirmationMessage; // Hiển thị thông báo
        }
    });

    // Khi form được submit, ngăn không hiển thị xác nhận nữa
    $('#submitAnswerForm').on('submit', function () {
        isFormSubmitted = true;
    });


    // Lấy các tham số từ URL hiện tại
    var params = new URLSearchParams(window.location.search);
    var partValues = params.getAll("part"); // Lấy tất cả các giá trị của 'part'

    // Nối các giá trị 'part' vào URL của action
    var partQuery = partValues && partValues.length > 0
        ? partValues.map(part => `part=` + part).join("&")
        : "";

    $("#submitAnswerForm").click(function (e) {
        e.preventDefault();

        var formData = document.getElementById("answerForm");
        var formElements = formData.elements;

        var userAnswers = []; // Array để chứa danh sách các câu trả lời

        for (var i = 0; i < formElements.length; i++) {
            var element = formElements[i];
            if (element.type === "radio" && element.checked) {
                userAnswers.push({
                    questionId: parseInt(element.name.replace("qid-", "")), // Lấy ID câu hỏi
                    answerId: parseInt(element.value) // Lấy ID câu trả lời
                });
            }
        }

        const form = {
            userAnswerRawList: userAnswers,
            completeTime: completeTime
        };



        var typeOfTest = "${typeOfTest}";
        var api = typeOfTest === 'FULL'
            ? `/api/result/${test.id}`
            : `/api/result/${test.id}?` + partQuery;



        // console.log(apiPartsTestUrl)
        $.ajax({
            url: api,
            type: "POST",
            data: JSON.stringify(form), // Gửi payload dưới dạng danh sách
            contentType: "application/json",
            success: function (response) {
                window.location.href = response;
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

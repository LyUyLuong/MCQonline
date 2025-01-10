<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test Result</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/web/css/style.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container mt-5">
    <h1>Test Result</h1>

    <!-- Test Information -->
    <div class="card mb-4">
        <div class="card-body">
            <h3>${test.name}</h3>
            <p>${test.description}</p>
            <p><strong>User:</strong> ${result.userEntity.fullName}</p>
            <p><strong>Thời gian hoàn thành: </strong> ${result.completeTime}</p>
            <p><strong>Listening: </strong> ${result.listeningPoint}/495</p>
            <p><strong>Reading: </strong> ${result.readingPoint}/495</p>
        </div>
    </div>

    <!-- Parts and Questions -->
    <div>
        <c:set var="globalQuestionCounter" value="0" scope="page"/>
        <c:forEach items="${partTestEntities}" var="part">
            <div class="card mb-4">
                <div class="card-header">
                    <h5>${part.partName}</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <c:forEach items="${part.questions}" var="question" varStatus="status">
                            <div class="col-md-6 mb-3 d-flex align-items-center">

                                <c:set var="globalQuestionCounter" value="${globalQuestionCounter + 1}"/>

                                <div class="flex-grow-1">

                                    <c:set var="correctAnswerMark" value=""/>
                                    <c:set var="userAnswerMark" value=""/>
                                    <c:set var="isCorrect" value="false"/>


                                    <c:forEach items="${question.answerEntityList}" var="answer">
                                        <c:if test="${answer.correct}">
                                            <c:set var="correctAnswerMark" value="${answer.mark}"/>
                                        </c:if>
                                        <c:if test="${userAnswerList.contains(answer)}">
                                            <c:set var="userAnswerMark" value="${answer.mark}"/>
                                            <c:set var="isCorrect" value="${answer.correct}"/>
                                        </c:if>
                                    </c:forEach>

                                    <!-- Question number with conditional styling -->

                                    <!-- Display result -->
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div class="d-flex align-items-center ">
                                            <div class="rounded-circle text-white text-center mr-4"
                                                 style="background:${isCorrect ? '#198754': '#a51f1c' }; margin-right:10px; width: 35px; height: 35px; line-height: 35px; font-size: 15px;">
                                                    ${globalQuestionCounter}
                                            </div>

                                            <div class="ml-3"> <!-- Tạo khoảng cách -->
                                                <c:choose>
                                                    <c:when test="${not empty userAnswerMark}">
                                                        <strong>${correctAnswerMark} : ${userAnswerMark}</strong>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <strong>${correctAnswerMark} : Không lựa chọn</strong>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>


                                            <div>
                                                <svg xmlns="http://www.w3.org/2000/svg"
                                                     width="25"
                                                     height="25"
                                                     fill="${isCorrect ? 'green' : 'red'}"
                                                     class="bi ${isCorrect ? 'bi-check2' : 'bi-x'} ms-2"
                                                     viewBox="0 0 16 16"
                                                     aria-label="${isCorrect ? 'Correct' : 'Incorrect'}">
                                                    <path d="${isCorrect ?
                    'M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0' :
                    'M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708'}"/>
                                                </svg>
                                            </div>
                                            <button class="btn btn-sm btn-link p-0 text-decoration-none"
                                                    onclick="loadQuestionDetail(${question.id},${result.id})">Chi tiết
                                            </button>


                                        </div>
                                    </div>
                                </div>

                            </div>
                        </c:forEach>

                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="modal fade" id="detailQuestionModal" tabindex="-1" aria-labelledby="detailQuestionModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="detailQuestionModalLabel">Chi tiết câu hỏi</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="question-context"></div>
                    <div id="question-answers"></div>
                    <div id="question-answers-details"></div>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>
<script>
    const loadQuestionDetail = (questionId, resultId) => {
        $.ajax({
            url: '/api/questions/' + questionId + '/result/' + resultId,
            type: "GET",
            success: function (response) {
                console.log(response);

                // Hiển thị nội dung câu hỏi
                const questionContext = '<p>' + response.questionTestDTO.content + '</p>';
                document.getElementById('question-context').innerHTML = questionContext;

                // Hiển thị danh sách câu trả lời
                let answersHtml = '';
                response.answers.forEach(function (answer) {
                    let answerStyle = '';
                    if (response.userAnswer && response.userAnswer.content === answer.content) {
                        answerStyle = 'font-weight: bold; color:black; background: red;';
                    } else if (answer.correct) {
                        answerStyle = 'font-weight: bold; color:black; background: green;';
                    }

                    const isChecked = (response.userAnswer && response.userAnswer.content === answer.content) ? 'checked' : '';

                    answersHtml += '<div class="form-check">' +
                        '<input class="form-check-input" type="radio" disabled ' + isChecked + ' style="' + answerStyle + '">' +
                        '<label class="form-check-label" style="' + answerStyle + '">' +
                        answer.content +
                        '</label>' +
                        '</div>';
                });
                document.getElementById('question-answers').innerHTML = answersHtml;

                // Hiển thị chi tiết khác
                let correctAnswer = "Không có";
                response.answers.forEach(function (answer) {
                    if (answer.correct) {
                        correctAnswer = answer.content;
                    }
                });

                let userAnswer = "Không lựa chọn";
                if (response.userAnswer && response.userAnswer.content) {
                    userAnswer = response.userAnswer.content;
                }

                const detailsHtml =
                    '<p><strong>Đáp án đúng:</strong> ' + correctAnswer + '</p>' +
                    '<p><strong>Câu trả lời của bạn:</strong> ' + userAnswer + '</p>';
                document.getElementById('question-answers-details').innerHTML = detailsHtml;

                // Hiển thị modal
                const modal = new bootstrap.Modal(document.getElementById('detailQuestionModal'));
                modal.show();
            },
            error: function () {
                alert("Không thể tải thông tin câu hỏi. Vui lòng thử lại!");
            }
        });
    };
</script>



</body>
</html>

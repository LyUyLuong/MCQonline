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
            <p><strong>Score:</strong> ${result.score}</p>
            <p><strong>Completed On:</strong> ${result.completeTine}</p>
        </div>
    </div>

    <!-- Parts and Questions -->
    <div>
        <c:set var="globalQuestionCounter" value="0" scope="page"/>
        <c:forEach items="${partTestEntities}" var="part">
            <div class="card mb-4">
                <div class="card-header">
                    <h5>Part: ${part.partName} (${part.partType})</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <c:forEach items="${questionTestEntities[part]}" var="question" varStatus="status">
                            <div class="col-md-6 mb-3 d-flex align-items-center">
                                <!-- Increment global question counter -->
                                <c:set var="globalQuestionCounter" value="${globalQuestionCounter + 1}"/>

                                <div class="flex-grow-1">
                                    <!-- Initialize variables -->
                                    <c:set var="correctAnswerMark" value=""/>
                                    <c:set var="userAnswerMark" value=""/>
                                    <c:set var="isCorrect" value="false"/>

                                    <!-- Iterate through answerEntityList -->
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
                                            <a>[Chi tiết]</a>

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


</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>

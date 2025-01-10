<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.javaweb.security.utils.SecurityUtils" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thống kê kết quả</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

</head>
<body>
<div class="container mt-4">
    <div class="row">
        <!-- Phần 9 -->
        <div class="col-md-9">
            <!-- Phần tiêu đề -->
            <div class="mb-4">
                <h1 class="h4 text-primary">📊 Thống kê kết quả luyện thi</h1>
                <p class="text-danger">Chú ý: Mặc định trang thống kê sẽ hiển thị các bài làm trong khoảng thời gian 30
                    ngày gần nhất. Để xem kết quả trong khoảng thời gian xa hơn, bạn chọn ở phần dropdown dưới đây.</p>
            </div>

            <!-- Lọc kết quả -->
            <div class="d-flex align-items-center mb-4">
                <div class="mr-2">
                    <select class="form-control">
                        <option>30 ngày</option>
                        <option>7 ngày</option>
                        <option>1 ngày</option>
                    </select>
                </div>
                <button class="btn btn-primary mr-2">Search</button>
                <button class="btn btn-secondary">Clear</button>
            </div>

            <!-- Thống kê tổng quan -->
            <div class="row mb-4">
                <div class="col-md-4">
                    <div class="border rounded p-3 text-center">
                        <h5>Số đề đã làm</h5>
                        <p><strong>${resultEntities.size()}</strong> đề thi</p>
                    </div>
                </div>
                <c:set var="totalHours" value="0"/>
                <c:set var="totalMinutes" value="0"/>
                <c:set var="totalSeconds" value="0"/>


                <c:forEach var="result" items="${resultEntities}">
                    <c:set var="totalHours" value="${totalHours + result.completeTime.hours}"/>
                    <c:set var="totalMinutes" value="${totalMinutes + result.completeTime.minutes}"/>
                    <c:set var="totalSeconds" value="${totalSeconds + result.completeTime.seconds}"/>
                </c:forEach>

                <div class="col-md-4">
                    <div class="border rounded p-3 text-center">
                        <h5>Tổng thời gian luyện thi</h5>
                        <p><strong>${totalHours} : ${totalMinutes} : ${totalSeconds}</strong> phút</p>
                    </div>
                </div>


                <div class="col-md-4">
                    <div class="border rounded p-3 text-center">
                        <h5>Điểm mục tiêu</h5>
                        <p><a href="#" class="text-primary">Tạo ngay</a></p>
                    </div>
                </div>
            </div>

            <!-- Tabs -->
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="listening-tab" data-toggle="tab" href="#listening" role="tab"
                       aria-controls="listening" aria-selected="true">Listening</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="reading-tab" data-toggle="tab" href="#reading" role="tab"
                       aria-controls="reading" aria-selected="false">Reading</a>
                </li>
            </ul>
            <div class="tab-content mt-3" id="myTabContent">
                <!-- Listening -->
                <div class="tab-pane fade show active" id="listening" role="tabpanel" aria-labelledby="listening-tab">
                    <div class="row mb-4">
                        <div class="col-md-2">
                            <div class="border rounded p-3 text-center">
                                <h6>Số đề đã làm</h6>
                                <p><strong>1</strong> đề thi</p>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="border rounded p-3 text-center">
                                <h6>Độ chính xác</h6>
                                <p>94.52%</p>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="border rounded p-3 text-center">
                                <h6>Thời gian trung bình</h6>
                                <p>0:35:35</p>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="border rounded p-3 text-center">
                                <h6>Điểm trung bình</h6>
                                <p>355/495</p>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="border rounded p-3 text-center">
                                <h6>Điểm cao nhất</h6>
                                <p>355/495</p>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Reading -->
                <div class="tab-pane fade" id="reading" role="tabpanel" aria-labelledby="reading-tab">
                    <p>Nội dung tab Reading (tuỳ chỉnh nếu cần).</p>
                </div>
            </div>

            <!-- Danh sách đề thi -->
            <h3 class="mt-5">Danh sách đề thi đã làm:</h3>
            <table class="table table-striped table-hover table-bordered align-middle">
                <thead class="thead-light">
                <tr>
                    <th>Ngày làm</th>
                    <th>Đề thi</th>
                    <th>Kết quả</th>
                    <th>Thời gian làm bài</th>
                    <th>Chi tiết</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="result" items="${resultEntities}">
                    <tr>
                        <td>${result.createdDate}</td>
                        <c:if test="${result.type == 'Parts_Test'}">
                            <td>
                                    ${result.testEntity.name}<br>
                                <span class="result-badge-practice">${result.type}</span>
                                <c:forEach var="part" items="${result.resultHaveParts}">
                                    <p class="result-badge-practice">${part.partTest.partName}</p>
                                </c:forEach>
                            </td>
                            <td>${result.listeningCorrectAnswer + result.readingCorrectAnswer}/${totalQuestionResult[result.id]}</td>
                        </c:if>
                        <c:if test="${result.type == 'Full_Test'}">
                            <td>
                                    ${result.testEntity.name}<br>
                                <span class="result-badge-full">${result.type}</span>

                            </td>
                            <td>${result.listeningCorrectAnswer + result.readingCorrectAnswer}/${totalQuestionResult[result.id]}
                                (Điểm: ${totalPointResult[result.id]})
                            </td>
                        </c:if>
                        <td>${result.completeTime}</td>
                        <td>
                            <a href="/test/${result.testEntity.id}/result/${result.id}"
                               class="btn btn-outline-primary btn-sm">
                                Xem chi tiết
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="text-end">
                <a href="#" class="text-primary">Xem tất cả &gt;&gt;</a>
            </div>

        </div>

        <!-- Phần 3 -->
        <div class="col-md-3">
            <div class="border rounded p-3">
                <h5 class="text-primary">Mẹo làm bài</h5>
                <ul>
                    <li>Đọc câu hỏi trước khi nghe.</li>
                    <li>Tập trung vào từ khóa trong câu hỏi.</li>
                    <li>Loại bỏ các đáp án sai.</li>
                </ul>
            </div>
            <div class="border rounded p-3 mt-3">
                <h5 class="text-primary">Quảng cáo</h5>
                <p>Tham gia khóa học TOEIC online để cải thiện điểm số.</p>
                <a href="#" class="btn btn-success btn-sm">Tham gia ngay</a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>
</body>
</html>

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thông tin Đề Thi</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Home</a>
                </li>
                <li class="active">Quản lý Đề Thi</li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">

            <div class="page-header">
                <h1>
                    ${test.id == null ? "Tạo mới Đề Thi" : "Chỉnh sửa Đề Thi"}
                </h1>
            </div><!-- /.page-header -->
        </div><!-- /.page-content -->

        <div class="row">
            <div class="col-xs-12">
                <form:form id="form-edit" method="POST" modelAttribute="testEdit">
                    <input type="hidden" name="id" id="id" value="${testEdit.id}"/>

                    <div class="form-group">
                        <label for="name" class="col-xs-3">Tên Đề Thi</label>
                        <div class="col-xs-9">
                            <form:input path="name" class="form-control"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-xs-3">Mô Tả</label>
                        <div class="col-xs-9">
                            <form:textarea path="description" class="form-control" rows="4"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="status" class="col-xs-3">Trạng Thái</label>
                        <div class="col-xs-9">
                            <form:select path="status" class="form-control">
                                <form:option value="1" label="Hoạt động"/>
                                <form:option value="0" label="Ngừng hoạt động"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="numberOfParticipants" class="col-xs-3">Số Người Tham Gia</label>
                        <div class="col-xs-9">
                            <form:input path="numberOfParticipants" type="number" class="form-control"/>
                        </div>
                    </div>

                    <!-- Phần này dành cho PartTestEntity -->
                    <div class="form-group row">
                        <label for="partTestEntities" class="col-sm-3 col-form-label text-right">Phần của Đề Thi</label>
                        <div class="col-sm-9">
                            <c:choose>
                                <c:when test="${not empty partTests}">
                                    <div class="form-check">
                                        <form:checkboxes
                                                path="partTestEntities"
                                                items="${partTests}"
                                                itemLabel="partName"
                                                itemValue="id"
                                                class="form-check-input"
                                        />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-warning">
                                        Hiện chưa có phần nào. Vui lòng tạo phần mới.
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>


                    <%--          <div class="form-group">--%>
                    <%--            <label for="resultEntities" class="col-xs-3">Kết Quả</label>--%>
                    <%--            <div class="col-xs-9">--%>
                    <%--              <form:checkboxes path="resultEntities" items="${results}" itemLabel="id" itemValue="id" class="form-control"/>--%>
                    <%--            </div>--%>
                    <%--          </div>--%>

                    <div class="form-group">
                        <label class="col-xs-3"></label>
                        <div class="col-xs-9">
                            <c:if test="${not empty testEdit.id}">
                                <button type="button" class="btn btn-primary" id="btnAddOrUpdateTest">
                                    Sửa Đề Thi
                                </button>
                            </c:if>
                            <c:if test="${empty testEdit.id}">
                                <button type="button" class="btn btn-primary" id="btnAddOrUpdateTest">
                                    Thêm Đề Thi
                                </button>
                            </c:if>
                            <a href="/admin/test-list">
                                <button type="button" class="btn btn-warning">
                                    Hủy Thao Tác
                                </button>
                            </a>
                        </div>
                    </div>

                </form:form>
            </div>
        </div>
    </div>
</div><!-- /.main-content -->

<script>
    const btn = document.getElementById("btnAddOrUpdateTest");

    btn.addEventListener("click", function () {
        var formData = $('#form-edit').serializeArray();
        var json = {};

        formData.forEach(function (field) {
            // Bỏ qua trường _partTestEntities
            if (!field.name.startsWith("_")) {
                if (field.name === "partTestEntities") {
                    // Nếu là checkbox, thêm vào mảng và chuyển giá trị thành Long
                    if (!json[field.name]) json[field.name] = [];
                    json[field.name].push(parseInt(field.value)); // Chuyển thành số nguyên (Long)
                } else {
                    // Kiểm tra nếu là trường id thì chuyển thành Long
                    if (field.name === "id" || field.name === "numberOfParticipants") {
                        json[field.name] = field.value ? parseInt(field.value) : null;
                    } else {
                        json[field.name] = field.value;
                    }
                }
            }
        });

        btnAddOrUpdateTest(json);
    });



    const btnAddOrUpdateTest = (json) => {
        $.ajax({
            url: "/api/tests",
            type: "POST", // Đảm bảo đây là POST
            data: JSON.stringify(json),
            contentType: "application/json",
            dataType: "json",
            success: function () {
                alert("Lưu thành công!");
                window.location.href = "/admin/test-list";
            },
            error: function (result) {
                alert("Đã xảy ra lỗi: " + result.responseJSON.details);
            }
        });
    };
</script>

</body>
</html>

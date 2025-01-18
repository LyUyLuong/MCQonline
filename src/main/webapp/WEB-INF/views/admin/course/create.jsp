<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thông tin Khóa Học</title>
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
                <li class="active">Quản lý Khóa Học</li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">

            <div class="page-header">
                <h1>
                    ${courseEdit.id == null ? "Tạo mới Khóa Học" : "Chỉnh sửa Khóa Học"}
                </h1>
            </div><!-- /.page-header -->
        </div><!-- /.page-content -->

        <div class="row">
            <div class="col-xs-12">
                <form:form id="form-edit" method="POST" modelAttribute="courseEdit">
                    <input type="hidden" name="id" id="id" value="${courseEdit.id}"/>

                    <div class="form-group">
                        <label for="name" class="col-xs-3">Tên Khóa Học</label>
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
                        <label for="price" class="col-xs-3">Giá</label>
                        <div class="col-xs-9">
                            <form:input path="price" type="number" class="form-control"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="category" class="col-xs-3">Danh Mục</label>
                        <div class="col-xs-9">
                            <form:select path="category" class="form-control">
                                <form:option value="" label="---Chọn Danh Mục---"/>
                                <form:options items="${categories}" itemValue="id" itemLabel="name"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="status" class="col-xs-3">Trạng Thái</label>
                        <div class="col-xs-9">
                            <form:select path="status" class="form-control">
                                <form:option value="active" label="Hoạt động"/>
                                <form:option value="inactive" label="Ngừng hoạt động"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 no-padding-right">Hình đại diện</label>
                        <input class="col-sm-3 no-padding-right" type="file" id="uploadImage"/>
                        <div class="col-sm-9">
                            <c:if test="${not empty courseEdit.image}">
                                <c:set var="imagePath" value="/repository${courseEdit.image}"/>
                                <img src="${imagePath}" id="viewImage" width="300px" height="300px" style="margin-top: 50px">
                            </c:if>
                            <c:if test="${empty courseEdit.image}">
                                <img src="/admin/image/default.png" id="viewImage" width="300px" height="300px">
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3"></label>
                        <div class="col-xs-9">
                            <c:if test="${not empty courseEdit.id}">
                                <button type="button" class="btn btn-primary" id="btnAddOrUpdateCourse">
                                    Sửa Khóa Học
                                </button>
                            </c:if>
                            <c:if test="${empty courseEdit.id}">
                                <button type="button" class="btn btn-primary" id="btnAddOrUpdateCourse">
                                    Thêm Khóa Học
                                </button>
                            </c:if>
                            <a href="/admin/course-list">
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
    const btn = document.getElementById("btnAddOrUpdateCourse");

    var imageBase64 = '';
    var imageName = '';

    $('#uploadImage').change(function (event) {
        const reader = new FileReader();
        const file = event.target.files[0];
        if (file) {
            reader.onload = function (e) {
                imageBase64 = e.target.result;
                imageName = file.name.replace(/\s+/g, '-'); // Tên không dấu và không có khoảng cách
                $('#viewImage').attr('src', imageBase64);
            };
            reader.readAsDataURL(file);
        }
    });

    btn.addEventListener("click", function () {
        const formData = $('#form-edit').serializeArray();
        const json = {};

        formData.forEach(field => {
            if (field.name === "id" || field.name === "price") {
                json[field.name] = field.value ? parseFloat(field.value) : null;
            } else {
                json[field.name] = field.value;
            }
        });

        if (imageBase64) {
            json['imageBase64'] = imageBase64;
            json['imageName'] = imageName;
        }

        $.ajax({
            url: "/api/courses",
            type: "POST",
            data: JSON.stringify(json),
            contentType: "application/json",
            dataType: "json",
            success: function () {
                alert("Lưu thành công!");
                window.location.reload();
            },
            error: function (result) {
                alert("Đã xảy ra lỗi: " + (result.responseJSON?.message || "Không xác định"));
            }
        });
    });

</script>

</body>
</html>

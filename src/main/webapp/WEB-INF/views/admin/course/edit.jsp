<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Thông tin khóa học</title>
</head>
<body>

<div class="main-content">
  <div class="main-content-inner">
    <div class="breadcrumbs" id="breadcrumbs">
      <script type="text/javascript">
        try {
          ace.settings.check('breadcrumbs', 'fixed')
        } catch (e) {
        }
      </script>

      <ul class="breadcrumb">
        <li>
          <i class="ace-icon fa fa-home home-icon"></i>
          <a href="#">Home</a>
        </li>
        <li class="active">Dashboard</li>
      </ul>
    </div>
    <div class="page-content">

      <div class="page-header">
        <h1>
          Sửa đổi khóa học
        </h1>
      </div>
    </div>

    <div class="row">
      <div class="col-xs-12">
        <form:form id="form-edit" method="POST" modelAttribute="courseEdit">

          <input type="hidden" name="id" id="id" value="${courseEdit.id}"/>

          <div class="form-group">
            <label for="name" class="col-xs-3">Tên khóa học</label>
            <div class="col-xs-9">
              <form:input path="name" class="form-control"/>
            </div>
          </div>

          <div class="form-group">
            <label for="description" class="col-xs-3">Mô tả</label>
            <div class="col-xs-9">
              <form:textarea path="description" class="form-control"/>
            </div>
          </div>

          <div class="form-group">
            <label for="price" class="col-xs-3">Giá</label>
            <div class="col-xs-9">
              <form:input path="price" class="form-control"/>
            </div>
          </div>

          <div class="form-group">
            <label for="category" class="col-xs-3">Danh mục</label>
            <div class="col-xs-9">
              <form:select path="category" class="form-control">
                <form:option value="" label="---Chọn Danh mục---"/>
                <form:options items="${categories}" itemValue="id" itemLabel="name"/>
              </form:select>
            </div>
          </div>

          <div class="form-group">
            <label for="status" class="col-xs-3">Trạng thái</label>
            <div class="col-xs-9">
              <form:select path="status" class="form-control">
                <form:option value="" label="---Chọn Trạng thái---"/>
                <form:option value="active" label="Kích hoạt"/>
                <form:option value="inactive" label="Không kích hoạt"/>
              </form:select>
            </div>
          </div>

          <div class="form-group">
            <label class="col-sm-3 no-padding-right">Hình đại diện</label>
            <input class="col-sm-3 no-padding-right" type="file" id="uploadImage"/>
            <div class="col-sm-9">
              <c:if test="${not empty courseEdit.image}">
                <c:set var="imagePath" value="${courseEdit.image}"/>
                <img src="/repository${imagePath}" id="viewImage" width="300px" height="300px" style="margin-top: 50px">
              </c:if>
              <c:if test="${empty courseEdit.image}">
                <img src="/images/default.png" id="viewImage" width="300px" height="300px">
              </c:if>
            </div>
          </div>

          <div class="form-group">
            <label class="col-xs-3"></label>
            <div class="col-xs-9">

              <c:if test="${not empty courseEdit.id}">
                <button type="button" class="btn btn-primary" id="btnAddOrUpdateCourse">
                  Sửa khóa học
                </button>
              </c:if>

              <c:if test="${empty courseEdit.id}">
                <button type="button" class="btn btn-primary" id="btnAddOrUpdateCourse">
                  Thêm khóa học
                </button>
              </c:if>

              <a href="/admin/course-list">
                <button type="button" class="btn btn-warning">
                  Hủy thao tác
                </button>
              </a>
            </div>
          </div>

        </form:form>
      </div>
    </div>
  </div>
</div>

<script>
  var imageBase64 = '';
  var imageName = '';

  $('#uploadImage').change(function (event) {
    var reader = new FileReader();
    var file = $(this)[0].files[0];
    if (file) {
      reader.onload = function(e) {
        imageBase64 = e.target.result;
        imageName = file.name.replace(/\s+/g, '-'); // Remove spaces from file name
        $('#viewImage').attr('src', imageBase64);
      };
      reader.readAsDataURL(file);
    }
  });

  $('#btnAddOrUpdateCourse').click(function () {
    var formData = $('#form-edit').serializeArray();
    var json = {};

    $.each(formData, (i, v) => {
      json[v.name] = v.value;
    });

    if (imageBase64) {
      json['imageBase64'] = imageBase64;
      json['imageName'] = imageName;
    }

    btnAddOrUpdateCourse(json);
  });

  const btnAddOrUpdateCourse = (json) => {
    $.ajax({
      url: "/api/courses",
      type: "POST",
      data: JSON.stringify(json),
      contentType: "application/json",
      dataType: "json",
      success: function () {
        alert("Lưu thành công!");
        window.location.href = "/admin/course-list";
      },
      error: function (result) {
        alert(result.responseJSON?.details || "Đã xảy ra lỗi!");
      }
    });
  };
</script>

</body>
</html>

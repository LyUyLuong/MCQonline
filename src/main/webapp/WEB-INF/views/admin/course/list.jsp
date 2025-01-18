<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglib.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<html>
<head>
    <title>Danh sách khóa học</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
</head>
<body>

<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <ul class="breadcrumb">
                <li>
                    <i class="fas fa-home home-icon"></i>
                    <a href="#">Home</a>
                </li>
                <li class="active">Course Management</li>
            </ul>
        </div>
        <div class="page-content">

            <div class="page-header">
                <h1>
                    Danh sách khóa học
                </h1>
            </div>
        </div>

        <div class="row">
            <div class="col-xs-12">
                <div class="widget-box">
                    <div class="widget-header">
                        <h4 class="widget-title">Tìm kiếm khóa học</h4>
                        <span class="widget-toolbar">
                            <a href="#" data-action="reload">
                                <i class="fas fa-sync"></i>
                            </a>
                            <a href="#" data-action="collapse">
                                <i class="fas fa-chevron-up"></i>
                            </a>
                            <a href="#" data-action="close">
                                <i class="fas fa-times"></i>
                            </a>
                        </span>
                    </div>

                    <div class="widget-body">
                        <div class="widget-main">
                            <form:form modelAttribute="modelSearch" action="/admin/course-list" method="GET"
                                       class="form-horizontal" id="listForm">
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <div class="col-xs-6">
                                            <label for="name">Tên khóa học</label>
                                            <form:input path="name" class="form-control" placeholder="Nhập tên khóa học"/>
                                        </div>
                                        <div class="col-xs-6">
                                            <label for="status">Trạng thái</label>
                                            <form:select path="status" class="form-control">
                                                <form:option value="" label="---Chọn trạng thái---"/>
                                                <form:options items="${statusList}"/>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <div class="col-xs-6">
                                            <button type="submit" class="btn btn-sm btn-primary">
                                                <i class="fa fa-search" aria-hidden="true"></i> Tìm kiếm
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>

                <div class="pull-right">
                    <a href="/admin/course-create">
                        <button class="btn btn-app btn-primary btn-sm">
                            <i class="fa fa-plus" aria-hidden="true"></i> Tạo khóa học
                        </button>
                    </a>
                </div>
            </div>
        </div>

        <div class="hr hr-18 dotted hr-double"></div>

        <div class="row">
            <div class="col-xs-12">
                <div class="table-responsive">
                    <display:table name="model.listResult" cellspacing="0" cellpadding="0"
                                   requestURI="${formUrl}" partialList="true" sort="external"
                                   size="${model.totalItems}" defaultorder="ascending"
                                   id="tableList" pagesize="${model.maxPageItems}"
                                   export="false"
                                   class="table table-striped table-bordered table-hover dataTable no-footer"
                                   style="margin: 3em 0 1.5em;">
                        <display:column property="name" title="Tên khóa học" headerClass="text-left"/>
                        <display:column property="createdBy" title="Người tạo" headerClass="text-left"/>
                        <display:column property="createdDate" title="Ngày tạo" headerClass="text-left"/>
                        <display:column property="modifiedBy" title="Người sửa" headerClass="text-left"/>
                        <display:column property="modifiedDate" title="Ngày sửa" headerClass="text-left"/>

                        <display:column title="Thao tác" headerClass="text-left">
                            <div class="hidden-sm hidden-xs btn-group">
                                <a href="/admin/course-edit-${tableList.id}">
                                    <button class="btn btn-xs btn-info" title="Sửa khóa học">
                                        <i class="fa fa-pencil"></i>
                                    </button>
                                </a>
                                <button class="btn btn-xs btn-danger" onclick="deletecourse(${tableList.id})"
                                        title="Xóa khóa học">
                                    <i class="fa fa-trash"></i>
                                </button>
                            </div>
                        </display:column>
                    </display:table>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const deletecourse = (courseId) => {
        if (confirm("Bạn có chắc chắn muốn xóa khóa học này?")) {
            $.ajax({
                url: `/api/courses/${courseId}`,
                type: "DELETE",
                success: function () {
                    alert("Xóa thành công!");
                    location.reload();
                },
                error: function () {
                    alert("Có lỗi xảy ra, vui lòng thử lại!");
                }
            });
        }
    };
</script>
</body>
</html>

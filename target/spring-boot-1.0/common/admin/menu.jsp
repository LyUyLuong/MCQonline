<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="sidebar" class="sidebar                  responsive                    ace-save-state">
    <script type="text/javascript">
        try {
            ace.settings.loadState('sidebar')
        } catch (e) {
        }
    </script>
    <div class="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large">
            <a href="/trang-chu" class="shortcut-btn" title="Trang chủ">
                <button class="btn btn-success shortcut-icon">
                    <i class="fa fa-university" aria-hidden="true"></i>
                </button>
            </a>
            <button class="btn btn-info shortcut-icon" title="Viết">
                <i class="fa fa-pencil"></i>
            </button>
            <button class="btn btn-warning shortcut-icon" title="Người dùng">
                <i class="fa fa-users"></i>
            </button>
            <button class="btn btn-danger shortcut-icon" title="Cài đặt">
                <i class="fa fa-cogs"></i>
            </button>
        </div>
        <div class="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div>
    <%--    <ul class="nav nav-list">--%>
    <%--    <li class="">--%>
    <%--        <a href="#" class="dropdown-toggle">--%>
    <%--            &lt;%&ndash;<i class="menu-icon fa fas fa-users"></i>&ndash;%&gt;--%>
    <%--            &lt;%&ndash;<span class="menu-text">QL Tòa Nhà</span>&ndash;%&gt;--%>
    <%--            &lt;%&ndash;<b class="arrow fa fa-angle-down"></b>&ndash;%&gt;--%>
    <%--            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-building-fill" viewBox="0 0 16 16">--%>
    <%--                <path d="M3 0a1 1 0 0 0-1 1v14a1 1 0 0 0 1 1h3v-3.5a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 .5.5V16h3a1 1 0 0 0 1-1V1a1 1 0 0 0-1-1H3Zm1 2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1Zm3 0a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1Zm3.5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5ZM4 5.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1ZM7.5 5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5Zm2.5.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1ZM4.5 8h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5Zm2.5.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1Zm3.5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5Z"/>--%>
    <%--                <span class="menu-text"> Quản Lý Tòa Nhà</span>--%>
    <%--            </svg>--%>
    <%--        </a>--%>
    <%--        <b class="arrow"></b>--%>
    <%--        <ul class="submenu">--%>
    <%--            <li class="">--%>
    <%--                <a href='<c:url value='/admin/building-list'/>'>--%>
    <%--                    <i class="menu-icon fa fa-caret-right"></i>--%>
    <%--                    Danh sách tòa nhà--%>
    <%--                </a>--%>
    <%--                <b class="arrow"></b>--%>
    <%--            </li>--%>
    <%--        </ul>--%>
    <%--    </li>--%>
    <%--</ul>--%>

    <ul class="nav nav-list">
        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-file-text"></i>
                <span class="menu-text">Quản Lý Đề Thi</span>
            </a>
            <b class="arrow"></b>
            <ul class="submenu">
                <li class="">
                    <a href='/admin/test-list'>
                        <i class="menu-icon fa fa-caret-right"></i>
                        Danh sách đề thi
                    </a>
                    <b class="arrow"></b>
                </li>
            </ul>
        </li>
    </ul>
    <ul class="nav nav-list">
        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fas fa-users"></i>
                <span class="menu-text">Quản Lý Tài Khoản</span>
            </a>
            <b class="arrow"></b>
            <ul class="submenu">
                <li class="">
                    <a href='/admin/user-list'>
                        <i class="menu-icon fa fa-caret-right"></i>
                        Danh sách tài khoản
                    </a>
                    <b class="arrow"></b>
                </li>
            </ul>
        </li>
    </ul>

    <%--    <ul class="nav nav-list">--%>
    <%--        <li class="">--%>
    <%--            <a href="#" class="dropdown-toggle">--%>
    <%--                <i class="menu-icon fa fas fa-users"></i>--%>
    <%--                <span class="menu-text">Quản Lý Khách Hàng</span>--%>
    <%--            </a>--%>
    <%--            <b class="arrow"></b>--%>
    <%--            <ul class="submenu">--%>
    <%--                <li class="">--%>
    <%--                    <a href='/admin/customer-list'>--%>
    <%--                        <i class="menu-icon fa fa-caret-right"></i>--%>
    <%--                        Danh sách khách hàng--%>
    <%--                    </a>--%>
    <%--                    <b class="arrow"></b>--%>
    <%--                </li>--%>
    <%--            </ul>--%>
    <%--        </li>--%>
    <%--    </ul>--%>
    <div class="sidebar-toggle sidebar-collapse">
        <i class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left"
           data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>
</div>
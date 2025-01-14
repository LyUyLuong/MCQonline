<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Chi tiết Đơn hàng</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
  <h1 class="my-4">Chi tiết Đơn hàng</h1>
  <p><strong>Mã đơn hàng:</strong> ${order.id}</p>
  <p><strong>Ngày đặt:</strong> ${order.orderDate}</p>
  <p><strong>Trạng thái:</strong> ${order.status}</p>

  <h2 class="mt-4">Danh sách sản phẩm</h2>
  <table class="table table-striped">
    <thead>
    <tr>
      <th>Tên sản phẩm</th>
      <th>Số lượng</th>
      <th>Giá</th>
      <th>Thành tiền</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${order.orderItems}">
      <tr>
        <td>${item.course.name}</td>
        <td>${item.quantity}</td>
        <td>${item.price} VND</td>
        <td>${item.price * item.quantity} VND</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <div class="text-right">
    <h4>Tổng cộng: ${order.totalPrice} VND</h4>
  </div>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>

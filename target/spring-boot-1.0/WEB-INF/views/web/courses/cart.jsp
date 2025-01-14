<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 1/13/2025
  Time: 7:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Giỏ hàng</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
  <h1 class="my-4">Giỏ hàng</h1>
  <table class="table table-striped">
    <thead>
    <tr>
      <th>Tên khóa học</th>
      <th>Giá</th>
      <th>Số lượng</th>
      <th>Thành tiền</th>
      <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${cartItems}">
      <tr data-item-id="${item.id}" data-course-name="${item.course.name}" data-price="${item.price}" data-quantity="${item.quantity}">
        <td class="course-name"></td>
        <td class="price"></td>
        <td class="quantity"></td>
        <td class="total"></td>
        <td>
          <button class="btn btn-danger remove-item">Xóa</button>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <div class="text-right">
    <h4 id="total-price"></h4>
    <button class="btn btn-success" id="checkout">Thanh toán</button>
  </div>
</div>

<script>
  // Populate table rows dynamically
  document.querySelectorAll('tr[data-item-id]').forEach(row => {
    const itemId = row.getAttribute('data-item-id');
    const courseName = row.getAttribute('data-course-name');
    const price = parseFloat(row.getAttribute('data-price'));
    const quantity = parseInt(row.getAttribute('data-quantity'));
    const total = price * quantity;

    row.querySelector('.course-name').textContent = courseName;
    row.querySelector('.price').textContent = price.toLocaleString() + ' VND';
    row.querySelector('.quantity').textContent = quantity;
    row.querySelector('.total').textContent = total.toLocaleString() + ' VND';

    // Add event listener for remove button
    row.querySelector('.remove-item').addEventListener('click', function () {
      removeFromCart(itemId);
    });
  });

  // Update total price dynamically
  const totalPrice = Array.from(document.querySelectorAll('tr[data-item-id]'))
          .reduce((sum, row) => {
            const price = parseFloat(row.getAttribute('data-price'));
            const quantity = parseInt(row.getAttribute('data-quantity'));
            return sum + (price * quantity);
          }, 0);

  document.getElementById('total-price').textContent = 'Tổng cộng: ' + totalPrice.toLocaleString() + ' VND';

  function removeFromCart(itemId) {
    fetch(`/api/cart/remove/${itemId}`, {method: 'DELETE'})
            .then(response => {
              if (response.ok) {
                alert("Đã xóa khỏi giỏ hàng!");
                location.reload();
              } else {
                alert("Có lỗi xảy ra!");
              }
            });
  }

  document.getElementById('checkout').addEventListener('click', function () {
    fetch('/api/cart/checkout', {method: 'POST'})
            .then(response => {
              if (response.ok) {
                alert("Thanh toán thành công!");
                window.location.href = "/orders";
              } else {
                alert("Có lỗi xảy ra!");
              }
            });
  });
</script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>
</body>
</html>

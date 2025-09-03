<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Danh sách đơn hàng</title></head>
<body>
<h2>Danh sách đơn hàng</h2>
<table border="1" cellpadding="8" cellspacing="0">
  <tr>
    <th>ID</th>
    <th>Account ID</th>
    <th>Trạng thái</th>
    <th>Tổng tiền</th>
    <th>Ngày tạo</th>
  </tr>
  <c:forEach var="order" items="${orders}">
    <tr>
      <td>${order.id}</td>
      <td>${order.accountId}</td>
      <td>${order.status}</td>
      <td>${order.totalAmount}</td>
      <td>${order.createdAt}</td>
    </tr>
  </c:forEach>
</table>
</body>
</html>

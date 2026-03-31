<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registercss.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

    <h2>Đăng Ký</h2>

    <form method="post" action="${pageContext.request.contextPath}/register">
        <label>Tên Đăng Nhập:</label>
        <input name="username" required /><br/>

        <label>Mật Khẩu:</label>
        <input type="password" name="password" required /><br/>

        <label>Họ Và Tên:</label>
        <input name="fullname" required/><br/>

        <label>Email:</label>
        <input name="email" type="email" required /><br/>

        <button type="submit">Đăng Ký</button>
    </form>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
</body>
</html>

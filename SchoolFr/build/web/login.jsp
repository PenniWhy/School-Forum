<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng Nhập</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logincss.css">
    </head>
    <body>
        <jsp:include page="/includes/header.jsp" />

        <div class="login-container">
            <h2 class="title">Đăng Nhập</h2>

            <form method="post" action="${pageContext.request.contextPath}/login" class="login-form">
                <div class="form-group">
                    <label for="username">Tên Đăng Nhập:</label>
                    <input type="text" id="username" name="username" required>
                </div>

                <div class="form-group">
                    <label for="password">Mật Khẩu:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" class="btn-submit">Đăng Nhập</button>

                <c:if test="${not empty error}">
                    <p class="error-msg">${error}</p>
                </c:if>

                <p class="register-link">
                    Chưa có tài khoản? 
                    <a href="${pageContext.request.contextPath}/register.jsp">Đăng ký ngay</a>
                </p>
            </form>
        </div>
    </body>
</html>

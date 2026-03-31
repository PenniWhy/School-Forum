<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="header">
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            Xin chào <b>${sessionScope.user.fullName}</b> |
            <a href="${pageContext.request.contextPath}/questions">Trang chủ</a> |
            <a href="${pageContext.request.contextPath}/ask.jsp">Đặt câu hỏi</a> |
            <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/questions">Trang chủ</a> |
            <a href="${pageContext.request.contextPath}/login">Đăng nhập</a> |
            <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
        </c:otherwise>
    </c:choose>
</div>
<hr/>

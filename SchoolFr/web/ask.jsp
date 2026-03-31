<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:if test="${empty sessionScope.user}">
    <c:redirect url="${pageContext.request.contextPath}/login"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt Câu Hỏi</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/askcss.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />
    <h2>Đặt Câu Hỏi</h2>

    <form method="post" action="${pageContext.request.contextPath}/addQuestion">
        <label>Tiêu Đề:</label>
        <input name="title" required /><br>

        <label>Nội Dung:</label><br>
        <textarea name="content" rows="6" cols="60"></textarea><br>

        <label>
            <input type="checkbox" name="anonymous" value="true" />
            Đặt câu hỏi ẩn danh
        </label><br>

        <button type="submit">Gửi</button>
    </form>
</body>
</html>

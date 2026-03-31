<%-- 
    Document   : editQuestion
    Created on : Nov 12, 2025, 9:18:12 AM
    Author     : thanh
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sửa Câu Hỏi</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/askcss.css">
</head>
<body>

<jsp:include page="/includes/header.jsp" />

<h2>✏️ Chỉnh Sửa Câu Hỏi</h2>

<form method="post" action="${pageContext.request.contextPath}/editQuestion">
    <input type="hidden" name="id" value="${question.questionId}" />

    <label>Tiêu đề:</label><br/>
    <input type="text" name="title" value="${question.title}" required style="width: 100%;"/><br/><br/>

    <label>Nội dung:</label><br/>
    <textarea name="content" required rows="10" style="width: 100%;">${question.content}</textarea><br/><br/>

    <button type="submit">💾 Lưu</button>
    <a href="${pageContext.request.contextPath}/questions">
        <button type="button">❌ Hủy</button>
    </a>
</form>

</body>
</html>

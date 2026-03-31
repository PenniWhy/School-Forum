<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh Sách Câu Hỏi</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/questionscss.css">
    </head>
    <body>
        <jsp:include page="/includes/header.jsp" />

        <div class="container">
            <h2>💬 Diễn Đàn Hỏi Đáp</h2>

            <form method="get" action="${pageContext.request.contextPath}/questions" class="search-form">
                <input type="text" name="search" placeholder="Tìm câu hỏi..." value="${param.search}" />
                <button type="submit">🔍 Tìm</button>
            </form>

            <hr/>

            <c:forEach var="q" items="${questions}">
                <div class="question-card">
                    <h3>
                        <a href="${pageContext.request.contextPath}/viewQuestion?id=${q.questionId}">
                            ${fn:escapeXml(q.title)}
                        </a>
                    </h3>
                    <p>${fn:escapeXml(q.content)}</p>
                    <small>
                        ✏️ Bởi 
                        <c:choose>

         
                            <c:when test="${q.anonymous}">
                                <c:choose>

          
                                    <c:when test="${sessionScope.user != null && fn:toLowerCase(sessionScope.user.role) == 'admin'}">
                                        Ẩn danh (${fn:escapeXml(q.authorName)})
                                    </c:when>

                    
                                    <c:otherwise>
                                        Ẩn danh
                                    </c:otherwise>

                                </c:choose>
                            </c:when>

             
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${sessionScope.user != null && sessionScope.user.userId == q.userId}">
                                        me
                                    </c:when>
                                    <c:otherwise>
                                        ${fn:escapeXml(q.authorName)}
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>

                        </c:choose>
                        — 🕓 ${q.createdAt}
                    </small>

                    <div class="action-buttons">

                        <!-- ✏️ EDIT: chỉ chủ bài -->
                        <c:if test="${sessionScope.user != null && sessionScope.user.userId == q.userId}">
                            <form method="get" action="${pageContext.request.contextPath}/editQuestion" style="display:inline;">
                                <input type="hidden" name="id" value="${q.questionId}" />
                                <button type="submit" class="edit-btn">✏️ Sửa</button>
                            </form>
                        </c:if>

                        <!-- 🗑 DELETE: chủ bài hoặc admin -->
                        <c:if test="${sessionScope.user != null && 
                                      (sessionScope.user.userId == q.userId || fn:toLowerCase(sessionScope.user.role) == 'admin')}">

                              <form method="post" action="${pageContext.request.contextPath}/deleteQuestion"
                                    onsubmit="return confirm('Bạn có chắc muốn xóa bài viết này không?')" style="display:inline;">
                                  <input type="hidden" name="id" value="${q.questionId}" />
                                  <button type="submit" class="delete-btn">🗑️ Xóa</button>
                              </form>

                        </c:if>

                    </div>
                </div>
                <hr/>
            </c:forEach>
        </div>
    </body>
</html>

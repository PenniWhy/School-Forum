<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${fn:escapeXml(question.title)}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewquestion.css">
    </head>
    <body>
        <jsp:include page="/includes/header.jsp" />

        <div class="container">
            <h2>${fn:escapeXml(question.title)}</h2>
            <p>${fn:escapeXml(question.content)}</p>
            <small>
                ✏️ Bởi 
                <c:choose>

                    <c:when test="${question.anonymous == true}">
                        <c:choose>

                            <c:when test="${sessionScope.user != null && fn:toLowerCase(sessionScope.user.role) == 'admin'}">
                                Ẩn danh (${fn:escapeXml(question.authorName)})
                            </c:when>

                            <c:otherwise>
                                Ẩn danh
                            </c:otherwise>

                        </c:choose>
                    </c:when>

                    <c:otherwise>
                        ${fn:escapeXml(question.authorName)}
                    </c:otherwise>

                </c:choose>
                — 🕓 ${question.createdAt}
            </small>

            <hr>

            <h3>💬 Câu trả lời</h3>

            <c:choose>
                <c:when test="${empty answers}">
                    <p><i>Chưa có câu trả lời nào. Hãy là người đầu tiên!</i></p>
                </c:when>

                <c:otherwise>
                    <c:forEach var="a" items="${answers}">
                        <div class="answer-card">

                            <p>${fn:escapeXml(a.content)}</p>

                            <small>
                                ✏️ Bởi 
                                <c:choose>

                                  
                                    <c:when test="${a.anonymous}">
                                        <c:choose>

                                        
                                            <c:when test="${sessionScope.user != null 
                                                            && fn:toLowerCase(sessionScope.user.role) == 'admin'}">
                                                    Ẩn danh (${fn:escapeXml(a.authorName)})
                                            </c:when>

                                          
                                            <c:otherwise>
                                                Ẩn danh
                                            </c:otherwise>

                                        </c:choose>
                                    </c:when>

                                
                                    <c:otherwise>
                                        ${fn:escapeXml(a.authorName)}
                                    </c:otherwise>

                                </c:choose>
                                — 🕓 ${a.createdAt}
                            </small>

                            <!-- 🗑 DELETE -->
                            <c:if test="${sessionScope.user != null 
                                          && (
                                          fn:toLowerCase(sessionScope.user.role) == 'admin'
                                          || sessionScope.user.userId == a.userId
                                          || (a.anonymous 
                                          && not empty sessionScope.anonAnswers 
                                          && sessionScope.anonAnswers.contains(a.answerId))
                                          )
                                  }">

                                <form method="post" action="${pageContext.request.contextPath}/deleteAnswer" class="inline-form">
                                    <input type="hidden" name="id" value="${a.answerId}">
                                    <button type="submit" class="delete-btn">🗑 Xóa</button>
                                </form>

                            </c:if>

                        </div>
                        <hr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

            <c:if test="${not empty sessionScope.user}">
                <h3>✏️ Viết câu trả lời của bạn</h3>
                <form method="post" action="${pageContext.request.contextPath}/addAnswer">
                    <input type="hidden" name="questionId" value="${question.questionId}" />
                    <textarea name="content" rows="4" required placeholder="Nhập câu trả lời của bạn..."></textarea><br>

                    <label>
                        <input type="checkbox" name="anonymous" />
                        Trả lời ẩn danh
                    </label><br>

                    <button type="submit">Gửi trả lời</button>
                </form>
            </c:if>

            <c:if test="${empty sessionScope.user}">
                <p><a href="${pageContext.request.contextPath}/login">Đăng nhập</a> để viết câu trả lời.</p>
            </c:if>
        </div>
    </body>
</html>

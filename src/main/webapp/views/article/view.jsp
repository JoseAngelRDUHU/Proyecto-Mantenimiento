<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>${article.title}</title>
    <script>
        function toggleEdit(commentId) {
            const textDiv = document.getElementById('text-' + commentId);
            const formDiv = document.getElementById('form-' + commentId);
            textDiv.style.display = textDiv.style.display === 'none' ? 'block' : 'none';
            formDiv.style.display = formDiv.style.display === 'none' ? 'block' : 'none';
        }

        function confirmDelete(commentId) {
            if (confirm("<spring:message code='view.delete.confirm' />")) {
                document.getElementById('delete-form-' + commentId).submit();
            }
        }
    </script>

    <style>
    body {
        font-family: 'Segoe UI', Tahoma, sans-serif;
        background-color: #f9fafb;
        color: #333;
        padding: 40px;
        max-width: 800px;
        margin: auto;
        line-height: 1.6;
    }

    h1 {
        color: #2c3e50;
        font-size: 2em;
        margin-bottom: 10px;
    }

    h3, h4 {
        color: #34495e;
        margin-top: 30px;
    }

    p {
        margin: 10px 0;
    }

    hr {
        border: 0;
        border-top: 1px solid #ddd;
        margin: 30px 0;
    }

    img {
        border-radius: 6px;
        margin-top: 10px;
        max-width: 100%;
        height: auto;
        display: block;
    }

    iframe {
        margin-top: 10px;
        max-width: 100%;
        border-radius: 6px;
    }

    .comment-box {
        background-color: #ffffff;
        border: 1px solid #ccc;
        border-radius: 6px;
        padding: 10px;
        margin: 10px 0;
        width: 100%;
        box-sizing: border-box;
    }

    .comment-box p {
        margin: 0;
    }

    textarea.comment-box {
        resize: vertical;
        width: 100%;
        font-family: inherit;
        font-size: 14px;
    }

    input[type="submit"], input[type="button"], button {
        background-color: #007bff;
        color: white;
        border: none;
        padding: 8px 14px;
        margin: 5px 4px 0 0;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s;
        font-size: 14px;
    }

    input[type="submit"]:hover, input[type="button"]:hover, button:hover {
        background-color: #0056b3;
    }

    form {
        margin-top: 10px;
    }

    form[style*="display:inline;"] {
        display: inline;
    }

    em {
        color: #888;
        font-style: italic;
    }

    </style>
</head>
<body>
    <h1>${article.title}</h1>
    <p><strong><spring:message code="view.author"/></strong> ${article.author.name}</p>
    <p><strong><spring:message code="view.category"/></strong> ${article.category.name}</p>
    <p><strong><spring:message code="view.published"/></strong> <fmt:formatDate value="${article.publicationMoment}" pattern="dd/MM/yyyy HH:mm"/></p>
    <hr/>
    <c:if test="${not empty article.coverImageUrl}">
        <div>
            <h4><spring:message code="view.relatedImage"/></h4>
            <img src="${article.coverImageUrl}" alt="Imagen del artículo" style="max-width: 100%; height: auto;" />
        </div>
    </c:if>
    <p>${article.body}</p>
    <c:if test="${not empty article.videoLink}">
        <div>
            <h4><spring:message code="view.relatedVideo"/></h4>
            <iframe width="560" height="315" src="${article.videoLink}" frameborder="0" allowfullscreen></iframe>
        </div>
    </c:if>

    <c:if test="${canToggleComments}">
        <form action="${pageContext.request.contextPath}/article/toggleComments.do" method="post">
            <input type="hidden" name="articleId" value="${article.id}" />
            <input type="submit" value="<spring:message code='view.enableComments.${article.commentsEnabled ? "off" : "on"}' />" />
        </form>
    </c:if>

    <hr/>
    <h3><spring:message code="view.title"/></h3>

    <c:if test="${article.commentsEnabled}">
        <c:forEach items="${article.comments}" var="comment">
            <div class="comment-box">
                <p><strong>${comment.author.userAccount.username}</strong> <spring:message code="view.published"/> <fmt:formatDate value="${comment.publicationMoment}" pattern="dd/MM/yyyy HH:mm"/></p>

                <div id="text-${comment.id}">
                    <p>${comment.text}</p>
                </div>

                <div id="form-${comment.id}" style="display:none;">
                    <form action="${pageContext.request.contextPath}/comment/edit.do" method="post">
                        <input type="hidden" name="commentId" value="${comment.id}" />
                        <textarea name="text" class="comment-box" rows="3" cols="50">${comment.text}</textarea><br/>
                        <input type="submit" value="<spring:message code='view.saveChanges' />" />
                        <input type="button" value="<spring:message code='view.cancel' />" onclick="toggleEdit(${comment.id})" />
                    </form>
                </div>

                <c:if test="${canEditOrDeleteComment[comment.id]}">
                    <button onclick="toggleEdit(${comment.id})"><spring:message code="view.edit"/></button>
                    <form id="delete-form-${comment.id}" action="${pageContext.request.contextPath}/comment/delete.do" method="post" style="display:inline;">
                        <input type="hidden" name="commentId" value="${comment.id}" />
                        <button type="button" onclick="confirmDelete(${comment.id})"><spring:message code="view.delete"/></button>
                    </form>
                </c:if>
            </div>
        </c:forEach>

        <hr/>
        <h4><spring:message code="view.newComment"/></h4>
        <form action="${pageContext.request.contextPath}/comment/add.do" method="post">
            <input type="hidden" name="articleId" value="${article.id}" />
            <textarea name="text" class="comment-box" rows="3" cols="60" required></textarea><br/>
            <input type="submit" value="<spring:message code="view.submit"/>" />
        </form>
    </c:if>

    <c:if test="${!article.commentsEnabled}">
        <p><em><spring:message code="view.disabled"/></em></p>
    </c:if>
</body>
</html>
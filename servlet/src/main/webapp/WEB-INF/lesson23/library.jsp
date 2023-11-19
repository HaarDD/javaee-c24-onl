<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.teachmeskills.lesson23.utils.FileUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="by.teachmeskills.lesson23.LibraryServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Библиотека</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/lesson23/library.css"/>?v=5">
    <script src="<c:url value="/js/lesson23/library.js"/>"></script>
</head>
<body>
<div class="library">
    <div class="library-header">Библиотека</div>
    <div class="library-help">Нажмите, чтобы загрузить файл</div>
    <ol class="book-list">
        <%
            List<String> bookNames = FileUtils.getFileNamesInDirectory(request.getServletContext().getRealPath("/") + LibraryServlet.BOOK_STORAGE_PATH);
            if (bookNames != null) {
                for (String bookName : bookNames) {
        %>
        <li><a href="library/control?book=<%= bookName %>"><%= bookName %>
        </a>
            <button class="remove-button" onclick="removeBook('<%=bookName%>')">× удалить</button>
        </li>
        <%
                }
            }
        %>
        <li><label for="show-upload-file-checkbox" id="show-upload-file">Загрузить книгу</label></li>
    </ol>
</div>
<input type="checkbox" id="show-upload-file-checkbox">
<jsp:include page="library_book_upload.jsp"/>
</body>
</html>

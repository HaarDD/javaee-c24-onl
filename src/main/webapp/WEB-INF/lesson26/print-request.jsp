<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/lesson26/tags/listtable.tld" prefix="lt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static by.teachmeskills.lesson26.SaveRequestServlet.ATTRIBUTE_REPAIR_REQUEST" %>
<html>
<head>
    <title>Печать заявок</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value='/css/global/bootstrap.min.css'/>" rel="stylesheet">
</head>
<body>
<c:choose>
    <c:when test="${empty sessionScope[ATTRIBUTE_REPAIR_REQUEST]}">
        <h1>Заявок нет</h1>
    </c:when>
    <c:otherwise>
        <h1>Список заявок</h1>
        <lt:listTable dataList="${sessionScope[ATTRIBUTE_REPAIR_REQUEST]}"
                      fieldsTranslator="${applicationScope.fieldsTranslator}"
                      listElementsTranslator="${applicationScope.listElementsTranslator}"
                      tableClasses="table table-striped"/>
    </c:otherwise>
</c:choose>
<script src="<c:url value='/js/global/bootstrap.bundle.min.js'/>"></script>
</body>
</html>
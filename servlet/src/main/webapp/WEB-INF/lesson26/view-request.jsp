<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="by.teachmeskills.lesson26.SaveRequestServlet" %>
<%@ page import="static by.teachmeskills.lesson26.SaveRequestServlet.ATTRIBUTE_REPAIR_REQUEST" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value='/css/global/bootstrap.min.css'/>" rel="stylesheet">
    <title>Список заявок</title>
</head>
<body>
<jsp:include page="request-navbar.jsp"/>
<div class="container">
    <c:choose>
        <c:when test="${empty sessionScope[ATTRIBUTE_REPAIR_REQUEST]}">
            <h1>Заявок нет</h1>
        </c:when>
        <c:otherwise>
            <h1>Список заявок</h1>
            <div class="row g-3">
                <div class="row-md-8">
                    <c:forEach var="repairRequest" items="${sessionScope[ATTRIBUTE_REPAIR_REQUEST]}">
                        <div class="card mb-4">
                            <div class="card-body">
                                <h5 class="card-title">Фамилия: ${repairRequest.clientLastName}</h5>
                                <h6 class="card-title">Имя: ${repairRequest.clientFirstName}</h6>
                                <p class="card-text">Адрес: ${repairRequest.clientAddress}</p>
                                <p>Услуги:</p>
                                <ul class="list-group">
                                    <c:forEach var="service" items="${repairRequest.clientService}">
                                        <li class="list-group-item">${SaveRequestServlet.SERVICE_LIST[service]}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<script src="<c:url value='/js/global/bootstrap.bundle.min.js'/>"></script>
</body>
</html>

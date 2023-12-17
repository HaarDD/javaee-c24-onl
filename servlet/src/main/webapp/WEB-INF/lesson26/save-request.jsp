<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="by.teachmeskills.lesson26.SaveRequestServletOLD" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value='/css/global/tom-select.bootstrap5.css'/>" rel="stylesheet">
    <link href="<c:url value='/css/global/bootstrap.min.css'/>" rel="stylesheet">
    <title>Оставить заявку</title>
</head>
<body>
<jsp:include page="request-navbar.jsp"/>
<div class="container">
    <div class="row">
        <div class="col g-5">
            <form class="row g-3 needs-validation" id="client-service-form" novalidate>
                <div class="col-md-5">
                    <label for="clientFirstName" class="form-label">Имя</label>
                    <input type="text" class="form-control" id="clientFirstName" required>
                    <div class="invalid-feedback">
                        Имя введено некорректно!
                    </div>
                </div>
                <div class="col-md-5">
                    <label for="clientLastName" class="form-label">Фамилия</label>
                    <input type="text" class="form-control" id="clientLastName" required>
                    <div class="invalid-feedback">
                        Фамилия введена некорректно!
                    </div>
                </div>
                <div class="col-md-10">
                    <label for="clientAddress" class="form-label">Адрес</label>

                    <select class="form-select" id="clientAddress" placeholder="Начните вводить адрес..."></select>

                    <div class="invalid-feedback">
                        Выберите адрес!
                    </div>
                </div>
                <div class="col-md-10">
                    <label for="clientService" class="form-label">Вид ремонта</label>
                    <select class="form-select" id="clientService" multiple required placeholder="Выберите услугу...">
                        <c:forEach items="#{SaveRequestServlet.SERVICE_LIST}" var="item">
                            <option value="${item.key}">${item.value}</option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback">
                        Выберите хотя бы одну услугу!
                    </div>
                </div>
                <div class="col-12">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="clientPersonalDataAgree"
                               required>
                        <label class="form-check-label" for="clientPersonalDataAgree">
                            Согласен с обработкой Персональных данных
                        </label>
                        <div class="invalid-feedback">
                            Необходимо согласие с обработкой Персональных данных!
                        </div>
                    </div>
                </div>
                <div class="col-5">
                    <button class="btn btn-primary" type="submit">Оставить заявку</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="<c:url value='/js/global/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/js/global/tom-select.complete.min.js'/>"></script>
<script src="<c:url value='/js/lesson26/save-request-script.js'/>"></script>
</body>
</html>

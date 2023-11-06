<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="by.teachmeskills.lesson26.SaveRequestServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="lesson26/css/tom-select.bootstrap5.css" rel="stylesheet">
    <link href="lesson26/css/bootstrap.min.css" rel="stylesheet">
    <title>Оставить заявку</title>
</head>
<body>
<jsp:include page="request-navbar.jsp"/>
<div class="container">
    <div class="row">
        <div class="col g-5">
            <form class="row g-3 needs-validation" id="client-service-form" novalidate>
                <div class="col-md-5">
                    <label for="client-first-name" class="form-label">Имя</label>
                    <input type="text" class="form-control" id="client-first-name" required>
                    <div class="invalid-feedback">
                        Имя введено некорректно!
                    </div>
                </div>
                <div class="col-md-5">
                    <label for="client-last-name" class="form-label">Фамилия</label>
                    <input type="text" class="form-control" id="client-last-name" required>
                    <div class="invalid-feedback">
                        Фамилия введена некорректно!
                    </div>
                </div>
                <div class="col-md-10">
                    <label for="client-address" class="form-label">Адрес</label>

                    <select class="form-select" id="client-address" placeholder="Начните вводить адрес..."></select>

                    <div class="invalid-feedback">
                        Выберите адрес!
                    </div>
                </div>
                <div class="col-md-10">
                    <label for="client-service" class="form-label">Вид ремонта</label>
                    <select class="form-select" id="client-service" multiple required placeholder="Выберите услугу...">
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
                        <input class="form-check-input" type="checkbox" value="" id="client-personal-data-agree"
                               required>
                        <label class="form-check-label" for="client-personal-data-agree">
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
<script src="lesson26/js/bootstrap.bundle.min.js"></script>
<script src="lesson26/js/tom-select.complete.min.js"></script>
<script src="lesson26/js/script.js"></script>
</body>
</html>

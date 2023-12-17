<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="by.teachmeskills.lesson26.RegistrationServlet" %>
<%@ page import="by.teachmeskills.lesson26.dao.RoleDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value='/css/global/bootstrap.min.css'/>" rel="stylesheet">
    <title>Оставить заявку</title>
</head>
<body>
<jsp:include page="request-navbar.jsp"/>
<div class="container">
    <div class="row">
        <div class="col g-5">
            <form class="row g-3 needs-validation" id="admin-registration-form" novalidate>
                <div class="col-md-5">
                    <label for="clientEmail" class="form-label">Email</label>
                    <input type="text" class="form-control" id="clientEmail" required>
                    <div class="invalid-feedback">
                        Email введен некорректно!
                    </div>
                </div>
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
                <div class="col-md-5">
                    <label for="clientPassword" class="form-label">Имя</label>
                    <input type="password" class="form-control" id="clientPassword" required>
                    <div class="invalid-feedback">
                        Пароль введен некорректно!
                    </div>
                </div>
                <div class="col-md-10">
                    <label for="clientRole" class="form-label">Роль</label>
                    <select class="form-select" id="clientRole" required placeholder="Выберите роль...">
                        <c:forEach items="#{RoleDAO.allRoles}" var="item">
                            <option value="${item.id}">${item.roleName}</option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback">
                        Выберите роль!
                    </div>
                </div>
                <div class="col-5">
                    <button class="btn btn-primary" type="submit">Зарегистрировать пользователя</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="<c:url value='/js/global/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/js/lesson26/registration.js'/>"></script>
</body>
</html>

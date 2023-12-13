<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <div class="col-md-5 mx-auto">
            <form class="row g-3 needs-validation" id="client-login-form" novalidate>
                <div class="col-md-12">
                    <label for="email" class="form-label">Логин (email)</label>
                    <input type="text" class="form-control" id="email" required>
                    <div class="invalid-feedback">
                        Имя введено некорректно!
                    </div>
                </div>
                <div class="col-md-12">
                    <label for="password" class="form-label">Пароль</label>
                    <input type="password" class="form-control" id="password" required>
                    <div class="invalid-feedback">
                        Пароль введен некорректно!
                    </div>
                </div>
                <div class="col-md-5 mx-auto text-center">
                    <button class="btn btn-primary w-100" type="submit">Войти</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="<c:url value='/js/global/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/js/lesson26/login.js'/>"></script>
</body>
</html>

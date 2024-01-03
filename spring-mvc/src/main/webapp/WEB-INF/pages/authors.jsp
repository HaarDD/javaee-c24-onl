<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="<c:url value='/css/global/tom-select.bootstrap5.css'/>" rel="stylesheet">
    <link href="<c:url value='/css/global/bootstrap.min.css'/>" rel="stylesheet">
    <title>Каталог авторов</title>
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Каталог авторов</h1>
    <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#editAuthorModal">
        Добавить автора
    </button>

    <div class="row">
        <c:forEach var="author" items="${authors}">
            <div class="col-md-4 mb-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${author.name}</h5>
                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                data-bs-target="#deleteAuthorModal" data-author-id="${author.id}">
                            Удалить
                        </button>
                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                data-bs-target="#editAuthorModal" data-author-id="${author.id}">
                            Редактировать
                        </button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Редактирование и добавление -->
<div class="modal fade" id="editAuthorModal" tabindex="-1" aria-labelledby="editAuthorModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editAuthorModalLabel"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form method="post" id="editAuthorForm">
                    <input type="hidden" name="id" id="edit-author-id">
                    <div class="mb-3">
                        <label for="edit-author-name" class="form-label">Имя автора</label>
                        <input type="text" class="form-control" id="edit-author-name" name="name" required>
                        <div id="nameErrorLabel" class="invalid-feedback"></div>
                    </div>
                    <button type="submit" class="btn btn-warning">Сохранить изменения</button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<!-- Удаление -->
<div class="modal fade" id="deleteAuthorModal" tabindex="-1" aria-labelledby="deleteAuthorModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteAuthorModalLabel">Удалить автора</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
            </div>
            <div class="modal-body">
                <p>Вы уверены, что хотите удалить автора?</p>
                <p id="deleteAuthorName"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                <a href="#" class="btn btn-danger" id="deleteAuthorButton">Удалить</a>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value='/js/global/jquery-3.7.1.min.js'/>"></script>
<script src="<c:url value='/js/global/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/js/global/tom-select.complete.min.js'/>"></script>
<script src="<c:url value='/js/global/popper.min.js'/>"></script>
<script src="<c:url value='/js/authors.js'/>"></script>
</body>
</html>
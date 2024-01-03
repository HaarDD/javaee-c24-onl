<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="<c:url value='/css/global/tom-select.bootstrap5.css'/>" rel="stylesheet">
    <link href="<c:url value='/css/global/bootstrap.min.css'/>" rel="stylesheet">
    <title>Каталог библиотеки</title>
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Каталог библиотеки</h1>
    <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#editBookModal">
        Добавить книгу
    </button>

    <div class="row">
        <c:forEach var="book" items="${books}">
            <div class="col-md-4 mb-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${book.name}</h5>
                        <p class="card-text">Авторы:
                            <c:forEach var="author" items="${book.authors}" varStatus="loop">
                                ${author.name}<c:if test="${not loop.last}">, </c:if>
                            </c:forEach>
                        </p>
                        <p class="card-text">Описание: ${book.description}</p>
                        <p class="card-text">ISBN: ${book.isbn}</p>
                        <p class="card-text">Кол-во страниц: ${book.pages}</p>
                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                data-bs-target="#deleteBookModal" data-book-id="${book.id}">
                            Удалить
                        </button>
                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                data-bs-target="#editBookModal" data-book-id="${book.id}">
                            Редактировать
                        </button>
                    </div>
                </div>
            </div>

        </c:forEach>
    </div>
</div>

<!-- Редактирование и добавление-->
<div class="modal fade" id="editBookModal" tabindex="-1" aria-labelledby="editBookModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editBookModalLabel"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form method="post" id="editBookForm">
                    <input type="hidden" name="id" id="edit-book-id">
                    <div class="mb-3">
                        <label for="edit-name" class="form-label">Название</label>
                        <input type="text" class="form-control" id="edit-name" name="name" required>
                        <div id="nameErrorLabel" class="invalid-feedback"></div>
                    </div>
                    <div class="mb-3">
                        <label for="authorIds" class="form-label">Авторы</label>
                        <select class="form-select" id="authorIds" name="authors" multiple>
                            <c:forEach items="${authors}" var="author">
                                <option value="${author.id}">${author.name}</option>
                            </c:forEach>
                        </select>
                        <div id="authorsErrorLabel" class="invalid-feedback"></div>
                    </div>
                    <div class="mb-3">
                        <label for="edit-description" class="form-label">Описание</label>
                        <textarea class="form-control" id="edit-description" name="description" rows="3" required></textarea>
                        <div id="descriptionErrorLabel" class="invalid-feedback"></div>
                    </div>
                    <div class="mb-3">
                        <label for="edit-isbn" class="form-label">ISBN</label>
                        <input type="text" class="form-control" id="edit-isbn" name="isbn" required>
                        <div id="isbnErrorLabel" class="invalid-feedback"></div>
                    </div>
                    <div class="mb-3">
                        <label for="edit-pages" class="form-label">Страницы</label>
                        <input type="text" class="form-control" id="edit-pages" name="pages" required>
                        <div id="pagesErrorLabel" class="invalid-feedback"></div>
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
<div class="modal fade" id="deleteBookModal" tabindex="-1" aria-labelledby="deleteBookModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteBookModalLabel">Удалить книгу</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
            </div>
            <div class="modal-body">
                <p>Вы уверены, что хотите удалить книгу?</p>
                <p id="deleteBookName"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                <a href="#" class="btn btn-danger" id="deleteBookButton">Удалить</a>
            </div>
        </div>
    </div>
</div>


<script src="<c:url value='/js/global/jquery-3.7.1.min.js'/>"></script>
<script src="<c:url value='/js/global/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/js/global/tom-select.complete.min.js'/>"></script>
<script src="<c:url value='/js/global/popper.min.js'/>"></script>
<script src="<c:url value='/js/books.js'/>"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="<c:url value='/css/global/tom-select.bootstrap5.css'/>" rel="stylesheet">
    <link href="<c:url value='/css/global/bootstrap.min.css'/>" rel="stylesheet">
    <title>Bookstore - Books</title>
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Book List</h1>
    <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addBookModal">
        Add Book
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
                                data-bs-target="#deleteBookModal${book.id}">
                            Delete
                        </button>
                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                data-bs-target="#editBookModal${book.id}">
                            Edit
                        </button>
                    </div>
                </div>
            </div>

            <!-- Удаление -->
            <div class="modal fade" id="deleteBookModal${book.id}" tabindex="-1" aria-labelledby="deleteBookModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="deleteBookModalLabel">Delete Book</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>Are you sure you want to delete this book?</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <a href="deleteBook?id=${book.id}" class="btn btn-danger">Delete</a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Редактирование -->
            <div class="modal fade" id="editBookModal${book.id}" tabindex="-1" aria-labelledby="editBookModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editBookModalLabel">Edit Book</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="editBook" method="post">
                                <input type="hidden" name="id" value="${book.id}">
                                <button type="submit" class="btn btn-warning">Save Changes</button>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Добавление -->
<div class="modal fade" id="addBookModal" tabindex="-1" aria-labelledby="addBookModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addBookModalLabel">Add Book</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Форма добавления книги -->
                <form action="${pageContext.request.contextPath}/add_book" method="post" accept-charset="UTF-8">
                    <div class="mb-3">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label for="authorIds" class="form-label">Authors</label>
                        <select class="form-select" id="authorIds" name="authorIds" multiple required>
                            <c:forEach items="${authors}" var="author">
                                <option value="${author.id}">${author.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="isbn" class="form-label">ISBN</label>
                        <input type="text" class="form-control" id="isbn" name="isbn" required>
                    </div>
                    <div class="mb-3">
                        <label for="pages" class="form-label">Pages</label>
                        <input type="text" class="form-control" id="pages" name="pages" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Book</button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
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
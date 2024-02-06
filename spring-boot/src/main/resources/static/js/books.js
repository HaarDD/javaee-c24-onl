$(document).ready(function () {
    // TODO: неправильная работа раскрытия панели (раскрывается с пустыми полями фильтрации)
    // Проверка и автоматическое отображение панели фильтрации
    if ($('#searchText').val() || $('input[name="searchType"]:checked').length > 0 ||
        $('#authorSelect').val() || $('#pagesFrom').val() || $('#pagesTo').val()) {
        $('#showSearchBlockButton').click();


    }
});

// Создание селекта для фильтрации по авторам
function createAuthorsFilterTomSelect(selector) {
    return new TomSelect(selector, {
        preload: true,
        // Загрузка через API
        load: function (query, callback) {
            fetch("/api/author/all")
                .then((response) => response.json())
                .then((data) => {
                    if (!data.statusCode) {
                        let json = data.map(function (author) {
                            return {
                                value: author.id,
                                text: author.name
                            };
                        });
                        callback(json);
                    } else {
                        callback();
                    }
                });
        },
        // Перевод надписей
        render: {
            no_results: function (data, escape) {
                return '<div class="no-results">Автор не найден</div>';
            },
        }
    });
}


// Функция для создания селекта TomSelect с возможностью добавления новых авторов
function createAuthorsTomSelect(selector) {
    return new TomSelect(selector, {
        preload: true,
        // Загрузка через API
        load: function (query, callback) {
            fetch("/api/author/all")
                .then((response) => response.json())
                .then((data) => {
                    if (!data.statusCode) {
                        let json = data.map(function (author) {
                            return {
                                value: author.id,
                                text: author.name
                            };
                        });
                        callback(json);
                    } else {
                        callback();
                    }
                });
        },
        // Возможность создания автора
        create: function (input) {
            $.ajax({
                type: 'POST',
                url: '/api/author',
                data: {name: input},
                success: () => {
                    select_authors.load();
                },
                error: function (xhr, status, error) {
                    alert(xhr + status + error);
                }
            });
        },
        // Фильтр создания автора
        createFilter: function (input) {
            input = input.toLowerCase();
            return (!(input in this.options)) && input.length >= 2 && input.length <= 50;
        },
        // Перевод надписей
        render: {
            option_create: function (data, escape) {
                return '<div class="create">Добавить автора: <strong>' + escape(data.input) + '</strong>&hellip;</div>';
            },
            no_results: function (data, escape) {
                return '<div class="no-results">Автор не найден</div>';
            },
        }
    });
}

// Инициализация TomSelect для выбора авторов в филтрации
let select_authors_filter = createAuthorsFilterTomSelect('#authorSelect');
// Инициализация TomSelect для выбора авторов
let select_authors = createAuthorsTomSelect('#authorIds');


// Модальное окно редактирования
let editBookModal = $('#editBookModal');
// Список провалидированных полей
let validatedFields = [];

// Ссылки на разные элементы
const editBookForm = $('#editBookForm');
const editBookModalLabel = $('#editBookModalLabel');
const editBookId = $('#edit-book-id');
const editName = $('#edit-name');
const editDescription = $('#edit-description');
const editIsbn = $('#edit-isbn');
const editPages = $('#edit-pages')

// Открытик модального окна редактирования/добавления
editBookModal.on('show.bs.modal', function (event) {
    // Очистка сообщений об ошибках
    $('.invalid-feedback').text('');

    // Сброс стилей результатов валидации
    validatedFields.forEach(field => field.toggleClass("is-invalid"));
    validatedFields = [];
    // Кнопка, с помощью которой открыто модальное окно (для идентификации редактирования или добавления книги)
    let button = $(event.relatedTarget);
    let bookId = button.data('book-id');
    let isAddForm = !bookId;

    if (isAddForm) {
        // Настройка формы для добавления книги (очистка и нейминг)
        editBookForm[0].action = "/api/book";
        editBookModalLabel.text('Добавление книги: ');
        editBookId.val('');
        editName.val('');
        select_authors.clear();
        editDescription.val('');
        editIsbn.val('');
        editPages.val('');
    } else {
        // Настройка формы для редактирования книги (установка значений книги с подгрузкой из бэка)
        editBookForm[0].action = "/api/book";

        $.get('/api/book/' + bookId, function (response) {
            editBookModalLabel.text('Редактирование книги: ');
            editBookId.val(response.id);
            editName.val(response.name);
            select_authors.setValue(response.authors.map(author => author.id));
            editDescription.val(response.description);
            editIsbn.val(response.isbn);
            editPages.val(response.pages);
        });
    }

    // Обработчик отправки формы
    editBookForm.off('submit').on('submit', function (event) {
        // Отключение стандартного поведения кнопки
        event.preventDefault();
        // Очистка ранее непрошедших валидацию полей
        validatedFields.forEach(field => field.toggleClass("is-invalid"));
        validatedFields = [];
        $.ajax({
            type: isAddForm ? "POST" : "PUT",
            url: isAddForm ? $(this).attr('action') + '?' + $(this).serialize() : $(this).attr('action') + "/" + editBookId.val() + '?' + $(this).serialize(),
            success: function (response) {
                // Перезагрузка страницы в случае 200-го кода
                window.location.reload();
            },
            error: function (xhr, status, error) {
                if (xhr.responseJSON) {
                    // Обработка ошибок валидации
                    $.each(xhr.responseJSON.fieldErrors, function (key, value) {
                        let errorLabel = $('#' + key + 'ErrorLabel');
                        errorLabel.text(value);
                        errorLabel.prev().toggleClass("is-invalid");
                        validatedFields.push(errorLabel.prev());
                    });
                } else {
                    console.error('Ошибка: ', error);
                }
            }
        });
    });
});

// Ссылки на модальное окно и кнопку для удаления
let deleteBookModal = $('#deleteBookModal');
let deleteBookButton = $('#deleteBookButton');

deleteBookModal.on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget);
    let bookId = button.data('book-id');
    $.get('/api/book/' + bookId, function (response) {
        $('#deleteBookName').text(response.name);
    });

    // Обработчик кнопки удаления
    deleteBookButton.off('click').on('click', function () {
        deleteBook(bookId);
    });
});

// Функция удаления книги
function deleteBook(bookId) {
    $.ajax({
        url: '/api/book/' + bookId,
        type: 'DELETE',
        success: function () {
            window.location.reload();
        },
        error: function (error) {
            console.error('Ошибка: ', error);
        }
    });
}
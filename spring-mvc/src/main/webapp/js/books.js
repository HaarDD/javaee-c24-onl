// Функция для создания селекта TomSelect с возможностью добавления новых авторов
function createAuthorsTomSelect(selector) {
    return new TomSelect(selector, {
        create: function (input) {
            return new Promise(function (resolve, reject) {
                $.post('/add_author', { name: input }, function (response) {
                    if (response.success) {
                        // Добавляем нового автора в список
                        select_authors.addOption({ value: response.authorId, text: input });
                        select_authors.refreshOptions();
                        resolve({ value: response.authorId, text: input });
                    } else {
                        // В случае ошибки выводим сообщение и отклоняем обещание
                        alert("Ошибка при добавлении автора: " + response.message);
                        reject();
                    }
                });
            });
        }
    });
}

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
        editBookForm[0].action = "/add_book";
        editBookModalLabel.text('Добавление книги: ');
        editBookId.val('');
        editName.val('');
        select_authors.clear();
        editDescription.val('');
        editIsbn.val('');
        editPages.val('');
    } else {
        // Настройка формы для редактирования книги (установка значений книги с подгрузкой из бэка)
        editBookForm[0].action = "/edit_book";
        $.get('/get_book_info?id=' + bookId, function (response) {
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
            type: "POST",
            url: $(this).attr('action'),
            data: $(this).serialize(),
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
    // Получание имени удаляемой книги с бэка (для надежности :) )
    $.get('/get_book_info?id=' + bookId, function (response) {
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
        url: '/delete_book?id=' + bookId,
        type: 'DELETE',
        success: function () {
            window.location.reload();
        },
        error: function (error) {
            console.error('Ошибка: ', error);
        }
    });
}

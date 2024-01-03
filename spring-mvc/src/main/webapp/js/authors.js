// Модальное окно редактирования
let editBookModal = $('#editBookModal');
// Список провалидированных полей
let validatedFields = [];


// Модальное окно редактирования
let editAuthorModal = $('#editAuthorModal');
// Ссылки на разные элементы
const editAuthorForm = $('#editAuthorForm');
const editAuthorModalLabel = $('#editAuthorModalLabel');
const editAuthorId = $('#edit-author-id');
const editAuthorName = $('#edit-author-name');

// Открытие модального окна редактирования/добавления
editAuthorModal.on('show.bs.modal', function (event) {
    // Очистка сообщений об ошибках
    $('.invalid-feedback').text('');

    // Сброс стилей результатов валидации
    // Сброс стилей результатов валидации
    validatedFields.forEach(field => field.toggleClass("is-invalid"));
    validatedFields = [];

    // Кнопка, с помощью которой открыто модальное окно (для идентификации редактирования или добавления автора)
    let button = $(event.relatedTarget);
    let authorId = button.data('author-id');
    let isAddForm = !authorId;

    if (isAddForm) {
        // Настройка формы для добавления автора (очистка и нейминг)
        editAuthorForm[0].action = "/add_author";
        editAuthorModalLabel.text('Добавление автора: ');
        editAuthorId.val('');
        editAuthorName.val('');
    } else {
        // Настройка формы для редактирования автора (установка значений автора с подгрузкой из бэка)
        editAuthorForm[0].action = "/edit_author";
        $.get('/get_author_info?id=' + authorId, function (response) {
            editAuthorModalLabel.text('Редактирование автора: ');
            editAuthorId.val(response.id);
            editAuthorName.val(response.name);
        });
    }

    // Обработчик отправки формы
    editAuthorForm.off('submit').on('submit', function (event) {
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
let deleteAuthorModal = $('#deleteAuthorModal');
let deleteAuthorButton = $('#deleteAuthorButton');

deleteAuthorModal.on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget);
    let authorId = button.data('author-id');
    // Получание имени удаляемого автора с бэка (для надежности :) )
    $.get('/get_author_info?id=' + authorId, function (response) {
        $('#deleteAuthorName').text(response.name);
    });

    // Обработчик кнопки удаления
    deleteAuthorButton.off('click').on('click', function () {
        deleteAuthor(authorId);
    });
});

// Функция удаления автора
function deleteAuthor(authorId) {
    $.ajax({
        url: '/delete_author?id=' + authorId,
        type: 'DELETE',
        success: function () {
            window.location.reload();
        },
        error: function (error) {
            console.error('Ошибка: ', error);
        }
    });
}

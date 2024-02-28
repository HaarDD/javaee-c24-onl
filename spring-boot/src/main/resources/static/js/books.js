$(document).ready(function () {
    // TODO: неправильная работа раскрытия панели (раскрывается с пустыми полями фильтрации)
    // Проверка и автоматическое отображение панели фильтрации
    if ($('#searchText').val() || $('input[name="searchType"]:checked').length > 0 ||
        $('#authorSelect').val() || $('#pagesFrom').val() || $('#pagesTo').val()) {
        $('#showSearchBlockButton').click();


    }
});

// Инициализация TomSelect для выбора авторов в филтрации
let select_authors_filter = createAuthorsFilterTomSelect('#authorSelect');

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



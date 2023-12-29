let select_authors = new TomSelect('#authorIds', {
    maxItems: 5,
    create: function (input) {
        console.log(input);

        return new Promise(function (resolve, reject) {
            // Отправка запроса на сервер для добавления автора
            $.post('/add_author', {name: input}, function (response) {
                if (response.success) {
                    // Обновление выпадающего списка авторов
                    select_authors.addOption({value: response.authorId, text: input});
                    select_authors.refreshOptions();
                    resolve({value: response.authorId, text: input});
                } else {
                    alert("Ошибка при добавлении автора: " + response.message);
                    reject();  // В случае ошибки, не создавать новый элемент
                }
            });
        });

    }

});
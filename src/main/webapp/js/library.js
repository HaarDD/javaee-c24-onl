function removeBook(bookName) {
    if (confirm('Удалить файл?')) {
        let site = window.location.origin + '/javaee_c24_onl_war_exploded/library/control?book=' + bookName;
        fetch(site, {method: 'DELETE'})
            .then(function (response) {
                if (response.status === 200) {
                    location.reload();
                } else {
                    alert('Не удалось удалить книгу');
                }
            })
            .catch(function (error) {
                console.error('Произошла ошибка:', error);
            });
    }
}
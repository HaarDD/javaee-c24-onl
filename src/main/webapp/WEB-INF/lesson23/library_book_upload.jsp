<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="library-book-upload">
    <label for="show-upload-file-checkbox" class="library-book-form-close"></label>

    <p>Загрузка книги</p>
    <form action="http://localhost:8081/javaee_c24_onl_war_exploded/library/control" method="post"
          enctype="multipart/form-data" class="library-book-form">

        <label class="input-book">
            <input type="file" name="book" onchange="setFileName()">
            <span>Выберите файл</span>
        </label>
        <input type="submit" value="Загрузить книгу" class="input-book-submit"/>
    </form>
    <script>
        const labelFileInput = document.querySelector('.input-book');
        labelFileInput.children[0].addEventListener('change', function () {
            const file = this.files[0];
            labelFileInput.children[1].textContent = file ? file.name : '';
        });
    </script>
</div>


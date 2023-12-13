const site_redirect = window.location.origin + '/javaee_c24_onl_war_exploded/view-request';

const site = window.location.origin + '/javaee_c24_onl_war_exploded/login';

const form = document.querySelector('#client-login-form');
const email = document.querySelector('#email');
const password = document.querySelector('#password');
form.addEventListener('submit', function (event) {
    event.preventDefault();
    event.stopPropagation();

    let formData = {
        'email': email.value,
        'password': password.value,
    };

    fetch(site, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
    })
        .then(response => {
            if (response.status === 200) {
                //window.location.href = site_redirect;
            } else {
                return response;
            }
        })
        .then(response => response.json())
        .then(data => {
            [email, password].forEach(input => {
                input.classList.remove('is-valid', 'is-invalid');
                input.classList.add(data[input.id] ? 'is-valid' : 'is-invalid');
            })
        })
        .catch(function (error) {
            console.log("Сервер не ответил")
        });

}, false)



let select_role_tom_select = new TomSelect('#clientRole', {
    maxItems: 5
});

select_role_tom_select.wrapper.id = 'clientRole';

const site_redirect = window.location.origin + '/javaee_c24_onl_war_exploded/print-request';

const site = window.location.origin + '/javaee_c24_onl_war_exploded/registration';

const form = document.querySelector('#admin-registration-form');

const client_email = document.querySelector('#clientEmail');
const client_firstname = document.querySelector('#clientFirstName');
const client_lastname = document.querySelector('#clientLastName');
const client_password = document.querySelector('#clientPassword');

form.addEventListener('submit', function (event) {
    event.preventDefault();
    event.stopPropagation();

    let formData = {
        'clientEmail': client_email.value,
        'clientFirstname': client_firstname.value,
        'clientLastname': client_lastname.value,
        'clientPassword': client_password.value,
        'clientRole': select_role_tom_select.getValue()
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
                window.location.href = site_redirect;
            } else {
                return response;
            }
        })
        .then(response => response.json())
        .then(data => {
            [client_email, client_firstname, client_lastname, client_password, select_role_tom_select.wrapper].forEach(input => {
                input.classList.remove('is-valid', 'is-invalid');
                input.classList.add(data[input.id] ? 'is-valid' : 'is-invalid');
            })

        })
        .catch(function (error) {
            console.log("Сервер не ответил")
        });

}, false)



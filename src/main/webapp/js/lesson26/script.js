let select_service_tom_select = new TomSelect('#clientService', {
    maxItems: 5
});

select_service_tom_select.wrapper.id = 'clientService';

let select_address_tom_select = new TomSelect('#clientAddress', {
    create: false,
    valueField: "display_name",
    labelField: "display_name",
    searchField: "display_name",
    load: function (query, callback) {
        fetch("https://nominatim.openstreetmap.org/search?city=Minsk&street=" + query + "&country=Belarus&format=json")
            .then(response => response.json())
            .then(data => {
                let suggestions = data.map(item => ({
                    display_name: item.display_name
                }));
                callback(suggestions);
            });
    }
});

select_address_tom_select.wrapper.id = 'clientAddress';

const site_redirect = window.location.origin + '/javaee_c24_onl_war_exploded/view-request';

const site = window.location.origin + '/javaee_c24_onl_war_exploded/save-request';

const form = document.querySelector('#client-service-form');
const client_first_name = document.querySelector('#clientFirstName');
const client_last_name = document.querySelector('#clientLastName');
const client_personal_data_agree = document.querySelector('#clientPersonalDataAgree');
form.addEventListener('submit', function (event) {
    event.preventDefault();
    event.stopPropagation();

    let formData = {
        'clientFirstName': client_first_name.value,
        'clientLastName': client_last_name.value,
        'clientAddress': select_address_tom_select.getValue(),
        'clientPersonalDataAgree': client_personal_data_agree.checked,
        'clientService': select_service_tom_select.getValue()
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
            [client_first_name, client_last_name, select_address_tom_select.wrapper, client_personal_data_agree, select_service_tom_select.wrapper].forEach(input => {
                input.classList.remove('is-valid', 'is-invalid');
                input.classList.add(data[input.id] ? 'is-valid' : 'is-invalid');
            })

        })
        .catch(function (error) {
            console.log("Сервер не ответил")
        });

}, false)



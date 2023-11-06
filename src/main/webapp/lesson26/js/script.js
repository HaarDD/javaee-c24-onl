let select_service_tom_select = new TomSelect('#client-service', {
    maxItems: 5
});

select_service_tom_select.wrapper.id = 'client-service';

let select_address_tom_select = new TomSelect('#client-address', {
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

select_address_tom_select.wrapper.id = 'client-address';


const site_redirect = window.location.origin + '/javaee_c24_onl_war_exploded/repair-request';

const site = window.location.origin + '/javaee_c24_onl_war_exploded/save-request';

const form = document.querySelector('#client-service-form');
const client_first_name = document.querySelector('#client-first-name');
const client_last_name = document.querySelector('#client-last-name');
const client_personal_data_agree = document.querySelector('#client-personal-data-agree');
form.addEventListener('submit', function (event) {
    event.preventDefault();
    event.stopPropagation();

    let formData = {
        'client-first-name': client_first_name.value,
        'client-last-name': client_last_name.value,
        'client-address': select_address_tom_select.getValue(),
        'client-personal-data-agree': client_personal_data_agree.checked,
        'client-service': select_service_tom_select.getValue()
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



window.addEventListener('load', (event) => {
    attachEvents();
});

function attachEvents() {
    attachChangeToOpenTimeInput();
    attachChangeToCloseTimeInput();
    attachChangeToReservationDeadLineInput();
}

function attachChangeToOpenTimeInput() {
    $('#open-time').on("change", function (e) {
        let newValue = e.target.value;
        ajaxToUpdateOpenTime(newValue).then(handleUpdateOpenTime);
    })
}

function ajaxToUpdateOpenTime(newValue) {
    return $.ajax({
        url: '/business/b-bakery-config/open/' + newValue,
        type: 'put'
    });
}

function handleUpdateOpenTime(result) {
    if (success(result)) {

    }
}

function attachChangeToCloseTimeInput() {
    $('#close-time').on("change", function (e) {
        let newValue = e.target.value;
        ajaxToUpdateCloseTime(newValue).then(handleUpdateCloseTime);
    })
}

function ajaxToUpdateCloseTime(newValue) {
    return $.ajax({
        url: '/business/b-bakery-config/close/' + newValue,
        type: 'put'
    });
}

function handleUpdateCloseTime(result) {
    if (success(result)) {

    }
}

function attachChangeToReservationDeadLineInput() {
    $('#reservation-dead-line').on("change", function (e) {
        let newValue = e.target.value;
        ajaxToUpdateReservationDeadLine(newValue).then(handleUpdateReservationDeadLine);
    })
}

function ajaxToUpdateReservationDeadLine(newValue) {
    return $.ajax({
        url: '/business/b-bakery-config/reservationDeadLine/' + newValue,
        type: 'put'
    });
}

function handleUpdateReservationDeadLine(result) {
    if (success(result)) {

    }
}
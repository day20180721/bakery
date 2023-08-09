window.addEventListener('load', (event) => {
    attachEvents();
});

function attachEvents() {
    attachClickEventToEditOrderStatusBtn();
    attachClickEventToOrderStatusDropdownOption();
}

function attachClickEventToEditOrderStatusBtn() {
    $(".edit-order-status-btn").on("click", function (event) {
        let destination = getOrderRowDropdownPositionFromEditOrderStatusBtn(this);
        moveOrderStatusDropdownTo(destination);
    })
}

function getOrderRowDropdownPositionFromEditOrderStatusBtn(btn) {
    return getOrderRowFromRowChild(btn).find(".order-status-dropdown-container");
}
function getOrderRowFromRowChild(child){
    return $(child).parents(".order-row");
}
function moveOrderStatusDropdownTo(destination) {
    $("#order-status-dropdown").appendTo(destination);
}

function disableOrderStatusDropdown() {
    $("#order-status-dropdown").appendTo($("body"));
}

function attachClickEventToOrderStatusDropdownOption() {
    $(".order-status-option").on("click", function (event) {
        showOrderRowStatusByOption(this);
        ajaxToUpdateOrderStatusByOption(this).then(handleUpdateOrderStatus);
    })
}
function showOrderRowStatusByOption(option) {
    let row = getOrderRowFromRowChild(option);
    let selectedOrderStatus = getSelectedOrderStatusFromOption(option);
    showOrderRowStatus(row, selectedOrderStatus);
}
function getSelectedOrderStatusFromOption(option) {
    let orderStatusId = getOrderDropDownRowFromRowChild(option).find(".order-status-id").val();
    let orderStatusName = getOrderDropDownRowFromRowChild(option).find(".order-status-name").text();
    return {
        "id": orderStatusId,
        "name": orderStatusName
    };
}
function getOrderDropDownRowFromRowChild(child){
    return $(child).parents(".order-status-dropdown-row");
}
function ajaxToUpdateOrderStatusByOption(option) {
    let updateOrderStatusTO = getOrderTOFromOption(option)
    return $.ajax({
        url: '/business/b-order/updateOrderStatus',
        type: 'post',
        dataType: 'json', // 預期從server接收的資料型態
        contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
        data: JSON.stringify(updateOrderStatusTO)
    });
}
function getOrderTOFromOption(option){
    let order = getOrderFromRowChild(option);
    let selectedOrderStatus = getSelectedOrderStatusFromOption(option);
    return  {
        "orderId": order.orderId,
        "orderStatusId": selectedOrderStatus.id
    }
}
function getOrderFromRowChild(child) {
    let row = getOrderRowFromRowChild(child);
    return getOrderFromRow(row);

}
function getOrderFromRow(row){
    let orderId = row.find(".order-id").val();
    return {
        orderId
    };
}


function showOrderRowStatus(orderRow, selectedOrderStatus) {
    $(orderRow).find(".order-status").text(selectedOrderStatus.name);
}

function handleUpdateOrderStatus(result) {
    if (success(result)) {
        disableOrderStatusDropdown();
    }
}
import {getSelectedDate} from "/js/calendar.js";

window.addEventListener('load', (event) => {
    attachEvents();
});

function attachEvents() {
    attachClickEventToSubmitBtn();
    attachChangeEventToCount();
    attachClickEventToRemoveBtn();
}

function attachClickEventToSubmitBtn() {
    $("#submit-btn").on("click", function (event) {
        let today = getSelectedDate();
        let datas = getCartItemListFromRowList();
        let comment = $("#comment").val();
        let hour = $("#hour").val();
        let minute = $("#minute").val();
        let TO = {"items": datas, "note": comment, "hour": hour, "minute": minute}
        $.ajax({
            url: '/customer/order/' + today,
            type: 'post',
            dataType: 'json', // 預期從server接收的資料型態
            contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
            data: JSON.stringify(TO),
            success: function (result) {
                if (success(result)) {
                    console.log("200");
                }
            },
        });
    });
}

function getCartItemListFromRowList() {
    let datas = [];
    $(".cart-item-row").each(function (index, row) {
        datas.push(getCartItemFromRow(row));
    })
    return datas;
}

function getCartItemFromRow(row) {
    let id = $(row).find(".cart-item-id").val();
    let productId = $(row).find(".cart-item-product-id").val();
    let name = $(row).find(".cart-item-name").text();
    let price = $(row).find(".cart-item-price").text();
    let count = $(row).find(".cart-item-count").val();
    let totalPrice = $(row).find(".cart-item-total-price").text();
    let description = $(row).find(".cart-item-description").text();
    let cartId = $(row).find(".cart-item-cart-id").val();
    return {
        id, productId, name, price, count, totalPrice, description, cartId
    }
}

function attachChangeEventToCount() {
    $(".cart-item-count").on("change", function (event) {
        let selectedDate = getSelectedDate();
        let cartItem = getCartItemFromRowChild(this);

        $.ajax({
            url: '/customer/cart/' + selectedDate,
            type: 'put',
            dataType: 'json', // 預期從server接收的資料型態
            contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
            data: JSON.stringify(cartItem),
            success: function (result) {

            },
        });
    })
}

function getCartItemFromRowChild(child) {
    let row = getCartItemRowFromChild(child);
    return getCartItemFromRow(row);
}

function getCartItemRowFromChild(child) {
    return $(child).parents(".cart-item-row");
}

function attachClickEventToRemoveBtn() {
    $(".cart-item-remove-btn").on("click", function (event) {
        let cartItem = getCartItemFromRowChild(this);
        $.ajax({
            url: '/customer/cart/',
            type: 'delete',
            dataType: 'json', // 預期從server接收的資料型態
            contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
            data: JSON.stringify(cartItem),
            success: function (result) {
                if (success(result)) {
                    console.log("200");
                }
            },
        });
    })
}



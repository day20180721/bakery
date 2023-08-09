import {getSelectedDate} from "/js/calendar.js";

window.addEventListener('load', (event) => {
    attachEvents();
});

function attachEvents() {
    attachClickEventToAddProductBtn();
}

function attachClickEventToAddProductBtn() {
    $(".add-product-to-cart-btn").on("click", function (event) {
        let today = getSelectedDate();
        let data = getProductFromRowChild(this)

        $.ajax({
            url: '/customer/cart/' + today,
            type: 'post',
            dataType: 'json', // 預期從server接收的資料型態
            contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
            data: JSON.stringify(data),
            success: function (result) {
                console.log(result);
                if (success(result)) {
                    console.log("200");
                }
            }, error: function (result) {
                goToUserAuthPage();
            },
        });
    })
}

function getProductFromRowChild(child) {
    let row = getProductRowFromChild(child);
    return getProductFromRow(row);
}

function getProductRowFromChild(child) {
    return $(child).parents(".product-row");
}

function getProductFromRow(row) {
    let productId = $(row).find(".product-product-id").val();
    let imageUrl = $(row).find(".product-image-url").attr("src");
    let name = $(row).find(".product-name").text();
    let price = $(row).find(".product-price").text();
    let remaining = $(row).find(".product-remaining").text();
    let count = $(row).find(".product-count").val();
    let description = $(row).find(".product-description").val();
    return {
        productId, imageUrl, name, price, remaining, count, description
    }
}



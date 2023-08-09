import '/js/sortable.js';
import '/js/jquery-sortable.js';
import {getSelectedDate} from "/js/calendar.js";

window.addEventListener('load', (event) => {
    attachEvents();
});

function attachEvents() {
    attachSortableToOnSale();
    attachSortableToNotOnSale();
    attachClickEventToEditOnSaleProductBtn();
    attachClickEventToEditNotOnSaleProductBtn();
    attachClickEventToModalSubmitBtn();
    attachClickEventToSubmitBtn();
}

function attachSortableToOnSale() {
    $('#on-sale-product-list').sortable({
        group: 'product',
        animation: 150
    });
}

function attachSortableToNotOnSale() {
    $('#not-on-sale-product-list').sortable({
        group: 'product',
        animation: 150
    });
}

let editedRow = null;

function attachClickEventToEditOnSaleProductBtn() {
    $(".edit-on-sale-product-btn").on("click", function (event) {
        showModalByOnSaleProduct(getOnSaleProductFromRowChild(this));
        editedRow = getOnSaleProductRowFromRowChild(this);
    })
}

function showModalByOnSaleProduct(onSaleProduct) {
    $("#on-sale-product-price").val(onSaleProduct.price);
    $("#on-sale-product-new-price").val(onSaleProduct.newPrice);
    if (onSaleProduct.stock) {
        $("#on-sale-product-stock").val(onSaleProduct.stock);
    } else {
        $("#on-sale-product-stock").val(1);
    }
    if (onSaleProduct.reservation) {
        $("#on-sale-product-reservation").val(onSaleProduct.reservation);
    } else {
        $("#on-sale-product-reservation").val(0);
    }
}


function getOnSaleProductFromRowChild(child) {
    let row = getOnSaleProductRowFromRowChild(child);
    return getOnSaleProductFromRow(row);
}

function getOnSaleProductRowFromRowChild(child) {
    return $(child).parents(".on-sale-product-row");
}


function getOnSaleProductFromRow(row) {
    let productOnSaleId = $(row).find(".on-sale-product-id").val();
    let productId = $(row).find(".on-sale-product-product-id").val();
    let name = $(row).find(".on-sale-product-name").text();
    let stock = $(row).find(".on-sale-product-stock").text();
    let reservation = $(row).find(".on-sale-product-reservation").text();
    let price = $(row).find(".on-sale-product-price").text();
    let newPrice = $(row).find(".on-sale-product-new-price").text();
    let description = $(row).find(".on-sale-product-description").text();
    return {
        productOnSaleId,
        productId,
        name,
        stock,
        reservation,
        price,
        newPrice,
        description
    }
}

function attachClickEventToEditNotOnSaleProductBtn() {
    $(".edit-not-on-sale-product-btn").on("click", function (event) {
        showModalByNotOnSaleProduct(getNotOnSaleProductFromRowChild(this));
        editedRow = getNotOnSaleProductRowFromRowChild(this);
    })
}

function getNotOnSaleProductFromRowChild(child) {
    let row = getNotOnSaleProductRowFromRowChild(child);
    return getNotOnSaleProductFromRow(row);
}

function getNotOnSaleProductRowFromRowChild(child) {
    return $(child).parents(".not-on-sale-product-row");
}

function getNotOnSaleProductFromRow(row) {
    let name = $(row).find(".not-on-sale-product-name").text();
    let productId = $(row).find(".not-on-sale-product-product-id").val();
    let stock = $(row).find(".not-on-sale-product-stock").text();
    let reservation = $(row).find(".not-on-sale-product-reservation").text();
    let description = $(row).find(".not-on-sale-product-description").text();
    let newPrice = $(row).find(".not-on-sale-product-new-price").text();
    let price = $(row).find(".not-on-sale-product-price").text();
    return {
        productId,
        name,
        stock,
        reservation,
        description,
        newPrice,
        price
    }
}

function showModalByNotOnSaleProduct(notOnSaleProduct) {
    $("#on-sale-product-price").val(notOnSaleProduct.price);
    $("#on-sale-product-new-price").val(notOnSaleProduct.newPrice);
    if (notOnSaleProduct.stock) {
        $("#on-sale-product-stock").val(notOnSaleProduct.stock);
    } else {
        $("#on-sale-product-stock").val(1);
    }
    if (notOnSaleProduct.reservation) {
        $("#on-sale-product-reservation").val(notOnSaleProduct.reservation);
    } else {
        $("#on-sale-product-reservation").val(0);
    }
}

function attachClickEventToModalSubmitBtn() {
    $("#modal-submit").on("click", function (event) {
        setEditedProductByModal();
        editedRow = null;
        $('#on-sale-product-modal').modal('hide');
    })
}

function setEditedProductByModal() {
    let newPrice = $("#on-sale-product-new-price").val();
    let stock = $("#on-sale-product-stock").val();
    let reservation = $("#on-sale-product-reservation").val();
    let modification = {
        newPrice,
        stock,
        reservation
    }
    if (isEditRowOriginallyOnSaleRow()) {
        setOnSaleProductRow(editedRow, modification);
    } else {
        setNotOnSaleProductRow(editedRow, modification);
    }
}

function isEditRowOriginallyOnSaleRow() {
    return $(editedRow).hasClass("on-sale-product-row");
}

function setOnSaleProductRow(row, onSaleProduct) {
    if (onSaleProduct.name) {
        $(row).find(".on-sale-product-name").text(onSaleProduct.name);
    }
    if (onSaleProduct.productOnSaleId) {
        $(row).find(".on-sale-product-id").val(onSaleProduct.productOnSaleId);
    }
    if (onSaleProduct.productId) {
        $(row).find(".on-sale-product-product-id").val(onSaleProduct.productId);
    }
    if (onSaleProduct.description) {
        $(row).find(".on-sale-product-description").text(onSaleProduct.description);
    }
    if (onSaleProduct.stock) {
        $(row).find(".on-sale-product-stock").text(onSaleProduct.stock);
    }
    if (onSaleProduct.reservation) {
        $(row).find(".on-sale-product-reservation").text(onSaleProduct.reservation);
    }
    if (onSaleProduct.newPrice) {
        $(row).find(".on-sale-product-new-price").text(onSaleProduct.newPrice);
    }
}

function setNotOnSaleProductRow(row, notOnSaleProduct) {
    if (notOnSaleProduct.name) {
        $(row).find(".not-on-sale-product-name").text(notOnSaleProduct.name);
    }
    if (notOnSaleProduct.productId) {
        $(row).find(".not-on-sale-product-product-id").val(notOnSaleProduct.productId);
    }
    if (notOnSaleProduct.description) {
        $(row).find(".not-on-sale-product-description").text(notOnSaleProduct.description);
    }
    if (notOnSaleProduct.stock) {
        $(row).find(".not-on-sale-product-stock").text(notOnSaleProduct.stock);
    }
    if (notOnSaleProduct.reservation) {
        console.log(notOnSaleProduct.reservation);
        $(row).find(".not-on-sale-product-reservation").text(notOnSaleProduct.reservation);
    }
    if (notOnSaleProduct.newPrice) {
        $(row).find(".not-on-sale-product-new-price").text(notOnSaleProduct.newPrice);
    }
}


function attachClickEventToSubmitBtn() {
    $("#submit-btn").on("click", function () {
        saveOrUpdateBatch();
        removeBatch();
    })
}

function saveOrUpdateBatch() {
    let selectedDate = getSelectedDate();
    let data = getSaveOrUpdateBatchData();
    $.ajax({
        url: '/business/b-shop/' + selectedDate,
        type: 'post',
        dataType: 'json', // 預期從server接收的資料型態
        contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
        data: JSON.stringify(data),
        success: function (result) {
            if (success(result)) {
                console.log("200");
            }
        },
    });
}

function getSaveOrUpdateBatchData() {
    let dataArray = [];
    let onSaleProductList = $("#on-sale-product-list");
    onSaleProductList.children(".on-sale-product-row").each(function (index, row) {
        dataArray.push(getOnSaleProductFromRow(row));
    })
    onSaleProductList.children(".not-on-sale-product-row").each(function (index, row) {
        dataArray.push(getNotOnSaleProductFromRow(row));
    })

    return dataArray;
}


function removeBatch() {
    let selectedDate = getSelectedDate();
    let data = getRemoveBatchData();
    $.ajax({
        url: '/business/b-shop/' + selectedDate,
        type: 'delete',
        dataType: 'json', // 預期從server接收的資料型態
        contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
        data: JSON.stringify(data),
        success: function (result) {
            if (success(result)) {
                console.log("200");
            }
        },
    });
}

function getRemoveBatchData() {
    let dataArray = [];
    $("#not-on-sale-product-list").children(".on-sale-product-row").each(function (index, row) {
        let onSaleProduct = getOnSaleProductFromRow(row);
        dataArray.push(onSaleProduct.productOnSaleId);
    })
    return dataArray;
}




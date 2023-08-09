// https://github.com/SortableJS/Sortable#options
import '/js/sortable.js';
import '/js/jquery-sortable.js';

window.addEventListener('load', (event) => {
    attachEvents();
});

function attachEvents() {
    attachSortableToCategoryList();
    attachClickToRemoveCategoryBtn();
    attachClickToAddCategoryBtn();
    attachClickToEditCategoryBtn();
    attachClickToSubmitBtn();
}

function attachSortableToCategoryList() {
    $('#category-list').sortable({
        animation: 150,
        onUpdate: function (/**Event*/evt) {
            ajaxToUpdateBatchSortOrder().then(handleUpdateBatchSortOrder);
        }
    });
}

function ajaxToUpdateBatchSortOrder() {
    let data = getCategoryListFromRowList();
    return $.ajax({
        url: '/business/b-category/updateBatchSortOrder',
        type: 'post',
        dataType: 'json', // 預期從server接收的資料型態
        contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
        data: JSON.stringify(data)
    });
}

function handleUpdateBatchSortOrder(result) {
    if (success(result)) {
        console.log("success")
    }
}

function getCategoryListFromRowList() {
    let data = [];
    $(".category-row").each(function (index, row) {
        let categoryDataForSort = getCategoryFromRow(row);
        data.push(categoryDataForSort);
    })
    return data;
}

function getCategoryFromRow(row) {
    let id = $(row).find(".category-id").val();
    let name = $(row).find(".category-name").text();
    return {
        id,
        name
    }
}

function attachClickToAddCategoryBtn() {
    $("#add-category-btn").on("click", function (event) {
        $("#modal-title").text("新增");
        clearModal();
    })
}

function attachClickToEditCategoryBtn() {
    $(".edit-category-btn").on("click", function (event) {
        $("#modal-title").text("編輯");
        let category = getCategoryFromRowChild(this);
        console.log(category);
        showModal(category);
    })
}

function getCategoryFromRowChild(child) {
    let row = getCategoryRowFromChild(child);
    return getCategoryFromRow(row);
}

function getCategoryRowFromChild(child) {
    return $(child).parents(".category-row");
}

function showModal(category) {
    $("#category-id").val(category.id);
    $("#category-name").val(category.name);
}

function attachClickToRemoveCategoryBtn() {
    $(".remove-category-btn").on("click", function (event) {
        ajaxToRemoveByRemoveBtn(this).then(handleRemove)
    });
}

function ajaxToRemoveByRemoveBtn(removeBtn) {
    var category = getCategoryFromRowChild(removeBtn);
    return $.ajax({
        url: '/business/b-category/remove',
        type: 'post',
        dataType: 'json', // 預期從server接收的資料型態
        contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
        data: JSON.stringify(category.id)
    });
}

function handleRemove(result) {
    if (success(result)) {
    }
}

function attachClickToSubmitBtn() {
    $("#category-submit-btn").on("click", function (event) {
        ajaxToSaveOrUpdate().then(handleSaveOrUpdateResult);
    });
}

function ajaxToSaveOrUpdate() {
    var data = getCategoryFromModal()
    return $.ajax({
        url: '/business/b-category/saveOrUpdate',
        type: 'post',
        dataType: 'json', // 預期從server接收的資料型態
        contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
        data: JSON.stringify(data),
    });
}

function getCategoryFromModal() {
    let name = $("#category-name").val();
    let id = $("#category-id").val();
    return {
        id,
        name
    };
}

function handleSaveOrUpdateResult(result) {
    if (success(result)) {
        $('#category-modal').modal('hide');
    }
}


function clearModal() {
    $("#category-name").val("");
    $("#category-id").val("");
}

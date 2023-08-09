window.addEventListener('load', (event) => {
    attachEvents();
});
function attachEvents() {
    attachClickEventToAddProductBtn();
    attachChangeEventToProductImageFile();
    attachClickEventToSubmitBtn();
    attachClickEventToEditBtn();
    attachClickEventToRemoveBtn();
    attachClickEventToCategoryBtn();
    attachKeyupEventToCategoryFilter();
}

function attachClickEventToAddProductBtn() {
    $("#add-product-btn").on("click", function (event) {
        closeError();
        clearModal();
        setModalTitle("新增");
    })
}
function clearModal() {
    $("#product-id").val("");
    $("#product-name").val("");
    $("#product-price").val("");
    $("#product-description").val("");
    $("#product-image-file").val("");
    $("#category-panel-trigger").text("無");
    $("#chosen-category").val("");

    clearPreview();
}
function clearPreview() {
    // 設置P標籤為預設提示
    const previewText = $("#preview-text");
    previewText.text('');
    // 隱藏圖片，不能使用src = null，會變成請求/null
    const image = $("#preview-img").get(0);
    image.style.display = 'none';
}
function setModalTitle(name){
    $("#modal-title").text(name);
}
function attachChangeEventToProductImageFile() {
    $("#product-image-file").on("change", function (event) {
        const input = $("#product-image-file").get(0);
        const curFiles = input.files;
        let inputFile = curFiles[0];
        showFileSizePreviewTextFromUploadedFile(inputFile);
        showPreviewImageSrcFromUploadedFile(inputFile);
    });
}
function showFileSizePreviewTextFromUploadedFile(file) {
    const previewText = $("#preview-text");
    previewText.text(`File name ${file.name}, file size ${returnFileSize(file.size)}.`);
}
function returnFileSize(number) {
    if (number < 1024) {
        return `${number} bytes`;
    } else if (number >= 1024 && number < 1048576) {
        return `${(number / 1024).toFixed(1)} KB`;
    } else if (number >= 1048576) {
        return `${(number / 1048576).toFixed(1)} MB`;
    }
}
function showPreviewImageSrcFromUploadedFile(file) {
    const image = $("#preview-img");
    image.get(0).src = URL.createObjectURL(file);
    image.get(0).style.display = 'block';
}
function attachClickEventToSubmitBtn() {
    $("#submit-btn").on("click", function (event) {
        submit();
    });
}
function submit() {
    disabledSubmitBtn();
    uploadFile().then(handleUploadFileResult);
    enableSubmitBtn();
}
function disabledSubmitBtn() {
    $("#submit-btn").prop("disabled", true);
}
function uploadFile() {
    let data = getUploadedImageFormDataFromModal();
    return $.ajax({
        type: "POST",               //使用POST傳輸,檔案上傳只能用POST
        enctype: 'multipart/form-data', //將資料加密傳輸 檔案上傳一定要有的屬性
        url: "/file/upload/single", //要傳輸對應的接口
        data: data,         //要傳輸的資料,我們將form 內upload打包成data
        processData: false, //防止jquery將data變成query String
        contentType: false,
        cache: false,  //不做快取
        async: false, //設為同步
        timeout: 10000, //設定傳輸的timeout,時間內沒完成則中斷
    })
}
function getUploadedImageFormDataFromModal(){
    var form = $("#uploaded-image-form").get(0); //取得HTML中第一個form id為UploadForm
    return new FormData(form); //將form內的所有訊息打包成FormData object
}
function handleUploadFileResult(result) {
    showError(result);

    let product = getProductFromModal();
    let uploadedImageName = result.msg;
    product.imageName = uploadedImageName;

    saveOrUpdate(product).then(handleSaveOrUpdateResult);
}
function getProductFromModal() {
    let name = $("#product-name").val();
    let price = $("#product-price").val();
    let description = $("#product-description").val();
    let categoryId = $("#chosen-category").val();
    let productId = $("#product-id").val();
    return {
        "name": name,
        "price": price,
        "description": description,
        "categoryId": categoryId,
        "productId": productId
    };
}
function saveOrUpdate(product) {
    return $.ajax({
        url: '/business/b-product/saveOrUpdate',
        type: 'post',
        dataType: 'json', // 預期從server接收的資料型態
        contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
        data: JSON.stringify(product)
    })
}
function handleSaveOrUpdateResult(result) {
    if (success(result)) {
        $('#product-modal').modal('hide');
    } else if (error(result)) {
        showError(result);
    }
}
function enableSubmitBtn() {
    $("#submit-btn").prop("disabled", false);
}
function attachClickEventToEditBtn() {
    $(".edit-product-btn").on("click", function clickEditProductBtn(event) {
        let product = getProductFromRowChild(this);
        showModal(product);
        setModalTitle("修改")
    })
}
function getProductFromRowChild(child){
    let row = $(child).parents(".product-row");
    return getProductFromRow(row);
}
function getProductFromRow(row){
    let id = row.find(".product-id").val();
    let categoryId = row.find(".product-category-id").val();
    let categoryName = row.find(".product-category-name").val();
    let imageUrl = row.find(".product-image-url").attr("src");
    let name = row.find(".product-name").text();
    let price = row.find(".product-price").text();
    let description = row.find(".product-description").text();
    return {
        id,
        categoryId,
        categoryName,
        imageUrl,
        name,
        price,
        description
    }
}
function showModal(product){
    $("#product-id").val(product.id);
    $("#product-name").val(product.name);
    $("#product-price").val(product.price);
    $("#product-description").val(product.description);
    $("#chosen-category").val(product.categoryId);
    $("#category-panel-trigger").text(product.categoryName);
    if (isStringNullOrEmpty(product.imageUrl)) {
        clearPreview();
    } else {
        showPreviewImageSrcByUrl(product.imageUrl);
    }
}
function showPreviewImageSrcByUrl(imageUrl) {
    // 設置圖片的src，並顯示出來
    const image = $("#preview-img");
    image.get(0).src = imageUrl;
    image.get(0).style.display = 'block';
}
function attachClickEventToRemoveBtn() {
    $(".removeProductBtn").on("click", function (event) {
        var product = getProductFromRowChild(this);
        $.ajax({
            url: '/business/b-product/remove',
            type: 'post',
            dataType: 'json', // 預期從server接收的資料型態
            contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
            data: JSON.stringify(product.id),
            success: function (result) {
                if (success(result)) {
                    console.log("200");
                }
            },
        });
    });
}

function attachClickEventToCategoryBtn(){
    $(".category-option").on("click", function (event) {
        let categoryOption = getCategoryOptionFromRowChild(this);
        showSelectedCategory(categoryOption);
        storeSelectedCategory(categoryOption);
    });
}
function getCategoryOptionFromRowChild(child){
    let row = $(child).parents(".category-option-row");
    return getCategoryOptonFromRow(row);
}
function getCategoryOptonFromRow(row){
    let id = $(row).find(".category-option-id").val();
    let name = $(row).find(".category-option-name").text();
    return {
        id,
        name
    }
}
function showSelectedCategory(categoryOption){
    let categoryPanelTrigger = $("#category-panel-trigger");
    categoryPanelTrigger.text(categoryOption.name);
}
function storeSelectedCategory(categoryOption){
    let chosenCategory = $("#chosen-category");
    chosenCategory.val(categoryOption.id);
}
function attachKeyupEventToCategoryFilter(){
    $("#category-filter").on("keyup", function (event) {
        event.preventDefault();
        var input, filter, a, i;
        input = document.getElementById("category-filter");
        filter = input.value.toUpperCase();
        var div = document.getElementById("category-dropdown");
        a = div.getElementsByClassName("category");
        for (i = 0; i < a.length; i++) {
            var txtValue = a[i].textContent || a[i].innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                a[i].style.display = "";
            } else {
                a[i].style.display = "none";
            }
        }
    });
}














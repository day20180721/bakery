window.addEventListener('load', (event) => {
    closeError();
});

function showError(result) {
    let errorMap = getErrorMap(result);
    errorMap.forEach((key, value) => {
        let fieldName = value;
        let errorMsg = key;
        let errorFiled = $("#" + fieldName + "-error");
        console.log("ErrorField = " + fieldName + "-" + "ErrorMsg = " + errorMsg);
        errorFiled.show();
        errorFiled.text(errorMsg);
    })
}

function getErrorMap(result) {
    if (!result.body) return new Map();
    return new Map(Object.entries(result.body));
}

function closeError() {
    $(".error-alert").hide();
}

function success(result) {
    if (result.code == 200) {
        if (result.msg) {
            alert(result.msg);
        } else {
            alert("操作成功");
        }
        refresh();
    }
    return result.code == 200;
}


function error(result) {
    if (result.code == 404) {
        if (result.msg) {
            alert(result.msg);
        } else {
            alert("操作失敗");
        }
    }
    return result.code == 404;
}

function refresh() {
    window.location.reload();
}


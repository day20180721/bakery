// window.addEventListener('load', (event) => {
//     attachEvents();
// });
//
// function attachEvents() {
//     attachClickEventToSubmitBtn();
// }
//
// function attachClickEventToSubmitBtn() {
//     $("#root-user-submit-btn").on("click",function (event){
//         ajaxToRootLogin();
//     })
// }
// function getRootUserFromModal(){
//     let account =  $("#root-user-account").val();
//     let password = $("#root-user-password").val();
//     return {
//         account,
//         password
//     }
// }
// function ajaxToRootLogin(){
//     let rootUserTO = getRootUserFromModal();
//     $.ajax({
//         url: '/oauth/root/',
//         type: 'post',
//         dataType: 'json', // 預期從server接收的資料型態
//         contentType: 'application/json; charset=utf-8', // 要送到server的資料型態
//         data: JSON.stringify(rootUserTO),
//         success: function (result) {
//             if (success(result)) {
//                 console.log("200");
//             }
//         },
//     });
// }

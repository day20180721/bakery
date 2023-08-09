const numberControl = {
    minValue: "",
    maxValue: "",
    init: function () {
        numberControl.bindAllDataFromDataAttribute();
        numberControl.renderHtmlStructure();
        numberControl.renderHtmlDates();
    },
    bindAllDataFromDataAttribute:function (){
        let numberSelector = $(".number-selector");
        numberControl.minValue = numberSelector.data("min");
        numberControl.maxValue = numberSelector.data("max");
    },
    renderHtmlStructure: function () {
        document.querySelector(
            ".number-selector"
        ).innerHTML += `<select class="form-control"></select>`;
    },

    renderHtmlDates: function () {
        // Reset
        document.querySelector(".calendar .calendar-body").innerHTML = "";
    },
};
numberControl.init();

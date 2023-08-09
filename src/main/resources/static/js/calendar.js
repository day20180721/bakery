export let calendar = null;
const highlightDates = null;
const defaultDate = null;
export const calendarControl = {
    today: new Date(),
    calWeekDays: ["週日", "週一", "週二", "週三", "週四", "週五", "週六"],
    calMonthNameExtension: "月",
    calMonthName: [
        "一",
        "二",
        "三",
        "四",
        "五",
        "六",
        "七",
        "八",
        "九",
        "十",
        "十一",
        "十二"
    ],
    requestDateToServerUrl: "",
    requestHighlightToServerUrl: "",

    init: function () {
        calendarControl.bindAllDataFromDataAttribute();
        calendarControl.renderHtmlStructure();
        calendarControl.renderHtmlDates();
        calendarControl.attachEvents();
        //

    },
    bindAllDataFromDataAttribute: function () {
        calendarControl.highlightDates = $(".calendar").data("highlight");
        calendarControl.defaultDate = $(".calendar").data("selected-date");
        if (calendarControl.defaultDate) {
            const defaultDate = calendarControl.defaultDate.split('-');
            calendar = new Date(defaultDate[0], defaultDate[1] - 1, defaultDate[2]);

            calendarControl.defaultDate = new Date(defaultDate[0], defaultDate[1] - 1, defaultDate[2]);
        } else {
            calendar = new Date();
        }
        calendarControl.requestDateToServerUrl = $(".calendar").data("request-date-url");
        calendarControl.requestHighlightToServerUrl = $(".calendar").data("request-highlight-url");
    },

    daysInMonth: function (month, year) {
        return new Date(year, month, 0).getDate();
    },
    firstDay: function () {
        return new Date(calendar.getFullYear(), calendar.getMonth(), 1);
    },
    lastDay: function () {
        return new Date(calendar.getFullYear(), calendar.getMonth() + 1, 0);
    },
    firstDayNumber: function () {
        return calendarControl.firstDay().getDay() + 1;
    },
    lastDayNumber: function () {
        return calendarControl.lastDay().getDay() + 1;
    },
    getPreviousMonthLastDate: function () {
        let lastDate = new Date(
            calendar.getFullYear(),
            calendar.getMonth(),
            0
        ).getDate();
        return lastDate;
    },

    navigateToPreviousMonth: function () {
        calendar.setMonth(calendar.getMonth() - 1);
        if (calendarControl.requestHighlightToServerUrl) {
            calendarControl.getHighlightFromServer().then(
                function (value) {
                    calendarControl.renderHtmlDates();
                    calendarControl.attachEvents();
                },
                function (value) {

                });
        } else {
            calendarControl.renderHtmlDates();
            calendarControl.attachEvents();
        }
    },
    navigateToNextMonth: function () {
        calendar.setMonth(calendar.getMonth() + 1);
        if (calendarControl.requestHighlightToServerUrl) {
            calendarControl.getHighlightFromServer().then(
                function (value) {
                    calendarControl.renderHtmlDates();
                    calendarControl.attachEvents();
                },
                function (value) {

                });
        } else {
            calendarControl.renderHtmlDates();
            calendarControl.attachEvents();
        }
    },
    navigateToCurrentMonth: function () {
        let currentMonth = calendarControl.today.getMonth();
        let currentYear = calendarControl.today.getFullYear();
        let currentDate = calendarControl.today.getDate();
        calendar.setMonth(currentMonth);
        calendar.setYear(currentYear);
        calendar.setDate(currentDate);

        if (calendarControl.requestHighlightToServerUrl) {
            calendarControl.getHighlightFromServer().then(
                function (value) {
                    calendarControl.renderHtmlDates();
                    calendarControl.attachEvents();
                },
                function (value) {

                });
        } else {
            calendarControl.renderHtmlDates();
            calendarControl.attachEvents();
        }
    },

    getHighlightFromServer: function () {
        let year = calendar.getFullYear();
        // 因為12月時getMonth會是11，所以要+1
        let month = calendar.getMonth() + 1;
        let yearMonth = year + "-" + month;
        let patternedUrl = calendarControl.requestHighlightToServerUrl + "?selectedDate=" + yearMonth
        console.log(patternedUrl);
        return $.ajax({
            url: patternedUrl,
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result.code == 200) {
                    // 請求回傳為List<String>，內容是指定年月的標註日期
                    calendarControl.highlightDates = result.body;
                }else {
                    console.log(result);
                }
            }
        });
    },

    renderHtmlStructure: function () {
        document.querySelector(
            ".calendar"
        ).innerHTML += `<div class="calendar-inner"><div class="calendar-controls">
          <div class="calendar-prev"><a href="#"><svg xmlns="http://www.w3.org/2000/svg" width="128" height="128" viewBox="0 0 128 128"><path fill="#666" d="M88.2 3.8L35.8 56.23 28 64l7.8 7.78 52.4 52.4 9.78-7.76L45.58 64l52.4-52.4z"/></svg></a></div>
          <div class="calendar-year-month">
          <div class="calendar-month-label"></div>
          <div>-</div>
          <div class="calendar-year-label"></div>
          </div>
          <div class="calendar-next"><a href="#"><svg xmlns="http://www.w3.org/2000/svg" width="128" height="128" viewBox="0 0 128 128"><path fill="#666" d="M38.8 124.2l52.4-52.42L99 64l-7.77-7.78-52.4-52.4-9.8 7.77L81.44 64 29 116.42z"/></svg></a></div>
          </div>
          <div class="calendar-today-date">今日: 
            ${calendarControl.calWeekDays[calendarControl.today.getDay()]}, 
            ${calendarControl.today.getDate()}, 
            ${calendarControl.calMonthName[calendarControl.today.getMonth()]}月
            ${calendarControl.today.getFullYear()}
          </div>
          <div class="calendar-body"></div></div>`;
    },

    renderHtmlDates: function () {
        // Reset
        document.querySelector(".calendar .calendar-body").innerHTML = "";
        // 顯示日期的英文名字，如Sun Mon Tue
        calendarControl.renderDayNames();
        // 顯示月份的英文名字，如Jun-2023的Jun
        calendarControl.renderMonth();
        // 顯示年分，如Jun-2023的2023
        calendarControl.renderYear();
        // 顯示屬於上個月的所有日期
        calendarControl.renderPrevMonthDates();
        // 顯示屬於這個月的所有日期
        calendarControl.renderCurMonthDates();
        // 顯示屬於下個月的所有日期
        calendarControl.renderNextMonthDates();
        //
        calendarControl.highlightToday();
        //
        calendarControl.highlightDefaultDate();
        // HighLight當前月份被指定的日期
        calendarControl.highlightDateThisMonth();
    },
    renderYear: function () {
        let yearLabel = document.querySelector(".calendar .calendar-year-label");
        yearLabel.innerHTML = calendar.getFullYear();
    },
    renderMonth: function () {
        let monthLabel = document.querySelector(
            ".calendar .calendar-month-label"
        );
        monthLabel.innerHTML = calendarControl.calMonthName[calendar.getMonth()] + calendarControl.calMonthNameExtension;
    },
    renderDayNames: function () {
        for (let i = 0; i < calendarControl.calWeekDays.length; i++) {
            document.querySelector(
                ".calendar .calendar-body"
            ).innerHTML += `<div>${calendarControl.calWeekDays[i]}</div>`;
        }
    },
    renderPrevMonthDates: function () {
        let prevMonthLastDate = calendarControl.getPreviousMonthLastDate();
        let prevDateCount = calendarControl.firstDayNumber() - 1;
        // 如果本月的1日不為Sun，則要將上個月最後的日期補完至Sun
        for (let i = 0; i < prevDateCount; i++) {
            document.querySelector(
                ".calendar .calendar-body"
            ).innerHTML += `<div class="prev-dates">${prevMonthLastDate}</div>`;
        }
    },
    renderCurMonthDates: function () {
        // 這個月有幾天
        let calendarDays = calendarControl.daysInMonth(
            calendar.getMonth() + 1,
            calendar.getFullYear()
        );
        let count = 1;
        // 因為日期沒有0日，所以i初始為1，並且calendarDays要+1
        for (let i = 1; i < calendarDays + 1; i++) {
            document.querySelector(
                ".calendar .calendar-body"
            ).innerHTML += `<div class="number-item" data-num=${count}><a class="dateNumber" href="#">${count++}</a></div>`;
        }
        ;
    },
    renderNextMonthDates: function () {
        let nextDateCount = 6 - (calendarControl.lastDayNumber() - 1);
        for (let i = 1; i < nextDateCount + 1; i++) {
            document.querySelector('.calendar-body').innerHTML += `<div class="next-dates">${i}</div>`;
        }
    },

    highlightToday: function () {
        let currentMonth = calendarControl.today.getMonth() + 1;
        let changedMonth = calendar.getMonth() + 1;
        let currentYear = calendarControl.today.getFullYear();
        let changedYear = calendar.getFullYear();
        if (
            currentYear === changedYear &&
            currentMonth === changedMonth &&
            document.querySelectorAll(".number-item")
        ) {
            document
                .querySelectorAll(".number-item")
                [calendarControl.today.getDate() - 1].classList.add("calendar-today");
        }
    },
    highlightDefaultDate: function () {
        if (calendarControl.defaultDate) {

            let defaultMonth = calendarControl.defaultDate.getMonth();
            let currentMonth = calendar.getMonth();
            let defaultYear = calendarControl.defaultDate.getFullYear();
            let currentYear = calendar.getFullYear();

            if (
                defaultYear === currentYear &&
                defaultMonth === currentMonth &&
                document.querySelectorAll(".number-item")
            ) {
                document
                    .querySelectorAll(".number-item")
                    [calendarControl.defaultDate.getDate() - 1].classList.add("calendar-selected-day");
            }
        }
    },
    highlightDateThisMonth: function () {
        if (calendarControl.highlightDates) {
            let dateList = document.querySelectorAll(".number-item");
            if (dateList) {
                for (let i = 0; i < calendarControl.highlightDates.length; i++) {
                    dateList[calendarControl.highlightDates[i] - 1].classList.add("calendar-highlight-day");
                }
            }
        }
    },

    attachEvents: function () {
        calendarControl.attachTodayBtnEvent();
        calendarControl.attachNextPrevBtnEvent();
        calendarControl.attachDateNumberBtnsEvent();
    },
    attachTodayBtnEvent: function () {
        let todayDate = document.querySelector(".calendar .calendar-today-date");
        let currentMonth = calendarControl.today.getMonth() + 1;
        let currentYear = calendarControl.today.getFullYear();
        let currentDate = calendarControl.today.getDate();
        let fullDate = calendarControl.getFullDate(currentYear,currentMonth,currentDate);
        todayDate.addEventListener("click", function (e) {
            calendarControl.setLocationHref(fullDate);
        })
    },
    attachNextPrevBtnEvent: function () {
        let prevBtn = document.querySelector(".calendar .calendar-prev a");
        let nextBtn = document.querySelector(".calendar .calendar-next a");
        prevBtn.addEventListener("click", calendarControl.navigateToPreviousMonth
        );
        nextBtn.addEventListener("click", calendarControl.navigateToNextMonth);
    },
    attachDateNumberBtnsEvent: function () {
        let dateNumber = document.querySelectorAll(".calendar .dateNumber");
        for (let i = 0; i < dateNumber.length; i++) {
            calendarControl.attachDateNumberBtnEvent(dateNumber[i]);
        }
    },
    attachDateNumberBtnEvent: function (dateNumber) {
        let date = calendarControl.getCurrentDateWithDDFormat(dateNumber.textContent);
        let month = calendarControl.getCurrentMonthWithMMFormat();
        let year = calendar.getFullYear();
        let fullDate = calendarControl.getFullDate(year,month,date);
        if (calendarControl.requestDateToServerUrl) {
            dateNumber.addEventListener("click", function () {
                calendarControl.setLocationHref(fullDate);
            }, false);
        }
    },
    getFormatedMonthOrDate: function (number) {
        return number < 10 ? "0" + number : number;
    },
    getCurrentDateWithDDFormat: function (number) {
        return calendarControl.getFormatedMonthOrDate(number);
    },
    getCurrentMonthWithMMFormat: function () {
        var month = calendar.getMonth() + 1;
        return calendarControl.getFormatedMonthOrDate(month);
    },
    setLocationHref: function (date) {
        window.location.href = calendarControl.requestDateToServerUrl + "?selectedDate=" + date;
    },
    getFullDate: function (year, month, date) {
        return year + "-" + month + "-" + date;
    }
};
calendarControl.init();

export function getSelectedDate(){
    return $(".calendar").data("selected-date");
}
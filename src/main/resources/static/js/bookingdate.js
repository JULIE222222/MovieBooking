const reserveDate = document.querySelector(".reserve-date");
const monthYearDisplay = document.getElementById("monthYear");
const monthSelect = document.getElementById("monthSelect");
const weekOfDay = ["일", "월", "화", "수", "목", "금", "토"];
let currentYear = new Date().getFullYear();
let currentMonth = new Date().getMonth();
const today = new Date(); // 현재 날짜

// 월 선택 드롭다운 생성
function populateMonthSelect() {
    monthSelect.innerHTML = "";
    for (let i = 0; i < 12; i++) {
        const option = document.createElement("option");
        option.value = i;
        option.textContent = `${i + 1}월`;
        monthSelect.append(option);
    }
    monthSelect.value = currentMonth;
}

// 월 드롭다운 변경 시 날짜 갱신
function onMonthChange() {
    currentMonth = parseInt(monthSelect.value, 10);
    updateCalendar();
}

monthSelect.addEventListener('change', onMonthChange);

// 날짜와 요일 생성
function generateDatesForMonths(year, month) {
    reserveDate.innerHTML = ""; // 기존 날짜 버튼들을 제거

    // 각 월의 마지막 날 계산
    const lastDay = new Date(year, month + 1, 0).getDate();
    const firstDay = new Date(year, month, 1).getDay();

    // 월 상단에 표시
    monthYearDisplay.innerText = `${year}`;

    // 빈 날짜를 추가하여 요일을 맞춤
    for (let i = 0; i < firstDay; i++) {
        const emptyDiv = document.createElement("div");
        reserveDate.append(emptyDiv);
    }

    // 현재 월의 날짜 생성
    for (let i = 1; i <= lastDay; i++) {
        const date = new Date(year, month, i);

        // 현재 날짜보다 이전일 경우 추가하지 않음
        if (date >= today) {
            const button = document.createElement("button");
            const spanWeekOfDay = document.createElement("span");
            const spanDay = document.createElement("span");

            // class 추가
            button.classList = "movie-date-wrapper";
            spanWeekOfDay.classList = "movie-week-of-day";
            spanDay.classList = "movie-day";

            // 요일 계산
            const dayOfWeek = weekOfDay[date.getDay()];

            // 요일에 따라 스타일 추가
            if (dayOfWeek === "토") {
                spanWeekOfDay.classList.add("saturday");
                spanDay.classList.add("saturday");
            } else if (dayOfWeek === "일") {
                spanWeekOfDay.classList.add("sunday");
                spanDay.classList.add("sunday");
            }
            spanWeekOfDay.innerHTML = dayOfWeek;
            button.append(spanWeekOfDay);

            // 날짜 추가
            spanDay.innerHTML = i;
            button.append(spanDay);

            reserveDate.append(button);

            // 클릭 이벤트 추가
            dayClickEvent(button);
        }
    }
}

function dayClickEvent(button) {
    button.addEventListener("click", function() {
        const movieDateWrapperActive = document.querySelectorAll(".movie-date-wrapper-active");
        movieDateWrapperActive.forEach((list) => {
            list.classList.remove("movie-date-wrapper-active");
        });
        button.classList.add("movie-date-wrapper-active");
    });
}

function updateCalendar() {
    generateDatesForMonths(currentYear, currentMonth);
    // 스크롤을 맨 위로 이동
    reserveDate.scrollTop = 0;
}

// 초기 설정
populateMonthSelect();
updateCalendar();

// 월이 넘어가면 상단에 새로운 달을 표시
function moveToNextMonth() {
    currentMonth++;
    if (currentMonth > 11) {
        currentMonth = 0;
        currentYear++;
    }
    monthSelect.value = currentMonth;
    updateCalendar();
}

function moveToPreviousMonth() {
    currentMonth--;
    if (currentMonth < 0) {
        currentMonth = 11;
        currentYear--;
    }
    monthSelect.value = currentMonth;
    updateCalendar();
}

// 예시: 월 이동 버튼 추가
const prevButton = document.createElement('button');
prevButton.innerText = '이전';
prevButton.addEventListener('click', moveToPreviousMonth);
document.body.insertBefore(prevButton, document.querySelector('.container'));

const nextButton = document.createElement('button');
nextButton.innerText = '다음';
nextButton.addEventListener('click', moveToNextMonth);
document.body.insertBefore(nextButton, document.querySelector('.container').nextSibling);

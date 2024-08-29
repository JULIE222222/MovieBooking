// 전역 변수 및 요소 참조
var s_selbox = [["강남", "분당", "용인"]]; // 강남, 분당, 용인 극장 목록
const reserveDate = document.querySelector(".reserve-date");
const monthYearDisplay = document.getElementById("monthYear");
const monthSelect = document.getElementById("monthSelect");
const weekOfDay = ["일", "월", "화", "수", "목", "금", "토"];
let currentYear = new Date().getFullYear();
let currentMonth = new Date().getMonth();
const today = new Date(); // 현재 날짜

// 영화 선택 후 극장 목록 표시
function selectMovie(movieId) {
    console.log("Selected Movie ID:", movieId); // 선택된 영화의 ID
    resetSelections(); // 이전 선택 초기화
    renderTheaterList(); // 극장 목록 표시
}

// 선택 초기화 (날짜 요소는 초기화하지 않도록 수정)
function resetSelections() {
    // 극장, 시간 목록 초기화 (날짜는 초기화하지 않음)
    const placeList = document.querySelector('.theater-list');
    if (placeList) placeList.innerHTML = '';

    const timeList = document.querySelector('.time-list');
    if (timeList) timeList.innerHTML = '';

    console.log("Selections have been reset.");
}

// 극장 목록 생성
function renderTheaterList() {
    const placeList = document.querySelector('.theater-list');
    placeList.innerHTML = '';

    s_selbox[0].forEach(function (place) {
        const li = document.createElement('li');
        li.textContent = place;
        li.onclick = function () {
            selectTheater(place);
        };
        placeList.appendChild(li);
    });
}

// 극장 선택 후 상영 날짜 표시
function selectTheater(theater) {
    console.log("Selected Theater:", theater);
    updateCalendar(); // 극장 선택 후 달력 유지
}

// 날짜 선택 시 상영 시간 목록 생성
function selectDate(date) {
    console.log("Selected Date:", date);
    renderTimeList(); // 상영 시간 목록 생성
}

// 상영 시간 목록 생성
function renderTimeList() {
    const timeList = document.querySelector('.time-list');
    timeList.innerHTML = '';

    var times = ["10:00", "12:00", "14:00"];
    times.forEach(function (time) {
        var li = document.createElement('li');
        li.textContent = time;
        li.onclick = function () {
            selectTime(time);
        };
        timeList.appendChild(li);
    });
}

// 최종 상영 시간 선택
function selectTime(time) {
    console.log("Selected Time:", time);
    alert("예매가 완료되었습니다!");
}

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

        // 현재 날짜와 이후 날짜만 추가
        if (date >= new Date(today.setHours(0, 0, 0, 0))) {
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
            button.addEventListener("click", function () {
                selectDate(date.toISOString().split('T')[0]); // 날짜 선택 시 호출
                const movieDateWrapperActive = document.querySelectorAll(".movie-date-wrapper-active");
                movieDateWrapperActive.forEach((list) => {
                    list.classList.remove("movie-date-wrapper-active");
                });
                button.classList.add("movie-date-wrapper-active");
            });
        }
    }
}

// 캘린더 업데이트
function updateCalendar() {
    generateDatesForMonths(currentYear, currentMonth);
    reserveDate.scrollTop = 0; // 스크롤 맨 위로 이동
}

// 초기 설정
populateMonthSelect();
updateCalendar();

// 월 이동 버튼 기능 추가
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

// 월 이동 버튼 추가
const prevButton = document.createElement('button');
prevButton.innerText = '이전';
prevButton.addEventListener('click', moveToPreviousMonth);
document.body.insertBefore(prevButton, document.querySelector('.container'));

const nextButton = document.createElement('button');
nextButton.innerText = '다음';
nextButton.addEventListener('click', moveToNextMonth);
document.body.insertBefore(nextButton, document.querySelector('.container').nextSibling);

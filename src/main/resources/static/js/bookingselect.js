// 전역 변수 및 요소 참조
var s_selbox = [["강남", "분당", "용인"]]; // 극장 목록 배열
const reserveDate = document.querySelector(".reserve-date"); // 날짜 버튼을 표시할 요소
const monthYearDisplay = document.getElementById("monthYear"); // 현재 년월을 표시할 요소
const monthSelect = document.getElementById("monthSelect"); // 월 선택 드롭다운 요소
const weekOfDay = ["일", "월", "화", "수", "목", "금", "토"]; // 요일 배열
let currentYear = new Date().getFullYear(); // 현재 연도
let currentMonth = new Date().getMonth(); // 현재 월

// 날짜를 YYYY-MM-DD 형식으로 변환하는 함수
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월을 두 자리로 포맷
    const day = String(date.getDate()).padStart(2, '0'); // 일을 두 자리로 포맷
    return `${year}-${month}-${day}`;
}

// 페이지 로드 시 초기 설정 함수
function init() {
    populateMonthSelect(); // 월 선택 드롭다운 생성
    updateCalendar(); // 초기 달력 생성
    renderTheaterList(); // 페이지 로드 시 극장 목록 자동 렌더링
}

// 영화 선택 후 극장 목록을 유지하도록 수정된 함수
function selectMovie(movieId) {
    console.log("Selected Movie ID:", movieId); // 선택된 영화의 ID를 콘솔에 출력
    resetSelections(); // 이전 선택 상태를 초기화
}

// 선택 초기화 함수 (극장과 시간 목록을 비움)
function resetSelections() {
    // 시간 목록만 초기화 (극장 목록은 유지)
    const timeList = document.querySelector('.time-list');
    if (timeList) timeList.innerHTML = ''; // 시간 목록 비우기

    console.log("Selections have been reset."); // 선택 초기화 완료 메시지
}

// 극장 목록 생성 함수
function renderTheaterList() {
    const placeList = document.querySelector('.theater-list'); // 극장 목록을 표시할 요소 선택
    placeList.innerHTML = ''; // 기존 목록을 비움

    // 's_selbox[0]' 배열의 각 극장 이름에 대해 반복
    s_selbox[0].forEach(function (place) {
        const li = document.createElement('li'); // 새로운 <li> 요소 생성
        li.textContent = place; // 극장 이름을 텍스트로 설정
        li.onclick = function () {
            selectTheater(place); // 클릭 시 selectTheater 함수 호출
        };
        placeList.appendChild(li); // <li> 요소를 극장 목록에 추가
    });
}

// 극장 선택 후 상영 날짜 표시 함수
function selectTheater(theater) {
    console.log("Selected Theater:", theater); // 선택된 극장 이름을 콘솔에 출력
    updateCalendar(); // 극장 선택 후 달력을 업데이트하여 유지
}

// 날짜 선택 시 상영 시간 목록 생성 함수
function selectDate(date) {
    console.log("Selected Date:", formatDate(date)); // 포맷된 날짜를 콘솔에 출력

    // 상영 시간 목록을 표시하기 위해 서버에서 데이터를 가져옵니다.
        fetch(`/getShowTimes?date=${formatDate(date)}`) // 예시 URL (서버에서 날짜에 해당하는 상영 시간 목록을 반환해야 함)
            .then(response => response.json())
            .then(showTimes => {
                renderTimeList(showTimes); // 상영 시간 목록을 렌더링합니다.
            })
            .catch(error => {
                console.error("Error fetching show times:", error);
            });

}

// 상영 시간 목록 생성 함수
function renderTimeList(showTimes) {
    const timeList = document.querySelector('.time-list'); // 상영 시간 목록을 표시할 요소 선택
    timeList.innerHTML = ''; // 기존 목록을 비움
if (showTimes.length === 0) {
        const noShowTimes = document.createElement('li');
        noShowTimes.textContent = "상영 시간이 없습니다.";
        timeList.appendChild(noShowTimes);
        return;
    }

    // 'showTimes' 배열의 각 상영 시간에 대해 반복
    showTimes.forEach(function (showTime) {
        var li = document.createElement('li'); // 새로운 <li> 요소 생성
        var button = document.createElement('button'); // 상영 시간 버튼 생성
        button.textContent = `${showTime.startTime} ~`; // 버튼 텍스트 설정
        button.onclick = function () {
            selectTime(showTime.id, showTime.startTime); // 클릭 시 selectTime 함수 호출
        };

        li.appendChild(button);
        timeList.appendChild(li); // <li> 요소를 상영 시간 목록에 추가
    });
}

// 최종 상영 시간 선택 함수
function selectTime(id, startTime) {
    console.log("Selected Time ID:", id); // 선택된 상영 시간 ID를 콘솔에 출력
    console.log("Selected Time Start Time:", startTime); // 선택된 상영 시간 시작 시간을 콘솔에 출력
    //alert("예매가 완료되었습니다!"); // 예매 완료 메시지 알림
}


// 상영 시간 목록을 표시하기 위해 서버에서 데이터를 가져옵니다.
function fetchShowTimes(date) {
    fetch(`/getShowTimes?date=${formatDate(date)}`) // 예시 URL (서버에서 날짜에 해당하는 상영 시간 목록을 반환해야 함)
        .then(response => {
            if (!response.ok) {
                // 서버 응답 상태가 200 OK가 아닐 경우 오류 처리
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // JSON 응답 파싱
        })
        .then(showTimes => {
            renderTimeList(showTimes); // 상영 시간 목록을 렌더링합니다.
        })
        .catch(error => {
            console.error("Error fetching show times:", error);
        });
}

// 월 선택 드롭다운 생성 함수
function populateMonthSelect() {
    monthSelect.innerHTML = ""; // 드롭다운의 기존 옵션을 제거
    for (let i = 0; i < 12; i++) {
        const option = document.createElement("option"); // 새로운 <option> 요소 생성
        option.value = i; // 월 값 설정
        option.textContent = `${i + 1}월`; // 표시할 월 이름 설정
        monthSelect.append(option); // <option> 요소를 드롭다운에 추가
    }
    monthSelect.value = currentMonth; // 현재 월을 드롭다운에서 선택
}

// 월 드롭다운 변경 시 날짜 갱신 함수
function onMonthChange() {
    currentMonth = parseInt(monthSelect.value, 10); // 선택된 월 값으로 현재 월 업데이트
    updateCalendar(); // 달력 업데이트
}

// 월 선택 드롭다운에 change 이벤트 리스너 추가
monthSelect.addEventListener('change', onMonthChange);

// 날짜와 요일 생성 함수
function generateDatesForMonths(year, month) {
    reserveDate.innerHTML = ""; // 기존 날짜 버튼들을 제거

    // 월의 마지막 날과 첫 번째 날의 요일을 계산
    const lastDay = new Date(year, month + 1, 0).getDate(); // 해당 월의 마지막 날
    const firstDay = new Date(year, month, 1).getDay(); // 해당 월의 첫 번째 날의 요일

    // 월과 연도를 상단에 표시
    monthYearDisplay.innerText = `${year}년 ${month + 1}월`;

    // 요일을 맞추기 위해 빈 날짜 추가
    for (let i = 0; i < firstDay; i++) {
        const emptyDiv = document.createElement("div");
        reserveDate.append(emptyDiv); // 빈 날짜 추가
    }

    // 현재 월의 날짜 버튼 생성
    for (let i = 1; i <= lastDay; i++) {
        const date = new Date(year, month, i); // 날짜 객체 생성
        const todayWithoutTime = new Date(); // 현재 날짜를 복제
        todayWithoutTime.setHours(0, 0, 0, 0); // 복제한 객체의 시간을 자정으로 설정

        // 현재 날짜와 이후 날짜만 버튼으로 추가
        if (date >= todayWithoutTime) {
            const button = document.createElement("button");
            const spanWeekOfDay = document.createElement("span");
            const spanDay = document.createElement("span");

            // 클래스 추가
            button.classList.add("movie-date-wrapper");
            spanWeekOfDay.classList.add("movie-week-of-day");
            spanDay.classList.add("movie-day");

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
            button.addEventListener('click', () => selectDate(date)); // 날짜 선택 시 selectDate 호출
        }
    }
}

// 캘린더 업데이트 함수
function updateCalendar() {
    generateDatesForMonths(currentYear, currentMonth); // 현재 연도와 월에 따라 날짜 생성
    reserveDate.scrollTop = 0; // 스크롤을 상단으로 이동
}

// 초기 설정 함수 호출
document.addEventListener("DOMContentLoaded", init);

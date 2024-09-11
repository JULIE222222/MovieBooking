// 전역 변수 및 요소 참조
const reserveDate = document.querySelector(".reserve-date"); // 날짜 버튼을 표시할 요소
const monthYearDisplay = document.getElementById("monthYear"); // 현재 년월을 표시할 요소
const monthSelect = document.getElementById("monthSelect"); // 월 선택 드롭다운 요소
const weekOfDay = ["일", "월", "화", "수", "목", "금", "토"]; // 요일 배열
let currentYear = new Date().getFullYear(); // 현재 연도
let currentMonth = new Date().getMonth(); // 현재 월
let selectedMovieId = null; // 선택된 영화 ID
let selectedDate = null; // 선택된 날짜

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
}

/*// 영화 포스터를 렌더링하는 함수
function renderPoster(posterData) {
    if (posterData.posterURL) {
        const imageUrl = `/upload/${posterData.posterURL}`;
        console.log('Image URL:', imageUrl); // 디버깅을 위한 로그
        document.getElementById('selected-movie-poster').innerHTML =
            `<img src="${imageUrl}" alt="Movie Poster" style="width: 100%;">`;
    } else {
        console.error('Poster URL not found in response.');
    }
}*/

function renderPoster(posterData) {
    if (posterData && posterData.posterURL) {
        const imageUrl = `/upload/${posterData.posterURL}`;
        console.log('Image URL:', imageUrl); // 디버깅을 위한 로그
        document.getElementById('selected-movie-poster').innerHTML =
            `<img src="${imageUrl}" alt="Movie Poster" class="poster-img">`;
    } else {
        console.error('Poster URL not found in response.');
        document.getElementById('selected-movie-poster').innerHTML = "포스터를 찾을 수 없습니다."; // 오류 메시지 출력
    }
}

// 영화 선택 함수
function selectMovie(element) {
    selectedMovieId = element.getAttribute('data-movie-id'); // movieId를 버튼의 data 속성에서 가져옴
    var movieTitle = element.textContent; // 선택한 영화 제목 가져오기
    var moviePosterUrl = element.getAttribute('data-movie-poster'); // 영화 포스터 URL 가져오기
    console.log("Selected Movie ID:", selectedMovieId); // 선택된 영화의 ID를 콘솔에 출력

    $.ajax({ // jQuery의 ajax 메서드를 사용
        url: '/movie/getPoster', // 서버 URL
        type: 'GET', // 요청 방식
        data: {
            movieId: selectedMovieId // 쿼리 파라미터
        },
        dataType: 'json', // 서버로부터 기대하는 응답 형식
        success: function(posterData) {
            console.log('Poster Data:', posterData); // 데이터 확인
            renderPoster(posterData); // 포스터 렌더링 함수 호출
        },
        error: function(xhr, status, error) {
            console.error("Error fetching movie poster:", error); // 에러 콘솔에 출력
        }
    });

    updateSelectedMovie(movieTitle, moviePosterUrl);
    resetSelections();

    if (selectedDate) {
        fetchShowTimes(selectedDate);
    }
}

// 선택 초기화 함수 (상영 시간 목록을 비움)
function resetSelections() {
    const timeList = document.querySelector('.time-list');
    if (timeList) timeList.innerHTML = ''; // 시간 목록 비우기
    console.log("Selections have been reset."); // 선택 초기화 완료 메시지
}

// 날짜 선택 시 상영 시간 목록 생성 함수
function selectDate(date) {
    console.log("Selected Date:", formatDate(date)); // 포맷된 날짜를 콘솔에 출력
    updateSelectedDate(date);
    selectedDate = date; // 선택된 날짜를 저장
    if (selectedMovieId) {
        fetchShowTimes(date); // 서버에서 상영 시간 목록을 가져옵니다.
    }
}

// 상영 시간 목록을 표시하기 위해 서버에서 데이터를 가져옵니다.
function fetchShowTimes(date) {
    if (!selectedMovieId) {
        alert("영화를 선택해주세요.");
        return;
    }

    $.ajax({
        url: '/showtime/getShowTimes',  // 서버 URL
        type: 'GET',  // HTTP 요청 방식
        data: {  // 쿼리 파라미터
            date: formatDate(date),  // 선택된 날짜
            movieId: selectedMovieId  // 선택된 영화 ID
        },
        dataType: 'json',  // 서버로부터 기대하는 응답 형식
        success: function(showTimes) {
            renderTimeList(showTimes);  // 상영 시간 목록을 렌더링합니다.
        },
        error: function(xhr, status, error) {
            console.error("Error fetching show times:", error);
            alert("상영 시간을 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    });
}

function renderTimeList(groupedShowTimes) {
    const timeList = document.querySelector('.time-list'); // 상영 시간 목록을 표시할 요소 찾기
    timeList.innerHTML = ''; // 기존 목록 비우기

    if (Object.keys(groupedShowTimes).length === 0) { // 상영 시간이 없을 경우
        const noShowTimes = document.createElement('li'); // <li> 요소 생성
        noShowTimes.textContent = "상영 시간이 없습니다."; // 텍스트 설정
        timeList.appendChild(noShowTimes); // 목록에 추가
        return; // 함수 종료
    }

    // 상영관별로 상영 시간을 그룹화하여 표시
    for (const [screenNum, showTimes] of Object.entries(groupedShowTimes)) { // 상영관 번호와 상영 시간들을 반복
        const screenTitle = document.createElement('div'); // <div> 요소 생성
        screenTitle.textContent = `${screenNum} 관`; // 상영관 번호 텍스트 설정
        screenTitle.classList.add('screen-title'); // 제목에 CSS 클래스 추가
        timeList.appendChild(screenTitle); // 상영관 제목 추가

        let row; // 새로운 행을 위한 변수 선언
        showTimes.forEach(function (showTime, index) { // 각 상영 시간에 대해 반복
            if (index % 3 === 0) { // 매 3번째마다 새로운 행을 추가
                row = document.createElement('div'); // 새로운 행 생성
                row.classList.add('row'); // Bootstrap 또는 커스텀 클래스 추가
                timeList.appendChild(row); // 목록에 새로운 행 추가
            }

            const col = document.createElement('div'); // 새로운 열 생성
            col.classList.add('col-4'); // 3개의 버튼을 한 행에 배치하기 위해 col-4 클래스 추가

            const button = document.createElement('button'); // 상영 시간 버튼 생성
            //button.classList.add('btn', 'btn-primary', 'w-100'); // 버튼 스타일 추가, 'w-100'로 버튼의 너비를 100%로 설정

            // 시간을 "시:분" 형식으로 변환
            const [hours, minutes] = showTime.startTime.split(':'); // "시:분:초"를 분리하여 시와 분만 사용
            const formattedTime = `${hours}:${minutes}`; // "시:분" 형식으로 시간 포맷

            button.textContent = formattedTime; // 버튼에 포맷된 상영 시간 텍스트 설정
            button.onclick = function () {
                  selectTime(showTime.showTimeID, showTime.startTime);
            };

            col.appendChild(button); // 버튼을 열에 추가
            row.appendChild(col); // 열을 행에 추가
        });
    }
}

// 최종 상영 시간 선택 함수
function selectTime(showTimeID, startTime) {
    console.log("Selected Time ID:", showTimeID); // 선택된 상영 시간 ID를 콘솔에 출력
    console.log("Selected Time Start Time:", startTime); // 선택된 상영 시간 시작 시간을 콘솔에 출력

    // 'startTime' 변수가 undefined가 아닌지 확인합니다.
       if (startTime) {
           updateSelectedTime(startTime); // startTime이 정의되었을 때만 호출
       } else {
           console.error("Error: startTime is undefined."); // 오류 메시지 출력
       }

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
    monthYearDisplay.innerText = `${year}년`;

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

            // 텍스트 설정
            spanWeekOfDay.innerText = weekOfDay[date.getDay()];
            spanDay.innerText = i;

            button.append(spanWeekOfDay, spanDay);
            button.onclick = function () {
                selectDate(date); // 날짜 버튼 클릭 시 이벤트
            };

            reserveDate.append(button);
        }
    }
}

// 달력 업데이트 함수
function updateCalendar() {
    generateDatesForMonths(currentYear, currentMonth); // 날짜와 요일을 생성하여 달력을 업데이트
}

// 선택된 영화 업데이트 함수
/*function updateSelectedMovie(movieTitle, moviePosterUrl) {
    document.getElementById('selected-movie-title').textContent = `${movieTitle}`;
    document.getElementById('selected-movie-poster').innerHTML =`<img src="${moviePosterUrl}">`;
}*/

// 선택된 영화 업데이트 함수
function updateSelectedMovie(movieTitle, moviePosterUrl) {
    document.getElementById('selected-movie-title').textContent = `${movieTitle}`;
    if (moviePosterUrl) {
        document.getElementById('selected-movie-poster').innerHTML =
            `<img src="${moviePosterUrl}" alt="Movie Poster" style="width: 100px; max-height: 100px;">`;
    } else {
        document.getElementById('selected-movie-poster').innerHTML = "포스터를 찾을 수 없습니다.";
    }
}

// 선택된 날짜 업데이트 함수
function updateSelectedDate(date) {
    document.getElementById('selected-date').textContent = `날짜: ${formatDate(date)}`;
}

// 선택된 시간 업데이트 함수
function updateSelectedTime(startTime) {
     // startTime을 "시:분:초" 형식으로 가정하고, 시와 분만 추출
     const [hours, minutes] = startTime.split(':'); // "시:분" 형식으로 분리
     const formattedTime = `${hours}:${minutes}`; // "시:분" 형식으로 시간 포맷
     document.getElementById('selected-time').textContent = `시간: ${formattedTime}`; // 포맷된 시간으로 업데이트
}

// 선택된 좌석 업데이트 함수
function updateSelectedSeat(seat) {
    document.getElementById('selected-seat').textContent = `좌석: ${seat}`;
}

// 상영 시간 선택 함수
function selectShowTime(showTime) {
    updateSelectedTime(showTime.startTime);
    console.log("Selected ShowTime:", showTime); // 선택된 상영 시간 객체를 콘솔에 출력
}

// 초기화 함수 호출
init();

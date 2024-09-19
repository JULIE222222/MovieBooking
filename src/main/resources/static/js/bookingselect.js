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
    if (!date) {
        console.error("Error: date is null or undefined.");
        return "";
    }
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월을 두 자리로 포맷
    const day = String(date.getDate()).padStart(2, '0'); // 일을 두 자리로 포맷
    return `${year}-${month}-${day}`;
}

// 페이지 로드 시 초기 설정 함수
function init() {
    populateMonthSelect(); // 월 선택 드롭다운 생성
    updateCalendar(); // 초기 달력 생성
    monthSelect.addEventListener('change', onMonthChange); // 드롭다운 변경 이벤트 리스너 추가
}

// 영화 포스터를 렌더링하는 함수
function renderPoster(posterData) {
    if (posterData && posterData.posterURL) {
        const imageUrl = `/upload/${posterData.posterURL}`;
        console.log('Image URL:', imageUrl);

        document.getElementById('selected-movie-poster').innerHTML =
            `<img src="${imageUrl}" alt="Movie Poster" class="poster-img">`;

        document.getElementById('hidden_poster').value = imageUrl;
    } else {
        document.getElementById('selected-movie-poster').innerHTML = "포스터를 찾을 수 없습니다.";
    }
}

// 영화 선택 함수
function selectMovie(element) {
    selectedMovieId = element.getAttribute('data-movie-id');
    const movieTitle = element.textContent;
    const moviePosterUrl = element.getAttribute('data-movie-poster');

    console.log("Selected Movie ID:", selectedMovieId); // 선택된 영화의 ID를 콘솔에 출력

    $.ajax({
        url: '/movie/getPoster',
        type: 'GET',
        data: { movieId: selectedMovieId },
        dataType: 'json',
        success: function(posterData) {
            renderPoster(posterData);
        },
        error: function(xhr, status, error) {
            console.error("Error fetching movie poster:", error);
        }
    });

    updateSelectedMovie(movieTitle, moviePosterUrl);
    resetSelections();

    if (selectedDate) {
        fetchShowTimes(selectedDate);
    } else {
        console.error("Error: selectedDate is null or undefined.");
    }

    // 영화 정보 및 선택된 날짜 설정
    setSelectedInfo(moviePosterUrl, movieTitle, formatDate(selectedDate), '');
}

// 선택 초기화 함수
function resetSelections() {
    const timeList = document.querySelector('.time-list');
    if (timeList) timeList.innerHTML = '';
}

// 날짜 선택 시 상영 시간 목록 생성 함수
function selectDate(date) {
    console.log("Selected Date:", formatDate(date));
    if (!date) {
        console.error("Error: date is null or undefined.");
        return;
    }
    updateSelectedDate(date);
    selectedDate = date;
    if (selectedMovieId) {
        fetchShowTimes(date);
    } else {
        console.error("Error: selectedMovieId is null or undefined.");
    }

    // 날짜 설정
    setSelectedInfo(
        document.getElementById("hidden_poster").value,
        document.getElementById("hidden_title").value,
        formatDate(date),
        document.getElementById("hidden_time").value
    );
}

// 상영 시간 목록을 표시하기 위해 서버에서 데이터를 가져옵니다.
function fetchShowTimes(date) {
    if (!selectedMovieId) {
        alert("영화를 선택해주세요.");
        return;
    }

    $.ajax({
        url: '/showtime/getShowTimes',
        type: 'GET',
        data: { date: formatDate(date), movieId: selectedMovieId },
        dataType: 'json',
        success: function(showTimes) {
            renderTimeList(showTimes);
        },
        error: function(xhr, status, error) {
            console.error("Error fetching show times:", error);
        }
    });
}

// 상영 시간 목록을 렌더링하는 함수
function renderTimeList(groupedShowTimes) {
    const timeList = document.querySelector('.time-list');
    timeList.innerHTML = '';

    if (Object.keys(groupedShowTimes).length === 0) {
        const noShowTimes = document.createElement('li');
        noShowTimes.textContent = "상영 시간이 없습니다.";
        timeList.appendChild(noShowTimes);
        return;
    }

    for (const [screenNum, showTimes] of Object.entries(groupedShowTimes)) {
        const screenTitle = document.createElement('div');
        screenTitle.textContent = `${screenNum} 관`;
        screenTitle.classList.add('screen-title');
        timeList.appendChild(screenTitle);

        let row;
        showTimes.forEach(function (showTime, index) {
            if (index % 3 === 0) {
                row = document.createElement('div');
                row.classList.add('row');
                timeList.appendChild(row);
            }

            const col = document.createElement('div');
            col.classList.add('col-4');

            const button = document.createElement('button');
            const [hours, minutes] = showTime.startTime.split(':');
            const formattedTime = `${hours}:${minutes}`;

            button.textContent = formattedTime;
            button.onclick = function () {
                selectTime(showTime.showTimeID, showTime.startTime);
            };

            col.appendChild(button);
            row.appendChild(col);
        });
    }
}

// 최종 상영 시간 선택 함수
function selectTime(showTimeID, startTime) {
    if (startTime) {
        updateSelectedTime(startTime);

        // 상영 시간 로그 출력
        console.log("Selected time:", startTime);
        // 상영 시간 설정
        setSelectedInfo(
            document.getElementById("hidden_poster").value,
            document.getElementById("hidden_title").value,
            document.getElementById("hidden_date").value,
            startTime
        );
    } else {
        console.error("Error: startTime is undefined.");
    }
}

// 월 선택 드롭다운 생성 함수
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

// 월 드롭다운 변경 시 날짜 갱신 함수
function onMonthChange() {
    currentMonth = parseInt(monthSelect.value);
    updateCalendar();
}

// 달력 갱신 함수
function updateCalendar() {
    monthYearDisplay.textContent = `${currentYear}년`;

    reserveDate.innerHTML = "";

    const currentDate = new Date(currentYear, currentMonth);
    const firstDayIndex = currentDate.getDay();
    const lastDate = new Date(currentYear, currentMonth + 1, 0).getDate();

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    for (let i = 0; i < firstDayIndex; i++) {
        const emptyCell = document.createElement("div");
        reserveDate.appendChild(emptyCell);
    }

    for (let date = 1; date <= lastDate; date++) {
        const dateButton = document.createElement("button");
        const buttonDate = new Date(currentYear, currentMonth, date);
        if (buttonDate >= today) {
            dateButton.textContent = `${date}일 (${weekOfDay[(firstDayIndex + date - 1) % 7]})`;
            dateButton.onclick = () => selectDate(buttonDate);
            reserveDate.appendChild(dateButton);
        }
    }
}

// 영화 제목과 포스터를 업데이트하는 함수
function updateSelectedMovie(movieTitle, moviePosterUrl) {
    document.getElementById('selected-movie-title').textContent = `${movieTitle}`;
    if (moviePosterUrl) {
        document.getElementById('selected-movie-poster').innerHTML =
            `<img src="${moviePosterUrl}" alt="Movie Poster" style="width: 100px; max-height: 100px;">`;
    }
}

// 선택된 날짜를 업데이트하는 함수
function updateSelectedDate(date) {
    document.getElementById("selected-date").textContent = `날짜: ${formatDate(date)}`;
}

// 선택된 시간을 업데이트하는 함수
function updateSelectedTime(startTime) {
    const [hours, minutes] = startTime.split(':');
    const formattedTime = `${hours}:${minutes}`;
    document.getElementById('selected-time').textContent = `시간: ${formattedTime}`;

}

// 선택한 영화의 정보나 시간, 날짜 등을 설정하는 함수
function setSelectedInfo(poster, title, date, time) {
    document.getElementById('hidden_poster').value = poster;
    document.getElementById('hidden_title').value = title;
    document.getElementById('hidden_date').value = date;
    document.getElementById('hidden_date').value = date;
    document.getElementById('hidden_time').value = time;
}

// 초기화 함수 호출
init();

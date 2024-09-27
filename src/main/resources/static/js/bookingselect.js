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
    // 선택된 영화의 ID를 가져와 변수에 저장하고, hidden 필드에 해당 ID를 설정
    selectedMovieId = element.getAttribute('data-movie-id');
    document.getElementById('hidden_movieId').value = selectedMovieId; // movieID 설정

    // 선택된 영화의 제목과 포스터 URL을 가져옴
    const movieTitle = element.textContent;
    const moviePosterUrl = element.getAttribute('data-movie-poster');

    // 다시 선택된 영화의 ID와 상영 시간 ID를 가져옴
    selectedMovieId = element.getAttribute('data-movie-id');
    selectedShowTimeId = element.getAttribute('data-showTime-id');

    // 선택된 영화 ID를 콘솔에 출력하여 디버깅 용도로 사용
    console.log("Selected Movie ID:", selectedMovieId);

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

    // selectedDate가 유효한지 체크
    if (selectedDate) {
        fetchShowTimes(selectedDate);
    } else {
        console.warn("Warning: selectedDate is not set. Please select a date first.");
        // 여기에 기본 날짜를 설정할지 여부를 결정
        // 예시: selectedDate = new Date(); // 기본값으로 오늘 날짜를 설정
    }

    // 영화 정보 및 선택된 날짜 설정
    setSelectedInfo({
    poster : moviePosterUrl,
    title : movieTitle,
    date : formatDate (selectedDate || new Date()),
    time : '',
    movieId : selectedMovieId,
    showTimeId : selectedShowTimeId
    });
}

// 영화 또는 날짜가 새로 선택되었을때 상영시간 초기화하는 함수
function resetSelections() {
    const timeList = document.querySelector('.time-list');
    if (timeList) timeList.innerHTML = '';
}

// 날짜 선택 시 해당 상영시간 목록 생성 함수
function selectDate(date) {
    console.log("Selected Date:", formatDate(date));
    if (!date) {
        console.error("Error: date is null or undefined.");
        return;
    }
    // 날짜가 유효하면 선택된 날짜를 업데이트하는 함수 호출
    updateSelectedDate(date);
    // 전역변수 selectedDate에 선택된 날짜 값을 저장
    selectedDate = date;

   // 선택된 영화가 존재하는 경우(selectedMovieId) 상영시간 목록 생성함수
   if (selectedMovieId) {
       console.log("여길 지나?");
       getTime(selectedMovieId, date); // selectedMovieId와 date를 전달
   } else {
       console.error("Error: selectedMovieId is null or undefined.");
   }

   // 새로운 방식 (객체로 전달) , 날짜 생성
   setSelectedInfo({
       poster: document.getElementById("hidden_poster").value,
       title: document.getElementById("hidden_title").value,
       date: formatDate(date),
       time: document.getElementById("hidden_time").value,
       movieId: document.getElementById("hidden_movieId").value,  // 필요 시 추가
       showTimeId: document.getElementById("hidden_showTimeId").value  // 필요 시 추가
   });

}

function getTime(selectedMovieId, selectedShowTimeId, startTime) {
  console.log("Selected showTimeID:", selectedShowTimeId);
  console.log("Selected startTime:", startTime);
  console.log("Selected movieId:", selectedMovieId);

  // showtime 목록 가져오기
  fetchShowTimes(selectedMovieId, selectedShowTimeId, startTime);
}


function fetchShowTimes(movieId, showTimeId, startTime) {
    // selectedDate가 정의되어 있는지 확인
    if (!selectedDate) {
        console.error("Error: selectedDate is undefined or null.");
        return;
    }

    // fetchShowTimes 호출 시 매개변수 로그 출력
    console.log("fetchShowTimes 호출됨");
    console.log("movieId:", movieId);
    console.log("showTimeId:", showTimeId);
    console.log("startTime:", startTime);

    document.getElementById('hidden_showTimeId').value = showTimeId;


    // Ajax 요청
   $.ajax({
       url: '/api/showtimes',
       type: 'GET',
       data: {
           movieId: movieId,
           date: formatDate(selectedDate) // 날짜 형식 확인
       },
       dataType: 'json',
       success: function(showTimes) {
           console.log("Fetched show times:", showTimes); // 응답 데이터 확인
           renderTimeList(showTimes); // 화면에 출력
       },
       error: function(xhr, status, error) {
           console.error("Error fetching show times:", error);
           console.error("Response text:", xhr.responseText); // 응답 본문 확인
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

    const movieId = document.getElementById("hidden_movieId").value; // movieId 가져오기

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
                selectTime(showTime.showTimeID, showTime.startTime, movieId); // movieId 전달
            };

            col.appendChild(button);
            row.appendChild(col);
        });
    }
}

// 최종 상영 시간 선택 함수
function selectTime(showTimeID, startTime, movieId) {
    if (startTime) {
        // fetchShowTimes를 호출하여 상영 시간 가져오기
        fetchShowTimes(movieId, showTimeID, startTime);


        updateSelectedTime(startTime);
        /*console.log("데이터 들어왔나" + Time);
        document.getElementById('hidden_showTimeId').value = Time; // showTimeID 필드에 설정*/

        // 새로운 방식 (객체로 전달)
        setSelectedInfo({
            poster: document.getElementById("hidden_poster").value,
            title: document.getElementById("hidden_title").value,
            date: document.getElementById("hidden_date").value,
            time: startTime,  // 필요한 상영 시간
            movieId: movieId,  // 전달된 movieId 사용
            showTimeId: document.getElementById('hidden_showTimeId').value  // 매개변수로 받은 showTimeID 사용
        });



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

// 선택한 영화의 정보나 시간, 날짜 등을 설정하는 함수(객체형식)
function setSelectedInfo(selectedInfo) {
    document.getElementById('hidden_poster').value = selectedInfo.poster;
    document.getElementById('hidden_title').value = selectedInfo.title;
    document.getElementById('hidden_date').value = selectedInfo.date;
    document.getElementById('hidden_time').value = selectedInfo.time;
    document.getElementById('hidden_movieId').value = selectedInfo.movieId;
   // document.getElementById('hidden_showTimeId').value = selectedInfo.showTimeId;
}

// 초기화 함수 호출
init();
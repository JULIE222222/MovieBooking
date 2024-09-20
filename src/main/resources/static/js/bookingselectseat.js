// 기본 선택된 요소들
const container = document.querySelector(".movie-seat");
const count = document.getElementById("count");
const total = document.getElementById("total");
const seatDisplay = document.getElementById('selected-seat');

// 기본 영화 가격 설정
let ticketPrice = 10000; // 기본 가격 설정

// 좌석 배열 데이터: 서버에서 받아온 데이터를 여기에 매핑합니다.
const rows = ["A", "B", "C", "D", "E"];  // 5행
const seatsPerRow = 10;                  // 각 행에 10개 좌석

// 좌석을 표시할 컨테이너
const seatContainer = document.getElementById("seat-container");

// DOMContentLoaded 시 좌석 생성 및 클릭 이벤트 바인딩
document.addEventListener("DOMContentLoaded", function () {
    // 좌석 생성 실행
    generateSeats(rows, seatsPerRow);

    // 좌석 클릭 이벤트 핸들러
    if (seatContainer) {
        seatContainer.addEventListener("click", (e) => {
            const seat = e.target.closest('.seat'); // .seat 클래스를 가진 요소를 찾기
            if (seat && !seat.classList.contains("occupied")) {
                seat.classList.toggle("selected");
                updateSelectedCount();
            }
        });
    } else {
        console.log("seatContainer 요소를 찾지 못했습니다.");
    }

    // UI 초기화 및 선택된 좌석 업데이트
    populateUI();
});

// 좌석 생성 함수
function generateSeats(rows, seatsPerRow) {
    rows.forEach(row => {
        // 각 행마다 새로운 div(row) 생성
        const rowDiv = document.createElement("div");
        rowDiv.classList.add("row");

        for (let i = 1; i <= seatsPerRow; i++) {
            const seat = document.createElement("div");
            seat.classList.add("seat");
            seat.textContent = `${row}${i}`;  // 좌석 번호 표시
            rowDiv.appendChild(seat);  // 좌석을 row에 추가
        }

        // 좌석 행을 seatContainer에 추가
        seatContainer.appendChild(rowDiv);
    });
}

function updateSelectedCount() {
    // 좌석을 감싸는 컨테이너를 기준으로 .seat.selected만 선택
    const seatContainer = document.querySelector("#seat-container"); // 좌석들이 위치한 컨테이너 선택
    const selectedSeats = seatContainer.querySelectorAll(".seat.selected"); // 선택된 좌석들 찾기

    // 전체 좌석 목록 가져오기
    const seats = seatContainer.querySelectorAll(".seat");

    // 선택된 좌석의 인덱스를 로컬 스토리지에 저장
    const seatsIndex = [...selectedSeats].map(seat => [...seats].indexOf(seat));
    localStorage.setItem("selectedSeats", JSON.stringify(seatsIndex));

    // 선택된 좌석 수 카운트
    const selectedSeatsCount = selectedSeats.length;
    document.getElementById("count").innerText = selectedSeatsCount;

    updateSelectedSeats(); // 선택된 좌석 정보 업데이트
}


// 선택된 좌석 정보를 UI에 업데이트
function updateSelectedSeats() {
    const selectedSeats = document.querySelectorAll('.seat.selected');
    const seatNumbers = [...selectedSeats].map(seat => seat.innerText.trim()); // 좌석 번호 가져오기

    localStorage.setItem('selectedSeats', JSON.stringify(seatNumbers)); // 좌석 정보 저장
    updateSeatDisplay(seatNumbers); // UI 업데이트
}

let isFirstSelection = true; // 첫 선택 여부를 추적하는 변수
// 선택된 좌석 화면에 표시
function updateSeatDisplay(seatNumbers) {
    seatDisplay.textContent = seatNumbers.join(', '); // 선택된 좌석 번호를 화면에 표시
}

// 로컬 스토리지에서 선택된 좌석 데이터를 불러와 UI 업데이트
function populateUI() {
    const selectedSeats = JSON.parse(localStorage.getItem("selectedSeats"));
    const seats = document.querySelectorAll(".seat"); // 좌석 업데이트

    if (selectedSeats !== null && selectedSeats.length > 0) {
        seats.forEach((seat, index) => {
            if (selectedSeats.indexOf(index) > -1) {
                seat.classList.add("selected");
            }
        });
    }
}

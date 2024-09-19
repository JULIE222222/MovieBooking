const container = document.querySelector(".movie-seat");
const count = document.getElementById("count");
const total = document.getElementById("total");
const seatDisplay = document.getElementById('selected-seat');

// 기본 영화 가격 설정
let ticketPrice = 10000; // 기본 가격 설정

// UI 초기화 및 선택된 좌석 업데이트
populateUI();

// 좌석 클릭 이벤트 핸들러
document.addEventListener("DOMContentLoaded", () => {
  console.log(container); // 올바르게 선택된 container 확인
  if (container) {
    container.addEventListener("click", (e) => {
      console.log("좌석 클릭 이벤트 발생!");
      const seat = e.target.closest('.seat'); // .seat 클래스를 가진 요소를 찾기
      if (seat && !seat.classList.contains("occupied")) {
        seat.classList.toggle("selected");
        updateSelectedCount();
      }
    });
  } else {
    console.log("container 요소를 찾지 못했습니다.");
  }
});

// 선택된 좌석 수 및 총 가격 업데이트
function updateSelectedCount() {
  const selectedSeats = document.querySelectorAll(".seat.selected"); // 선택된 좌석 찾기
  const seats = document.querySelectorAll(".seat"); // 클릭 시 업데이트되는 좌석 목록

  const seatsIndex = [...selectedSeats].map(seat => [...seats].indexOf(seat));
  localStorage.setItem("selectedSeats", JSON.stringify(seatsIndex));

  const selectedSeatsCount = selectedSeats.length;
  count.innerText = selectedSeatsCount;
  //total.innerText = selectedSeatsCount * ticketPrice; // 총 가격 업데이트

  updateSelectedSeats(); // 선택된 좌석 정보 업데이트
}

// 선택된 좌석 정보를 UI에 업데이트
function updateSelectedSeats() {
  const selectedSeats = document.querySelectorAll('.seat.selected');
  const seatNumbers = [...selectedSeats].map(seat => seat.innerText.trim()); // 좌석 번호 가져오기

  localStorage.setItem('selectedSeats', JSON.stringify(seatNumbers)); // 좌석 정보 저장
  updateSeatDisplay(seatNumbers); // UI 업데이트
}

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


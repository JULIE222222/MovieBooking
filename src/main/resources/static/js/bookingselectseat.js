// 기본 선택된 요소들
const container = document.querySelector(".movie-seat");
const count = document.getElementById("count");
const total = document.getElementById("total");
const seatDisplay = document.getElementById('selected-seat');
const bookingForm = document.getElementById('bookingForm'); // 폼 요소 추가


// 기본 영화 가격 설정
let ticketPrice = 10000; // 기본 가격 설정

// 좌석 데이터 초기화
let totalSeats = 0; // 총 좌석 수
let occupiedSeats = []; // 이미 예약된 좌석 번호

const seatContainer = document.getElementById('seat-container');

// 서버에서 좌석 데이터 가져오기
async function fetchSeats() {
    try {
        const response = await fetch('/api/seats');
        const seats = await response.json();

        console.log("Fetched seats data:", seats); // 데이터 확인
        totalSeats = seats.length; // 전체 좌석 수 설정
        occupiedSeats = seats
            .filter(seat => seat.occupied) // occupied가 true인 좌석 필터링
            .map(seat => seat.seatNumber); // 각 좌석의 번호를 가져옴

        createSeats(); // 좌석 생성 호출
    } catch (error) {
        console.error("Error fetching seat data:", error);
    }
}

// DOMContentLoaded 시 좌석 데이터 가져오기 및 생성
document.addEventListener("DOMContentLoaded", async () => {
    await fetchSeats(); // 서버에서 데이터 가져오기
});

// 좌석 생성 함수
function createSeats() {
    seatContainer.innerHTML = ''; // 기존 좌석 제거
    const rows = 5; // 행 수
    const seatsPerRow = 10; // 열 수

    for (let i = 0; i < rows; i++) {
        const row = document.createElement('div');
        row.className = 'row';

        for (let j = 1; j <= seatsPerRow; j++) {
            const seatLetter = String.fromCharCode(65 + i); // A, B, C, D, E
            const seatId = `${seatLetter}${j}`; // A1, A2, ..., E10

            const seat = document.createElement('div');
            seat.className = 'seat';
            seat.textContent = seatId;

            // 좌석 상태 설정
            if (occupiedSeats.includes(seatId)) {
                seat.classList.add('occupied');
                seat.style.cursor = 'not-allowed'; // 클릭 불가능 표시
            } else {
                seat.addEventListener('click', () => {
                    seat.classList.toggle('selected');
                    updateSelectedCount();
                });
            }

            row.appendChild(seat);
        }
        seatContainer.appendChild(row); // 각 행을 seatContainer에 추가
    }
}

// 선택된 좌석 수 업데이트
function updateSelectedCount() {
    const selectedSeats = document.querySelectorAll('.seat.selected');
    const selectedSeatsNumbers = [...selectedSeats].map(seat => seat.textContent); // 선택된 좌석 번호 가져오기

    // 첫 번째 요소 제거 (원하는 경우)
    if (selectedSeatsNumbers.length > 0) {
        selectedSeatsNumbers.shift(); // 첫 번째 요소 제거
    }

    document.getElementById('count').textContent = selectedSeats.length - 1; // 선택된 좌석 수 업데이트
    updateSeatDisplay(selectedSeatsNumbers); // 선택된 좌석 정보 업데이트
    console.log(selectedSeatsNumbers); // 선택된 좌석 번호 확인
}

// 선택된 좌석 정보를 UI에 업데이트
function updateSeatDisplay(seatNumbers) {
    seatDisplay.textContent = seatNumbers.join(', '); // 선택된 좌석 번호를 화면에 표시
}

// 결제하기 버튼 클릭 시 예약 처리
document.getElementById('submit-booking').addEventListener('click', async () => {
    const selectedSeats = Array.from(document.querySelectorAll('.seat.selected'))
        .map(seat => seat.textContent);

    // 선택된 좌석 정보가 비어있지 않다면
    if (selectedSeats.length === 0) {
        alert("좌석을 선택해주세요!");
        return;
    }

    const bookingData = {
         seats: selectedSeats, // 좌석을 배열 그대로 전송
            movie: document.getElementById('movieID').value, // 영화 ID
            showTime: document.getElementById('showTimeID').value // 상영시간 ID

    };
    console.log("예약 데이터:", reservationData);

    try {
        const response = await fetch('/api/booking', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(bookingData)
        });
console.log('예약 데이터:', bookingData);
        if (response.ok) {
            const data = await response.json();
            console.log('예약 성공:', data);
            alert('예약이 완료되었습니다!');
            // 예약 완료 후 처리 (예: 페이지 리디렉션)
        } else {
            const errorData = await response.json(); // 서버에서 반환한 오류 메시지
                console.error('예약 실패:', errorData);
                alert(`예약 실패: ${errorData.message || '알 수 없는 오류입니다.'}`);
        }
    } catch (error) {
        console.error('에러:', error);
        alert('예약 중 오류가 발생했습니다. 다시 시도해주세요.');
    }
});

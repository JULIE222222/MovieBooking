// 기본 선택된 요소들
const container = document.querySelector(".movie-seat"); // 영화 좌석이 표시되는 컨테이너
const count = document.getElementById("count"); // 선택된 좌석 수를 표시하는 요소
const total = document.getElementById("total"); // 총 금액을 표시하는 요소
const seatDisplay = document.getElementById('selected-seat'); // 선택된 좌석 번호를 표시하는 요소
const bookingForm = document.getElementById('bookingForm'); // 예약 폼 요소

// 기본 영화 가격 설정
let ticketPrice = 10000; // 각 좌석의 티켓 가격을 10,000원으로 설정

// 좌석 데이터 초기화
let totalSeats = 0; // 총 좌석 수를 저장할 변수
let occupiedSeats = []; // 이미 예약된 좌석 번호를 저장할 배열

const seatContainer = document.getElementById('seat-container'); // 좌석이 생성될 컨테이너

// 서버에서 좌석 데이터 가져오기
async function fetchSeats() {
    try {
        const response = await fetch('/api/seats'); // 서버에서 좌석 데이터를 가져오는 API 호출
        const seats = await response.json(); // 응답 데이터를 JSON으로 파싱
        console.log("Fetched seats data:", seats); // 가져온 좌석 데이터를 콘솔에 출력하여 확인
        totalSeats = seats.length; // 총 좌석 수를 저장
        occupiedSeats = seats
            .filter(seat => seat.occupied) // 예약된 좌석만 필터링
            .map(seat => seat.seatNumber); // 예약된 좌석의 번호만 추출

        createSeats(); // 좌석을 화면에 생성하는 함수 호출
    } catch (error) {
        console.error("Error fetching seat data:", error); // 데이터 가져오기 실패 시 에러 메시지 출력
    }
}

// DOMContentLoaded 이벤트가 발생하면 좌석 데이터를 가져오고 생성하는 작업 실행
document.addEventListener("DOMContentLoaded", async () => {
    await fetchSeats(); // 좌석 데이터를 서버에서 가져오는 작업 수행
});

// 좌석 생성 함수
function createSeats() {
    seatContainer.innerHTML = ''; // 기존에 생성된 좌석을 제거
    const rows = 5; // 좌석의 행 수 (5행)
    const seatsPerRow = 10; // 각 행당 좌석 수 (10열)

    // 좌석을 행과 열의 형태로 생성
    for (let i = 0; i < rows; i++) {
        const row = document.createElement('div'); // 새로운 행 생성
        row.className = 'row'; // 행에 클래스 추가

        for (let j = 1; j <= seatsPerRow; j++) {
            const seatLetter = String.fromCharCode(65 + i); // A, B, C, D, E 등의 행 식별자 생성
            const seatId = `${seatLetter}${j}`; // 좌석 ID 생성 (예: A1, B5 등)

            const seat = document.createElement('div'); // 좌석 요소 생성
            seat.className = 'seat'; // 좌석에 클래스 추가
            seat.textContent = seatId; // 좌석의 텍스트로 ID 표시

            // 좌석 상태 설정
            if (occupiedSeats.includes(seatId)) { // 해당 좌석이 예약된 좌석인지 확인
                seat.classList.add('occupied'); // 예약된 좌석에 occupied 클래스 추가
                seat.style.cursor = 'not-allowed'; // 예약된 좌석은 클릭할 수 없게 처리
            } else {
                // 클릭 가능한 좌석이면 클릭 시 selected 상태를 토글하는 이벤트 추가
                seat.addEventListener('click', () => {
                    seat.classList.toggle('selected'); // 선택된 좌석은 selected 클래스를 추가/제거
                    updateSelectedCount(); // 선택된 좌석 수와 정보를 업데이트하는 함수 호출
                });
            }

            row.appendChild(seat); // 좌석을 해당 행에 추가
        }
        seatContainer.appendChild(row); // 행을 seatContainer에 추가하여 화면에 표시
    }
}

// 선택된 좌석 수와 선택된 좌석 정보 업데이트
function updateSelectedCount() {
    const selectedSeats = document.querySelectorAll('.seat.selected'); // 선택된 좌석을 모두 찾음
    const selectedSeatsNumbers = [...selectedSeats].map(seat => seat.textContent); // 선택된 좌석의 번호 가져오기

    // 여기서 selectedSeatsNumbers가 비어있지 않은지 확인
    console.log('Selected Seats:', selectedSeatsNumbers); // 디버깅용 로그

    document.getElementById('count').textContent = selectedSeats.length; // 선택된 좌석 수 표시
    updateSeatDisplay(selectedSeatsNumbers); // 선택된 좌석 정보를 화면에 업데이트
}

// 선택된 좌석 정보를 UI에 업데이트
function updateSeatDisplay(seatNumbers) {
    seatDisplay.textContent = seatNumbers.join(', '); // 선택된 좌석 번호를 쉼표로 구분하여 표시
}

// 결제하기 버튼 클릭 시 예약 처리
document.getElementById('submit-booking').addEventListener('click', async () => {
    const selectedSeats = Array.from(document.querySelectorAll('.seat.selected'))
        .map(seat => seat.textContent); // 선택된 좌석 번호를 배열로 가져옴

    // 선택된 좌석 정보가 비어있으면 경고 메시지 출력
    if (selectedSeats.length == 0) {
        alert("좌석을 선택해주세요!"); // 좌석 선택이 없을 때 알림
        return;
    }
    const movieId = document.getElementById("hidden_movieId").value;
    const showTimeId = document.getElementById("hidden_showTimeId").value;

    console.log('Movie ID:', movieId); // 영화 ID 확인
    console.log('ShowTime ID:', showTimeId); // 상영시간 ID 확인

    const bookingData = {
        seats: selectedSeats, // 선택된 좌석 정보를 배열로 전송
        movieId: document.getElementById("hidden_movieId").value,
        showTimeId: document.getElementById("hidden_showTimeId").value,
    };

    console.log('Booking data:', bookingData); // 서버로 보내는 데이터 확인

    try {
        const response = await fetch('/api/booking', {
            method: 'POST', // POST 메소드로 서버에 데이터 전송
            headers: {
                'Content-Type': 'application/json' // JSON 형식으로 데이터 전송
            },
            body: JSON.stringify(bookingData) // 예약 데이터를 JSON 문자열로 변환하여 전송
        });

        if (response.ok) {
            const data = await response.json(); // 서버에서 받은 응답을 JSON으로 변환
            console.log('예약 성공:', data); // 예약 성공 시 응답 데이터 출력
            alert('예약이 완료되었습니다!'); // 성공 메시지 출력
            // 예약 완료 후 추가 처리 (예: 페이지 리디렉션)
        } else {
            const errorData = await response.json(); // 오류 응답 데이터 받기
            console.error('예약 실패:', errorData); // 예약 실패 시 오류 메시지 출력
            alert(`예약 실패: ${errorData.message || '알 수 없는 오류입니다.'}`); // 실패 메시지 출력
        }
    } catch (error) {
        console.error('에러:', error); // 네트워크 에러 등 처리
        alert('예약 중 오류가 발생했습니다. 다시 시도해주세요.'); // 일반적인 오류 메시지 출력
    }
});

const container = document.querySelector(".container");
const seats = document.querySelectorAll(".row .seat:not(.occupied)");
const count = document.getElementById("count");
const total = document.getElementById("total");
const movieSelect = document.getElementById("movie");

populateUI();

let ticketPrice = +movieSelect.value;

// Save selected movie index and price
function setMovieData(movieIndex, moviePrice) {
  localStorage.setItem("selectedMovieIndex", movieIndex);
  localStorage.setItem("selectedMoviePrice", moviePrice);
}

// Update total and count
function updateSelectedCount() {
  const selectedSeats = document.querySelectorAll(".row .seat.selected");

  const seatsIndex = [...selectedSeats].map((seat) => [...seats].indexOf(seat));

  localStorage.setItem("selectedSeats", JSON.stringify(seatsIndex));

  const selectedSeatsCount = selectedSeats.length;

  count.innerText = selectedSeatsCount;
  total.innerText = selectedSeatsCount * ticketPrice;
}

// Get data from localstorage and populate UI
function populateUI() {
  localStorage.clear();
  const selectedSeats = JSON.parse(localStorage.getItem("selectedSeats"));

  if (selectedSeats !== null && selectedSeats.length > 0) {
    seats.forEach((seat, index) => {
      if (selectedSeats.indexOf(index) > -1) {
        seat.classList.add("selected");
      }
    });
  }

  const selectedMovieIndex = localStorage.getItem("selectedMovieIndex");

  if (selectedMovieIndex !== null) {
    movieSelect.selectedIndex = selectedMovieIndex;
  }
}

// Movie select event
movieSelect.addEventListener("change", (e) => {
  ticketPrice = +e.target.value;
  setMovieData(e.target.selectedIndex, e.target.value);
  updateSelectedCount();
});

// Seat click event
container.addEventListener("click", (e) => {
  if (
    e.target.classList.contains("seat") &&
    !e.target.classList.contains("occupied")
  ) {
    e.target.classList.toggle("selected");

    updateSelectedCount();
  }
});

 function updateSelectedDetails() {
     // localStorage에서 데이터 가져오기
     const title = localStorage.getItem('hidden-title');
     const poster = localStorage.getItem('hidden-poster');
     const date = localStorage.getItem('hidden-date');
     const time = localStorage.getItem('hidden-time');
     const seat = localStorage.getItem('hidden-seat');

     // 각 요소에 데이터 설정
     document.getElementById('selected-movie-title').textContent = title || '';
     document.getElementById('selected-movie-poster').innerHTML = poster ? `<img src="${poster}" alt="Movie Poster" style="width: 100%; height: auto;">` : '';
     document.getElementById('selected-date').textContent = date || '';
     document.getElementById('selected-time').textContent = time || '';
     document.getElementById('selected-seat').textContent = seat || '';
 }

     // 페이지가 로드되면 setFormData 함수를 호출합니다.
     document.addEventListener('DOMContentLoaded', setFormData);




// Initial count and total set
updateSelectedCount();

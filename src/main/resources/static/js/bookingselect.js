// s_selbox 배열은 각 지역에 따른 장소 목록을 저장하고 있습니다.
        // s_selbox[0]은 서울, s_selbox[1]은 부산, s_selbox[2]는 제주에 해당하는 장소들을 포함합니다.
        var s_selbox = [
            ["인천공항 1터미널", "인천공항 2터미널", "김포공항 국내선", "김포공항 국제선", "서울역", "홍대입구", "고속버스터미널", "동서울터미널"],
            ["강남", "분당", "용인"],
            ["날짜"]
            ["상영시간"]
        ];

        // itemChange 함수는 첫 번째 리스트에서 항목을 클릭할 때 호출됩니다.
        // 매개변수 selectedIndex는 사용자가 클릭한 지역의 인덱스 값입니다 (0, 1, 2 중 하나).
        function itemChange(selectedIndex) {
            // .place-list 클래스의 요소를 가져옵니다. 이 요소는 두 번째 리스트입니다.
            var placeList = document.querySelector('.place-list');

            // placeList의 기존 내용을 모두 삭제합니다. 새로운 장소 목록을 표시하기 전 초기화 과정입니다.
            placeList.innerHTML = '';

            // s_selbox 배열에서 선택된 지역에 맞는 장소 목록을 가져옵니다.
            s_selbox[selectedIndex].forEach(function (place) {
                // 새로운 리스트 항목을 생성합니다.
                var li = document.createElement('li');
                // 생성된 리스트 항목에 장소명을 텍스트로 설정합니다.
                li.textContent = place;
                // 생성된 리스트 항목을 placeList에 추가하여 화면에 표시합니다.
                placeList.appendChild(li);
            });
        }
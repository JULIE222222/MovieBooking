package com.cinema.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingDTO {

    private Long memberId;
    private List<String> seats; // 선택된 좌석 ID 목록으로 변경
    private Long movieId; // 영화 ID
    private Long showTimeId; // 상영시간 ID


}
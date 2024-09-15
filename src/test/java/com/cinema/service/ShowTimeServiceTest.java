package com.cinema.service;

import com.cinema.domain.ShowTime;
import com.cinema.repository.ShowTimeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ShowTimeServiceTest {

    @Autowired
    private ShowTimeService showTimeService;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Test
    void saveShowTime() {
        //테스트용 객체 생성
        ShowTime showTime = new ShowTime();
        showTime.setShowDate("2024-09-01");
        showTime.setStartTime(LocalTime.parse("14:00"));
        showTime.setEndTime(LocalTime.parse("16:00"));

        //showTime을 저장
        ShowTime saveShowTime = showTimeService.saveShowTime(showTime);

        // 저장된 ShowTime이 null이 아닌지 확인
        assertNotNull(saveShowTime);
        assertEquals("2024-09-01", saveShowTime.getShowDate());
        assertEquals(LocalTime.parse("14:00"), saveShowTime.getStartTime());
    }

    @Test
    void getShowTimesByDate() {
        // 먼저 데이터를 저장
        ShowTime showTime1 = new ShowTime();
        showTime1.setShowDate("2024-09-15");
        showTime1.setStartTime(LocalTime.parse("14:00"));
        showTime1.setEndTime(LocalTime.parse("16:00"));
        showTimeService.saveShowTime(showTime1);

        ShowTime showTime2 = new ShowTime();
        showTime2.setShowDate("2024-09-15");
        showTime2.setStartTime(LocalTime.parse("16:30"));
        showTime2.setEndTime(LocalTime.parse("18:30"));
        showTimeService.saveShowTime(showTime2);

        // 주어진 날짜의 상영 시간을 가져옴
        List<ShowTime> showTimes = showTimeService.getShowTimesByDate("2024-09-15");

        // 상영 시간이 2개인지 확인
        assertEquals(2, showTimes.size());
        assertEquals(LocalTime.parse("14:00"), showTimes.get(0).getStartTime());
        assertEquals(LocalTime.parse("16:30"), showTimes.get(1).getStartTime());
    }
}
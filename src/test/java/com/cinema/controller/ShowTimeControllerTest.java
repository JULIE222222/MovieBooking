package com.cinema.controller;

import com.cinema.domain.Movie;
import com.cinema.domain.ShowTime;
import com.cinema.domain.Theater;
import com.cinema.service.MovieService;
import com.cinema.service.ShowTimeService;
import com.cinema.service.TheaterService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShowTimeController.class)
class ShowTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private ShowTimeService showTimeService;

    @MockBean
    private TheaterService theaterService;

    @Test
    void testShowtimeInfoForm() throws Exception {
        when(movieService.getAllMovies()).thenReturn(List.of(new Movie()));
        when(theaterService.getAllTheaters()).thenReturn(List.of(new Theater()));

        mockMvc.perform(get("/showtime/showtimeInfoForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("uploadInfo/showtimeInfoForm"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attributeExists("theaters"))
                .andExpect(model().attributeExists("showTime"));

        verify(movieService).getAllMovies();
        verify(theaterService).getAllTheaters();
    }

    @Test
    void testSubmitShowtimeInfo() throws Exception {
        mockMvc.perform(post("/showtime/showtimeInfoForm")
                        .param("showDate", "2024-09-01")
                        .param("startTime", "14:00")
                        .param("endTime", "16:00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/showtime/showtimeInfoForm"));

        verify(showTimeService).saveShowTime(any(ShowTime.class));
    }

    @Test
    void testUpdateShowTime() throws Exception {
        mockMvc.perform(post("/showtime/updateShowTime")
                        .param("movieId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("uploadInfo/showtimeTimeList"))
                .andExpect(model().attributeExists("showTimes"));

        verify(showTimeService).getShowTimesByDate(anyString());
    }

    @Test
    void testGetShowTimes() throws Exception {
        when(showTimeService.getShowTimesByDate(anyString())).thenReturn(List.of(new ShowTime()));

        mockMvc.perform(get("/showtime/getShowTimes")
                        .param("movieId", "1")
                        .param("date", "2024-09-15"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[]")); // 빈 JSON 배열

        verify(showTimeService).getShowTimesByDate(anyString());
    }
}

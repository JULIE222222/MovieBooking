package com.cinema.controller;

import com.cinema.domain.Movie;
import com.cinema.repository.MovieRepository;
import com.cinema.service.MovieService;
import com.cinema.utils.MyPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    /*@PostMapping("/image")
    public @ResponseBody String image(MultipartFile pic){

        UUID uuid = UUID.randomUUID();
        //파일이름 찾는 함수
        String imageFileName = uuid + "_" + pic.getOriginalFilename();

        Path imagePath = Paths.get(MyPath.IMAGEPATH + imageFileName);

        try{
            Files.write(imagePath, pic.getBytes());
        } catch (Exception e){
            e.printStackTrace();
            return "Error uploading image";
        }
        return imageFileName;
    }*/



    @PostMapping("/movieInfoForm")
    public String uploadMovie(
            @RequestParam("pic") MultipartFile pic,
            @RequestParam("title") String title,
            @RequestParam("director") String director,
            @RequestParam("cast") String cast,
            @RequestParam("releaseDate") String releaseDateStr,
            @RequestParam("rating") String rating,
            @RequestParam("description") String description,
            Model model) {

        try {
            // 파일 업로드 처리
            UUID uuid = UUID.randomUUID();
            String imageFileName = uuid + "_" + pic.getOriginalFilename();
            Path imagePath = Paths.get(MyPath.IMAGEPATH + imageFileName);
            Files.write(imagePath, pic.getBytes());

            // 날짜 변환 처리
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate releaseDate = LocalDate.parse(releaseDateStr, formatter);

            // Movie 객체 생성 및 저장
            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setDirector(director);
            movie.setCast(cast);
            movie.setReleaseDate(releaseDate);
            movie.setRating(rating);
            movie.setDescription(description);
            movie.setPosterURL(imageFileName); // 이미지 파일 경로 설정

            movieService.save(movie);

            model.addAttribute("message", "영화 정보가 성공적으로 업로드되었습니다.");
            return "redirect:/movieInfoForm";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "영화 정보 업로드 중 오류가 발생했습니다.");
            return "uploadInfo/movieInfoForm";
        }
    }

    //영화정보 등록페이지
    @GetMapping("/movieInfoForm")
    public String getUploadMoviePage(Model model){

        model.addAttribute("movieInfoForm", new Movie());
        return "/uploadInfo/movieInfoForm";
    }

    //메인화면 best4 영화목록 띄우기
    @GetMapping ("/")
    public String findTop4ByMovieDateDesc(Model model){
        List<Movie> recentMovie = movieService.get4Movies();
        model.addAttribute("movie",recentMovie);

        return "index";
    }


    //영화 전체목록
    @GetMapping("/movie")
    public String getAllMovies(Model model){
        List<Movie> movie = movieService.getAllMovies();
        model.addAttribute("movie", movie);

    return "movie";
    }

    //영화 상세페이지 로드
    @GetMapping("/movieDetailPage")
    public String MovieDetailPage(@RequestParam("id") Long movieId, Model model){
         //Id로 영화 정보가져오기
        Movie movie = movieService.findById(movieId);
        model.addAttribute("movie",movie);
        return "movies/movieDetailPage";
    }


   /* // 영화 포스터 가져오기 엔드포인트
    @GetMapping("/movie/getPoster")
    @ResponseBody
    public String getMoviePoster(@RequestParam("movieId") Long movieId) {
        Movie movie = movieService.findById(movieId);
        if (movie != null) {

            System.out.println(movie.getPosterURL());
            return movie.getPosterURL();  // 포스터 URL 반환
        } else {
            return "영화 정보를 찾을 수 없습니다.";  // 에러 메시지
        }
    }*/

    @GetMapping("/movie/getPoster")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getMoviePoster(@RequestParam("movieId") Long movieId) {
        Movie movie = movieService.findById(movieId);
        Map<String, String> response = new HashMap<>();
        if (movie != null) {
            response.put("posterURL", movie.getPosterURL());
            System.out.println(movie.getPosterURL());
        } else {
            response.put("error", "영화 정보를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(response);
    }
}


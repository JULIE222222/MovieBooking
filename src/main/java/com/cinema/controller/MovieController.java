package com.cinema.controller;

import com.cinema.domain.Movie;
import com.cinema.service.MovieService;
import com.cinema.utils.MyPath;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    @PostMapping("/image")
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = sdf.parse(releaseDateStr);

            // Movie 객체 생성 및 저장
            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setDirector(director);
            movie.setCast(cast);
            movie.setReleaseDate(releaseDate);
            movie.setRating(Integer.parseInt(rating));
            movie.setDescription(description);
            movie.setPosterURL(imageFileName); // 이미지 파일 경로 설정

            movieService.save(movie);

            model.addAttribute("message", "영화 정보가 성공적으로 업로드되었습니다.");
            return "redirect:/image";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "영화 정보 업로드 중 오류가 발생했습니다.");
            return "uploadIMG/image";
        }
    }

    @GetMapping("/image")
    public String getImageUploadPage(){
        return "/uploadIMG/image";
    }

    @GetMapping("/movie")
    public String list(Model model){

        List<Movie> movie = movieService.findAll();
        model.addAttribute("movie", movie);

    return "movie";
    }
}

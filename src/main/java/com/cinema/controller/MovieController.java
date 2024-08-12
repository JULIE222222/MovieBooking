package com.cinema.controller;

import com.cinema.domain.Movie;
import com.cinema.service.MovieService;
import com.cinema.utils.MyPath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/image")
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

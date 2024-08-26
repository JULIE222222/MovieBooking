package com.cinema.controller;

import com.cinema.domain.Member;
import com.cinema.service.MemberService;
import com.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public String getProfilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // 로그인된 사용자의 id로 Member객체를 조회합니다.
        String id = userDetails.getUsername(); //잘 불러옴
        Member member = memberService.findById(id);

        if (member != null) {
            System.out.println("Name: " + member.getName());
            System.out.println("ID: " + member.getId());

            model.addAttribute("name", member.getName());
            model.addAttribute("id", member.getId());// member의 name을 모델에 추가합니다.

        } else {
            model.addAttribute("name", "Unknown");
            model.addAttribute("id", "Unknown");
        }

        return "profile";
    }
}

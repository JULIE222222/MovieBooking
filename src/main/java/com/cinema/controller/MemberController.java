package com.cinema.controller;

import com.cinema.domain.Member;
import com.cinema.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/joinForm")
    public String joinMember(
            @RequestParam("id") String id,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            Model model) {

        try {
            Member member = new Member();
            member.setId(id);
            member.setPassword(password);
            member.setName(name);
            member.setEmail(email);
            member.setPhone(phone);

            memberService.join(member);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "회원가입 중 오류가 발생했습니다.");
            return "members/joinForm";
        }
        return "redirect:/";
    }

    @GetMapping("/joinForm")
    public String getJoinMember(Model model) {
        model.addAttribute("joinForm", new Member());
        return "members/joinForm";
    }

}

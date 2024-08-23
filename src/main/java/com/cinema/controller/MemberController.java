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


    //회원가입 기능
    @PostMapping("/joinForm")
    public String joinMember(
            @RequestParam("id") String id,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
           /* @RequestParam("role") String role,  -> 가져올값이 없기때문에 작성x */
            Model model) {

        try {
            Member member = new Member();
            member.setId(id);
            member.setPassword(password);
            member.setName(name);
            member.setEmail(email);
            member.setPhone(phone);
           /* member.setRole("ROLE_USER");*/

            memberService.join(member);

            model.addAttribute("message", "회원가입이 완료되었습니다.");
            return "redirect:/loginForm";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "회원가입 중 오류가 발생했습니다.");
            return "members/joinForm";
        }
    }

    @GetMapping("/joinForm")
    public String getJoinMember(Model model) {

        model.addAttribute("joinForm", new Member());
        return "members/joinForm";
    }


    //로그인 기능
    @GetMapping("/loginForm")
    public String loginForm (){
        return "members/loginForm";
    }


    /*@PostMapping("/loginForm")
    public String login(@RequestParam("id") String id, @RequestParam("password") String password, Model model) {
        try {
            Member member = memberService.login(id, password);
            if (member != null) {
                return "redirect:/";
            } else {
                model.addAttribute("error", "Invalid credentials");
                return "members/loginForm";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred");
            return "members/loginForm";
        }
    }*/
}

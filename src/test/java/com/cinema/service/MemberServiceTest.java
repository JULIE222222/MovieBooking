package com.cinema.service;

import com.cinema.domain.Member;
import com.cinema.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Test
    public void join() throws Exception{
        Member member = new Member();
        member.setName("jiji");
        member.setId("jjii");
        member.setPhone("010-0000-0000");
        member.setEmail("jiji@naver.com");
        member.setPassword("12345");
    }
}
package com.cinema.service;

import com.cinema.domain.Member;
import com.cinema.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 인코더 추가

    //회원가입
    @Transactional
    public Long join(Member member) {
        // 중복회원 검증 메서드
        validateDuplicateMember(member);
        // 비밀번호를 암호화하여 저장
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        // 사용자 역할 설정 (기본적으로 USER로 설정)
        member.setRole("USER");
        // 데이터 저장
        Member savedMember = memberRepository.save(member);
        return savedMember.getUserID();
    }

    //중복회원 검증
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findById(member.getId());

        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    public Member findById(String id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member nowMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return findById(userId);
    }
/*    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(id);

        if (member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("ADMIN".equals(member.getRole())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .authorities(authorities)
                .build();
    }*/
    }

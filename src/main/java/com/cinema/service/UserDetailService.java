package com.cinema.service;

import com.cinema.domain.Enum.UserRole;
import com.cinema.domain.Member;
import com.cinema.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        // 1. 사용자 ID로 Member 조회
        Optional<Member> _member = this.memberRepository.findById(id);

        // 2. 사용자가 없으면 예외 처리
        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Member member = _member.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 3. 사용자 권한 설정
        if ("ADMIN".equals(member.getRole())) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.name()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.name()));
        }

        // 4. UserDetails 객체 반환
        return new User(member.getEmail(), member.getPassword(), authorities);
    }
}

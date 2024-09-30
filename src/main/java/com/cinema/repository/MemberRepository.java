package com.cinema.repository;

import com.cinema.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String id);  // Optional로 변경
    Optional<Member> findByName(String name); // 이름으로 조회하는 메서드 추가
    Member findByUserID(Long userID);
}
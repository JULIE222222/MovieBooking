package com.cinema.repository;

import com.cinema.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository <Member,Long> {
    Optional<Member> findById(String id);


}

package com.cinema.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "member")
@RequiredArgsConstructor
public class Member {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userID;
    private String id; //로그인시 받는 id값
    private String password; //로그인시 받는 password값
    private String name;
    private String email;
    private String phone;


}

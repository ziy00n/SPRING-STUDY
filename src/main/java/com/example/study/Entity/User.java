package com.example.study.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity // JPA 엔티티다.
@Table(name = "users")// users 라는 테이블과 매칭
@Getter
@Setter
public class User {
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT
    @Column(name = "user_id") //속성명
    private Long userId;

    @Column(name = "username", nullable = false) //null이 아니도록
    private String username;

    @Column(name = "password", nullable = false) // null X 무조건 값있도록
    private String password;
}

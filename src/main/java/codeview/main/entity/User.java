package codeview.main.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false, unique = true)
    private String username;

//    @Column(nullable = false)
    private String password;

//    @Column(nullable = false, unique = true)
    private String name;

//    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String socialId;
}
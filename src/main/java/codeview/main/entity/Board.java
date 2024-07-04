package codeview.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column
    private String title;
//
//    @Column @OneToOne
//    private Content content;
//
//    @Column @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<Comment> commentList;

    public Board(String title) {
        this.title = title;
    }
}
package codeview.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    private String category;

    private int hits;

    @Builder.Default
    @Column(nullable = false)
    private Date createdAt = new Date();
}

package codeview.main.entity;

import codeview.main.dto.CommentDTO;
import codeview.main.dto.comment.CommentRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board boardId;

    @Column
    private String content;

    @Column
    private String createdAt;

//    @ManyToOne
//    @JoinColumn(name = "parentId")
//    private Comment parentComment;
//
//    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
//    private List<Comment> childrenComment = new ArrayList<>();

    @PrePersist
    protected void createTime() {
        createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }


    public Comment(CommentRequest commentRequest){
        this.content = commentRequest.getContent();
    }
    public void addBoard(Board board){
        this.boardId = board;
    }
    public void addUser(User user){
        this.userId = user;
    }

    public void update(CommentRequest commentRequest){
        this.content = commentRequest.getContent();
    }
}


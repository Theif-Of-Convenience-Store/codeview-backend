package codeview.main.entity;

import codeview.main.dto.blog.BlogReqDto;
import codeview.main.dto.blog.BlogResDto;
import codeview.main.dto.board.BoardReqDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Blog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>();

    public Blog(BlogReqDto blogReqDto) {
        this.name = blogReqDto.getName();
    }

    public void update(BlogReqDto blogReqDto) {
        if (blogReqDto != null) {
            this.name = blogReqDto.getName();
        }
    }
    public void addUser(User user) {
        this.user = user;
    }

    public void addBoard(Board board) {
        boardList.add(board);
        board.addBlog(this);
    }
    public void removeBoard(Board board) {
        boardList.remove(board);
        board.addBlog(null);
    }
}

package codeview.main.dto.board;

import codeview.main.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResDto {
    private Long id;
    private String title;

    public BoardResDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
    }
}

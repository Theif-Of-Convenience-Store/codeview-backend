package codeview.main.dto.board;

import codeview.main.entity.Board;
import lombok.Getter;

@Getter
public class BoardResDto {
    private Long id;
    private String content;

    public BoardResDto(Board board) {
        this.id = board.getId();
        this.content = board.getContent();
    }
}

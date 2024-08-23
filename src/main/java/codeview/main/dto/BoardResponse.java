package codeview.main.dto;

import codeview.main.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponse {
    private Long id;
    private String title;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
    }
}

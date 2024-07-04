package codeview.main.controller;

import codeview.main.entity.Board;
import codeview.main.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @PostMapping("/board/write")
    public void boardSave(@RequestBody Board board) {
        boardService.save(board);
    }
}

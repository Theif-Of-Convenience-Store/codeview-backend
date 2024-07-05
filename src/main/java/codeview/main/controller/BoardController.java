package codeview.main.controller;

import codeview.main.dto.BoardDTO;
import codeview.main.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/board/write")
    public ResponseEntity<String> boardSave(@RequestBody BoardDTO boardDTO) {
        try {
            boardService.save(boardDTO);
            return new ResponseEntity<>("Board saved", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to save board",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

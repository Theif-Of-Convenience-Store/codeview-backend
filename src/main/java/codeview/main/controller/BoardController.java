package codeview.main.controller;

import codeview.main.dto.BoardDTO;
import codeview.main.security.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/write")
    public ResponseEntity<String> boardSave(@RequestBody BoardDTO boardDTO) {
        try {
            boardService.save(boardDTO);
            return new ResponseEntity<>("Board saved", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save board", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Long id) {
        Optional<BoardDTO> boardDTO = boardService.findById(id);
        return boardDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        List<BoardDTO> boardDTOs = boardService.findAll();
        return new ResponseEntity<>(boardDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody BoardDTO boardDTO) {
        try {
            boolean isUpdated = boardService.update(id, boardDTO);
            if (isUpdated) {
                return new ResponseEntity<>("Board updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Board not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update board", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id) {
        try {
            boolean isDeleted = boardService.delete(id);
            if (isDeleted) {
                return new ResponseEntity<>("Board deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Board not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete board", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

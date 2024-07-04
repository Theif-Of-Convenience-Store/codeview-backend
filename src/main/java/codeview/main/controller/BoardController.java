package codeview.main.controller;

import codeview.main.dto.ApiResponse;
import codeview.main.dto.BoardRequest;
import codeview.main.dto.BoardResponse;
import codeview.main.entity.Board;
import codeview.main.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable Long id) {
        Optional<Board> findBoardObj = boardService.findBoardById(id);
        if (findBoardObj.isPresent()) {
            BoardResponse response = new BoardResponse(findBoardObj.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/write")
    public ResponseEntity<ApiResponse<BoardResponse>> boardSave(@RequestBody Board board) {
        Board saveBoard = boardService.save(board);
        BoardResponse boardResponse = new BoardResponse(saveBoard);
        ApiResponse<BoardResponse> response = new ApiResponse<>(HttpStatus.CREATED, "Board Write Success", boardResponse);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(
            @PathVariable Long id,
            @RequestBody BoardRequest boardRequest) {
        Board updatedBoard = new Board();
        updatedBoard.setTitle(boardRequest.getTitle());

        try {
            Board savedBoard = boardService.updateBoard(id, updatedBoard);
            BoardResponse responseDto = new BoardResponse(savedBoard);
            return ResponseEntity.ok(responseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BoardResponse> deleteBoard(@PathVariable Long id) {
        try {
            boardService.deleteBoard(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

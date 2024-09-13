package codeview.main.controller;

import codeview.main.global.ApiResponse;
import codeview.main.dto.board.BoardReqDto;
import codeview.main.dto.board.BoardResDto;
import codeview.main.entity.Board;
import codeview.main.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/{blogId}/board")
public class BoardController {
    private final BoardService boardService;


    @PostMapping
    public ApiResponse<BoardResDto> createBoard(Long userId, @PathVariable Long blogId, @RequestBody BoardReqDto boardReqDto) {
        BoardResDto boardResponse = new BoardResDto(boardService.createBoard(userId, blogId, boardReqDto));
        return ApiResponse.createResponse("S100", "Board Write Success", boardResponse);
    }


    @GetMapping
    public ApiResponse<BoardResDto> getBoardById(@RequestParam("boardId") Long boardId) {
        BoardResDto boardResDto = new BoardResDto(boardService.getBoard(boardId));
        return ApiResponse.createResponse("S101", "Success create board", boardResDto);
    }


    @PutMapping
    public ApiResponse<Void> updateBoard(
            Long userId,
            @PathVariable Long blogId,
            @RequestParam("boardId") Long boardId,
            @RequestBody BoardReqDto boardReqDto) {
        boardService.updateBoard(userId, blogId, boardId, boardReqDto);
        return ApiResponse.createResponse("S102", "Success update board");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBoard(
            Long userId,
            @PathVariable Long blogId
    ) {
        boardService.deleteBoard(userId, blogId);
        return ApiResponse.createResponse("S103", "Success delete board");
    }
}

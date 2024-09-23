package codeview.main.controller;

import codeview.main.dto.board.BoardReqDto;
import codeview.main.dto.board.BoardResDto;
import codeview.main.global.ApiResponse;
import codeview.main.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{blogId}/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ApiResponse<BoardResDto> createBoard(Authentication authentication, @PathVariable Long blogId, @RequestBody BoardReqDto boardReqDto) {
        String userEmail = authentication.getName();
        BoardResDto boardResponse = new BoardResDto(boardService.createBoard(userEmail, blogId, boardReqDto));
        return ApiResponse.createResponse("S100", "Board Write Success", boardResponse);
    }

    @GetMapping("/{boardId}")
    public ApiResponse<BoardResDto> getBoard(@PathVariable Long boardId) {
        BoardResDto boardResDto = new BoardResDto(boardService.getBoard(boardId));
        return ApiResponse.createResponse("S101", "Success create board", boardResDto);
    }

    @PutMapping("/{boardId}")
    public ApiResponse<Void> updateBoard(Authentication authentication, @PathVariable Long blogId, @PathVariable Long boardId, @RequestBody BoardReqDto boardReqDto) {
        String userEmail = authentication.getName();
        boardService.updateBoard(userEmail, blogId, boardId, boardReqDto);
        return ApiResponse.createResponse("S102", "Success update board");
    }

    @DeleteMapping("/{boardId}")
    public ApiResponse<Void> deleteBoard(Authentication authentication, @PathVariable Long blogId, @PathVariable Long boardId) {
        String userEmail = authentication.getName();
        boardService.deleteBoard(userEmail, blogId, boardId);
        return ApiResponse.createResponse("S103", "Success delete board");
    }
}

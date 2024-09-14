package codeview.main.service;

import codeview.main.dto.board.BoardReqDto;
import codeview.main.entity.Blog;
import codeview.main.entity.Board;
import codeview.main.exception.BusinessException;
import codeview.main.exception.code.CommonErrorCode;
import codeview.main.exception.code.UserErrorCode;
import codeview.main.repository.BlogRepository;
import codeview.main.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public Board createBoard(Long userId, Long blogId, BoardReqDto boardReqDto){
        Blog blog = blogRepository.findByIdAndUser_Id(blogId, userId).orElseThrow(() -> new BusinessException(UserErrorCode.UNAUTHORIZED_USER));
        Board board = new Board(boardReqDto);
        blog.addBoard(board);
        boardRepository.save(board);
        return board;
    }
    @Transactional
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new BusinessException(CommonErrorCode.NOT_FOUND_RESOURCE));
    }
    @Transactional
    public void updateBoard(Long userId, Long blogId, Long boardId, BoardReqDto boardReqDto) {
        Board board = boardRepository.findByIdAndBlog_Id(boardId, blogId).orElseThrow(() -> new BusinessException(CommonErrorCode.NOT_FOUND_RESOURCE));
        if (board.getBlog().getUser().getId().equals(userId)) {
            board.update(boardReqDto);
        }
    }
    @Transactional
    public void deleteBoard(Long userId, Long blogId) {
        Board board = boardRepository.findById(blogId).orElseThrow(() -> new BusinessException(CommonErrorCode.NOT_FOUND_RESOURCE));
        Blog blog = board.getBlog();
        if (board.getBlog().getUser().getId().equals(userId)) {
            blog.removeBoard(board);
        }
    }
}

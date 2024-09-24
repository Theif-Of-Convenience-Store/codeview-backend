package codeview.main.service;

import codeview.main.dto.board.BoardReqDto;
import codeview.main.entity.Board;
import codeview.main.entity.Blog;
import codeview.main.entity.User;
import codeview.main.repository.BoardRepository;
import codeview.main.repository.BlogRepository;
import codeview.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Transactional
    public Board createBoard(String userEmail, Long blogId, BoardReqDto boardReqDto) {
        Blog blog = blogRepository.findById(blogId).orElseThrow();
        Board board = Board.builder()
                .content(boardReqDto.getContent())
                .category(boardReqDto.getCategory())
                .build();
        board.setBlog(blog);
        return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow();
    }

    @Transactional
    public void updateBoard(String userEmail, Long blogId, Long boardId, BoardReqDto boardReqDto) {
        Board board = getBoard(boardId);
        board.setContent(boardReqDto.getContent());
        board.setCategory(boardReqDto.getCategory());
        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(String userEmail, Long blogId, Long boardId) {
        Board board = getBoard(boardId);
        boardRepository.delete(board);
    }
}

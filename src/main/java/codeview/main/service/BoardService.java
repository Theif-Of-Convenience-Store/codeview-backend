package codeview.main.service;

import codeview.main.dto.BoardDTO;
import codeview.main.entity.Board;
import codeview.main.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDTO boardDTO) {
        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        boardRepository.save(board);
    }
}

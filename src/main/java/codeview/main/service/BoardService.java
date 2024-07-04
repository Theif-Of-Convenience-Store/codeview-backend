package codeview.main.service;

import codeview.main.entity.Board;
import codeview.main.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(Board board) {
        boardRepository.save(board);
    }
}

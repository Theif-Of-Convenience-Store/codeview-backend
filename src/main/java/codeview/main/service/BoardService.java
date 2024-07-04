package codeview.main.service;

import codeview.main.entity.Board;
import codeview.main.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public Board save(Board board) {
        return boardRepository.save(board);
    }
    public Optional<Board> findBoardById(Long id) {
        return boardRepository.findById(id);
    }
    public Board updateBoard(Long id, Board updateBoard) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(updateBoard.getTitle());
                    return board;
                })
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }
    public void deleteBoard(Long id) {
        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id);
        } else {
            throw new RuntimeException("Board not found");
        }
    }
}

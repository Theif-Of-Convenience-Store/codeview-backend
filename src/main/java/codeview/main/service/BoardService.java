package codeview.main.service;

import codeview.main.dto.BoardDTO;
import codeview.main.entity.Board;
import codeview.main.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        boardRepository.save(board);
    }

    public Optional<BoardDTO> findById(Long id) {
        return boardRepository.findById(id).map(board -> {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setTitle(board.getTitle());
            return boardDTO;
        });
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll().stream().map(board -> {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setTitle(board.getTitle());
            return boardDTO;
        }).collect(Collectors.toList());
    }

    public boolean update(Long id, BoardDTO boardDTO) {
        Optional<Board> boardOpt = boardRepository.findById(id);
        if (boardOpt.isPresent()) {
            Board board = boardOpt.get();
            board.setTitle(boardDTO.getTitle());
            boardRepository.save(board);
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

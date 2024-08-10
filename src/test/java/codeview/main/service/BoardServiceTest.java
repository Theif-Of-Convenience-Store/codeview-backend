//package codeview.main.service;
//
//import codeview.main.dto.BoardDTO;
//import codeview.main.entity.Board;
//import codeview.main.repository.BoardRepository;
//import codeview.main.security.service.BoardService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class BoardServiceTest {
//
//    @Mock
//    private BoardRepository boardRepository;
//
//    @InjectMocks
//    private BoardService boardService;
//
//    @Test
//    public void testSaveBoard() {
//        BoardDTO boardDTO = new BoardDTO();
//        boardDTO.setTitle("Test Title");
//
//        Board board = new Board();
//        board.setTitle(boardDTO.getTitle());
//
//        when(boardRepository.save(any(Board.class))).thenReturn(board);
//
//        boardService.save(boardDTO);
//
//        verify(boardRepository, times(1)).save(any(Board.class));
//    }
//}

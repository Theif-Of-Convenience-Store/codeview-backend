//package codeview.main.controller;
//
//import codeview.main.dto.BoardDTO;
//import codeview.main.security.service.BoardService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.verify;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.test.web.servlet.request.MockMvcBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.mockito.ArgumentMatchers.any;
//
//@ExtendWith(MockitoExtension.class)
//public class BoardControllerTest {
//
//    @Mock
//    private BoardService boardService;
//
//    @InjectMocks
//    private BoardController boardController;
//
//    @Test
//    public void testBoardSave() throws Exception {
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
//
//        BoardDTO boardDTO = new BoardDTO();
//        boardDTO.setTitle("Test Title");
//
//        doNothing().when(boardService).save(any(BoardDTO.class));
//
//        mockMvc.perform(post("/board/write")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"title\": \"Test Title\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(content().string("Board saved"));
//
//        verify(boardService).save(any(BoardDTO.class));
//    }
//}

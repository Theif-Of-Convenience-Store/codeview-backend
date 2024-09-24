package codeview.main.service;

import codeview.main.dto.CommentDTO;
import codeview.main.dto.comment.CommentRequest;
import codeview.main.entity.Board;
import codeview.main.entity.Comment;
import codeview.main.entity.User;
import codeview.main.exception.BusinessException;
import codeview.main.repository.BoardRepository;
import codeview.main.repository.CommentRepository;
import codeview.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest // Spring Boot 애플리케이션 전체를 로드하여 통합 테스트
@Transactional
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;  // 실제 서비스 계층

    @Autowired
    private CommentRepository commentRepository;  // 실제 리포지토리

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        // 가상의 사용자와 게시판을 데이터베이스에 추가
        User user = new User();
        user.setId(1L);
        userRepository.save(user);

        Board board = new Board();
        board.setTitle("Test Board");
        boardRepository.save(board);
    }
    @Test
    void testCreateComment_Success() {
        // Given: 테스트 데이터 준비
        Long userId = 1L;  // 존재하는 사용자 ID로 설정해야 함
        Long boardId = 1L;  // 존재하는 게시판 ID로 설정해야 함
        CommentRequest commentRequest = new CommentRequest("This is a test comment");

        // When: 서비스 메서드를 호출하여 댓글 생성
        Comment comment = commentService.createComment(boardId, userId, commentRequest);

        // Then: 댓글이 성공적으로 생성되었는지 확인
        assertNotNull(comment);
        assertEquals("This is a test comment", comment.getContent());
        assertNotNull(comment.getUserId());
        assertNotNull(comment.getBoardId());

        // 저장된 데이터가 실제로 존재하는지 확인
        Comment savedComment = commentRepository.findById(comment.getId()).orElse(null);
        assertNotNull(savedComment);
        assertEquals(comment.getContent(), savedComment.getContent());
    }

    @Test
    void testCreateComment_UserNotFound() {
        // Given: 존재하지 않는 사용자 ID 사용
        Long userId = 999L;  // 존재하지 않는 사용자 ID
        Long boardId = 1L;   // 존재하는 게시판 ID
        CommentRequest commentRequest = new CommentRequest("This is a test comment");

        // When & Then: 예외가 발생하는지 확인
        assertThrows(BusinessException.class, () -> {
            commentService.createComment(boardId, userId, commentRequest);
        });
    }

    @Test
    void testCreateComment_BoardNotFound() {
        // Given: 존재하지 않는 게시판 ID 사용
        Long userId = 1L;   // 존재하는 사용자 ID
        Long boardId = 999L;  // 존재하지 않는 게시판 ID
        CommentRequest commentRequest = new CommentRequest("This is a test comment");

        // When & Then: 예외가 발생하는지 확인
        assertThrows(BusinessException.class, () -> {
            commentService.createComment(boardId, userId, commentRequest);
        });
    }




}

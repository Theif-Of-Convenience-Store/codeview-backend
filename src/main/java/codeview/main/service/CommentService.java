package codeview.main.service;

import codeview.main.dto.CommentDTO;
import codeview.main.dto.comment.CommentRequest;
import codeview.main.dto.comment.CommentResponse;
import codeview.main.entity.Board;
import codeview.main.entity.Comment;
import codeview.main.entity.NoticeType;
import codeview.main.entity.User;
import codeview.main.exception.BusinessException;
import codeview.main.exception.code.CommonErrorCode;
import codeview.main.exception.code.UserErrorCode;
import codeview.main.repository.BoardRepository;
import codeview.main.repository.CommentRepository;
import codeview.main.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private NotificationService notificationService;
    @Transactional
    public Comment createComment(Long BoardId, Long userId, CommentRequest commentRequest){
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(UserErrorCode.UNAUTHORIZED_USER));
        Board board = boardRepository.findById(BoardId).orElseThrow(() -> new BusinessException(CommonErrorCode.NOT_FOUND_RESOURCE));
        Comment comment = new Comment(commentRequest);
        comment.addUser(user);
        comment.addBoard(board);

        commentRepository.save(comment);
        log.info(comment.toString());


        String massage = "게시글에 댓글이 달렸어요";

        notificationService.send(user,NoticeType.Comment,massage,String.valueOf(BoardId));

        return comment;
    }

    @Transactional
    public List<CommentResponse> getComment(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new BusinessException(CommonErrorCode.NOT_FOUND_RESOURCE));
        List<Comment> comments = commentRepository.findByBoardId(board);

        return comments.stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getUserId().getId(),
                        comment.getContent(),
                        comment.getCreatedAt()))
                .collect(Collectors.toList());

    }


    @Transactional
    public void updateComment(Long BoardId, Long userId, Long commentId, CommentRequest commentRequest){
        Comment comment = commentRepository.findByIdAndBoardId_Id(commentId, BoardId);
        if(comment.getUserId().getId().equals(userId)){
            comment.update(commentRequest);
        }
    }

    @Transactional
    public void deleteComment(Long BoardId, Long userId, Long commentId){
        Comment comment = commentRepository.findByIdAndBoardId_Id(commentId, BoardId);
        if(comment.getUserId().getId().equals(userId)){
            commentRepository.delete(comment);
        }
    }



    public CommentDTO addComment(Long id, CommentDTO replyDto){
        return null;
    }

}

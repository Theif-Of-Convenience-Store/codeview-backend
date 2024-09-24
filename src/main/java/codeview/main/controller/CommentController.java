package codeview.main.controller;

import codeview.main.dto.CommentDTO;
import codeview.main.dto.comment.CommentRequest;
import codeview.main.dto.comment.CommentResponse;
import codeview.main.entity.Comment;
import codeview.main.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/{boardId}/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @PostMapping
    public ResponseEntity<String> saveComment(@PathVariable Long boardId, Authentication authentication, @RequestBody CommentRequest commentRequest) {

        try {
            String userEmail = authentication.getName();
            Comment comment = commentService.createComment(boardId, userEmail, commentRequest);
            log.info(comment.toString());
            return ResponseEntity.ok("성공");
        } catch (Exception e) {
            log.error("Error saving comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 저장 중 오류가 발생했습니다.");
        }
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComment(@PathVariable Long boardId){
        try{
            List<CommentResponse> commentResponse = commentService.getComment(boardId);
            return ResponseEntity.ok(commentResponse);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateComment(@PathVariable Long boardId,
                                              Authentication authentication,
                                              @RequestParam Long commentId ,
                                              @RequestBody CommentRequest commentRequest){

        try{
            String userEmail = authentication.getName();
            commentService.updateComment(boardId,userEmail,commentId,commentRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long boardId,
            Authentication authentication,
            Long id
    ){
        try {
            String userEmail = authentication.getName();
            commentService.deleteComment(boardId,userEmail,id);
            log.info(String.valueOf(id));
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}

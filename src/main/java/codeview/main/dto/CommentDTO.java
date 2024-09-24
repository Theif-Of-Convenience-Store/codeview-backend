package codeview.main.dto;

import codeview.main.entity.Board;
import codeview.main.entity.Comment;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private Long userId;
    private Long boardId;
    private String content;
    private String createdAt;
    private CommentDTO  parentId;
    private List<CommentDTO> replies;




}



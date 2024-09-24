package codeview.main.repository;

import codeview.main.entity.Board;
import codeview.main.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardId(Board board);

    Comment findByIdAndBoardId_Id(Long id, Long board);

}

package codeview.main.repository;

import codeview.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndUser_Id(Long userId, Long blogId);
}

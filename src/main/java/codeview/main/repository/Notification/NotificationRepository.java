package codeview.main.repository.Notification;

import codeview.main.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    Page<Notification> findAllByUserId(Long userId, Pageable pageable);

    List<Notification> findAllByUserIdAndIsReadFalse(Long userId);



}
package codeview.main.service;

import codeview.main.dto.Notification.NotificationResponse;
import codeview.main.entity.NoticeType;
import codeview.main.entity.Notification;
import codeview.main.entity.User;
import codeview.main.repository.Notification.EmitterRepositoryImpl;
import codeview.main.repository.Notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    @Autowired
    private EmitterRepositoryImpl emitterRepository;

    @Autowired
    private NotificationRepository notificationRepository;
    public SseEmitter createEmitter(Long userId, String lastEventId) {

        String emitterId = userId + "_" + System.currentTimeMillis();

        SseEmitter emitter = emitterRepository.saveEmitter(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(()->emitterRepository.deleteEmitter(emitterId));
        emitter.onTimeout(()->emitterRepository.deleteEmitter(emitterId));
        emitter.onError((e) -> {
            log.error("Emitter error: " + emitterId + ", error: " + e.getMessage());
            emitterRepository.deleteEmitter(emitterId);
        });

        sendToClient(emitter,emitterId,"EventStream Create.[userId= "+userId + "]");
        log.info("Last-Event-ID: " + lastEventId);
        if(!lastEventId.isEmpty()){
            Map<String, Object> events = emitterRepository.findAllEventCachesByUserId(userId);
            log.info("캐시된 이벤트 개수: " + events.size());
            events.forEach((key, value) -> {
                log.info("Cached Event ID: " + key + ", Data: " + value);
            });

            events.entrySet().stream()
                    .filter(entry -> {
                        boolean shouldSend = lastEventId.compareTo(entry.getKey()) < 0;
                        if (shouldSend) {
                            log.info("Sending cached event: ID = " + entry.getKey() + ", Data = " + entry.getValue());
                        } else {
                            log.info("Skipping cached event: ID = " + entry.getKey() + " (lastEventId = " + lastEventId + ")");
                        }
                        return shouldSend;
                    })
                    .forEach(entry -> sendToClient(emitter,entry.getKey(),entry.getValue()));
        }

        return emitter;
    }

    @Async
    @Transactional
    public void send(User user, NoticeType noticeType, String message, String data) {
        log.info("Sending notification to user: " + user.getId());
        Notification notification = notificationRepository.save(createNotification(user, noticeType, message, data));

        String userId = String.valueOf(user.getId());
        String eventId = userId + "_" + System.currentTimeMillis();

        Map<String, SseEmitter> emitters = emitterRepository.findAllEmittersByUserId(Long.valueOf(userId));

        emitters.forEach((key, emitter) -> {
            emitterRepository.saveEventCache(key, notification);
            log.info("id는: "+ notification.getId().toString());
            log.info(emitterRepository.findAllEmittersByUserId(1L).toString());
            sendToClient(emitter, key, NotificationResponse.creat(notification));
        });
    }

    private Notification createNotification(User user, NoticeType noticeType, String message, String data) {
        return Notification.builder()
                .user(user)
                .noticeType(noticeType)
                .message(message)
                .data(data)
                .isRead(false)
                .build();
    }

    private void sendToClient(SseEmitter emitter, String eventId, Object data) {
        try {
            log.info("성공");
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("notification")
                    .data(data));
        }catch (Exception e) {
            log.error("Failed to send SSE notification to client: " + eventId, e);
            emitterRepository.deleteEmitter(eventId);
        }
    }

    private void delete(Long userId, Long notificationId){


    }

}

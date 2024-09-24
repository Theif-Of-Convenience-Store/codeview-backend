package codeview.main.repository.Notification;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {
    SseEmitter saveEmitter(String emitterId, SseEmitter emitter);

    void deleteEmitter(String emitterId);

    void saveEventCache(String eventId, Object event);

    Map<String, SseEmitter> findAllEmittersByUserId(Long userId);

    Map<String, Object> findAllEventCachesByUserId(Long userId);
}

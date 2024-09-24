package codeview.main.repository.Notification;

import codeview.main.entity.Notification;
import codeview.main.repository.Notification.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
@Slf4j
@Repository
public class EmitterRepositoryImpl implements EmitterRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();


    @Override
    public SseEmitter saveEmitter(String emitterId, SseEmitter emitter) {
        emitters.put(emitterId,emitter);

        return emitter;
    }

    @Override
    public void deleteEmitter(String emitterId) {
        emitters.remove(emitterId);
    }

    @Override
    public void saveEventCache(String eventId, Object event) {
        eventCache.put(eventId,event);
        log.info("Event cached: ID = " + eventId + ", Event = " + event.toString());
    }

    @Override
    public Map<String, SseEmitter> findAllEmittersByUserId(Long userId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(String.valueOf(userId)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    @Override
    public Map<String, Object> findAllEventCachesByUserId(Long userId) {
        Map<String, Object> cachedEvents = eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(String.valueOf(userId)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        log.info("Retrieved cached events for user " + userId + ": " + cachedEvents.toString());
        return cachedEvents;
    }
}

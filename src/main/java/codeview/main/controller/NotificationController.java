package codeview.main.controller;

import codeview.main.dto.Notification.NotificationResponse;
import codeview.main.entity.Notification;
import codeview.main.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamNotifications(Authentication authentication,
                                          @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId){
        Long userId = Long.parseLong(authentication.getName());
        return service.createEmitter(userId,lastEventId);
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<Page<NotificationResponse>> getNotifications(@PathVariable Long id,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<NotificationResponse> notifications = service.getNotifications(id, pageable);
        return ResponseEntity.ok(notifications);
    }


}

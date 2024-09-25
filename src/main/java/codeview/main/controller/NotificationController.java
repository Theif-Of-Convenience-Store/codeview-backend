package codeview.main.controller;

import codeview.main.auth.dto.model.PrincipalDetails;
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
    public SseEmitter streamNotifications(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                          @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId){

        return service.createEmitter(principalDetails.getId(), lastEventId);
    }

    @GetMapping("/notification")
    public ResponseEntity<Page<NotificationResponse>> getNotifications(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<NotificationResponse> notifications = service.getNotifications(principalDetails.getId(), pageable);
        return ResponseEntity.ok(notifications);
    }


}

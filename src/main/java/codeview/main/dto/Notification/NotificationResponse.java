package codeview.main.dto.Notification;

import codeview.main.entity.NoticeType;
import codeview.main.entity.Notification;
import codeview.main.entity.User;
import lombok.*;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long userId;
    private NoticeType noticeType;
    private String message;
    private String url;
    private Boolean isRead;
    private String createdAt;

    public static NotificationResponse creat(Notification notification){
        return NotificationResponse.builder()
                .userId(notification.getUser().getId())
                .message(notification.getMessage())
                .url(notification.getData())
                .createdAt(notification.getCreatedAt())
                .build();
    }




}

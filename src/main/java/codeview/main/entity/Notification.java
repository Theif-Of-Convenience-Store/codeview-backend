package codeview.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column
    private NoticeType noticeType;

    @Column
    private String message;

    @Column
    private String data;

    @Column
    private Boolean isRead = false;

    @Column
    private String createdAt;

    @PrePersist
    protected void createTime() {
        createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }


}

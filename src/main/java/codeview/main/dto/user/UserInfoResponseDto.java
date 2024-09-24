package codeview.main.dto.user;

import codeview.main.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {
    private Long id;
    private String email;
    private String name;
    private String logo;
    private String intro;
    private String state;

    public UserInfoResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.logo = user.getLogo();
        this.intro = user.getIntro();
    }
}

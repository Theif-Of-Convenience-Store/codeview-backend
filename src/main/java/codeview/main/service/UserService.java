package codeview.main.service;

import codeview.main.dto.user.UserInfoResponseDto;
import codeview.main.entity.User;
import codeview.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional
    public User updateUserInfo(String email, UserInfoResponseDto userInfoDto) {
        User user = findByEmail(email);
        user.setName(userInfoDto.getName());
        user.setIntro(userInfoDto.getIntro());
        user.setLogo(userInfoDto.getLogo());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String email) {
        User user = findByEmail(email);
        userRepository.delete(user);
    }
}

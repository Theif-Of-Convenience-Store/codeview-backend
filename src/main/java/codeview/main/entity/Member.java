package codeview.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberKey;
    private String name;
    private String email;
    private String password;
    private String profile;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    public String getRoleKey() {
        return role.getKey();
    }

    public enum Role {
        ROLE_USER(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        private final List<GrantedAuthority> authorities;

        Role(List<GrantedAuthority> authorities) {
            this.authorities = authorities;
        }

        public List<GrantedAuthority> getAuthorities() {
            return authorities;
        }

        public String getKey() {
            return name();
        }
    }
}

package hanium.dongguk.global.security.info;

import hanium.dongguk.user.core.domain.ERole;
import hanium.dongguk.user.core.dto.UserSecurityForm;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final UUID userId;
    private final String password;
    private final ERole role;
    private final Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(UserSecurityForm securityForm){
        return UserPrincipal.builder()
                .userId(securityForm.getId())
                .password(securityForm.getPassword())
                .role(securityForm.getRole())
                .authorities(
                        Collections.singleton(
                                new SimpleGrantedAuthority(securityForm.getRole().getSecurityRole())
                        )
                )
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

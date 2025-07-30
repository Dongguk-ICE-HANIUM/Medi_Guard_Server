package hanium.dongguk.user.core.dto;


import hanium.dongguk.user.core.domain.ERole;
import hanium.dongguk.user.core.domain.User;

import java.util.UUID;

public interface UserSecurityForm {
    UUID getId();
    ERole getRole();
    String getPassword();

    static UserSecurityForm invoke(User user){
        return new UserSecurityForm() {
            @Override
            public UUID getId() {
                return user.getId();
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public ERole getRole() {
                return user.getRole();
            }
        };
    }
}

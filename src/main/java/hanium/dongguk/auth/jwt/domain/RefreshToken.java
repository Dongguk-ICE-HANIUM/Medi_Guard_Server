package hanium.dongguk.auth.jwt.domain;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken {
    @Id
    private UUID id;

    @Indexed
    private String token;

    @Builder
    public RefreshToken(UUID id, String token) {
        this.id = id;
        this.token = token;
    }

    public static RefreshToken issueRefreshToken(
            final UUID userId,
            final String refreshToken
    ) {
        return new RefreshToken(userId, refreshToken);
    }

    public void updateRefreshToken(final String refreshToken) {
        this.token = refreshToken;
    }
}

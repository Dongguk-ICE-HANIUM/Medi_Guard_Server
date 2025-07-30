package hanium.dongguk.auth.jwt.service;

import hanium.dongguk.auth.jwt.domain.RefreshToken;
import hanium.dongguk.auth.jwt.domain.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void updateRefreshToken(UUID userId, String refreshToken) {
        refreshTokenRepository.findById(userId)
                .ifPresentOrElse(
                        existingToken -> {
                            refreshTokenRepository.deleteById(userId);
                            refreshTokenRepository.save(RefreshToken.issueRefreshToken(userId, refreshToken));
                        },
                        () -> refreshTokenRepository.save(RefreshToken.issueRefreshToken(userId, refreshToken))
                );
    }

    @Transactional
    public void deleteRefreshToken(UUID userId){
        refreshTokenRepository.deleteById(userId);
    }
}

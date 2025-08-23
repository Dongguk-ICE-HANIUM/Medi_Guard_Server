package hanium.dongguk.user.core.domain;

import hanium.dongguk.auth.exception.AuthErrorCode;
import hanium.dongguk.global.exception.CommonException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    @Column(name = "email", updatable = false, nullable = false, unique = true)
    private String email;

    public static Email of(String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new CommonException(AuthErrorCode.INVALID_EMAIL_FORMAT);
        }
        return new Email(email);
    }

    private Email(String email) {
        this.email = email;
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

package hanium.dongguk.global.constants;

import java.util.List;

public class Constants {
    public static String CLAIM_USER_ID = "uuid";
    public static String CLAIM_USER_ROLE = "role";
    public static String PREFIX_BEARER = "Bearer ";
    public static String PREFIX_AUTH = "Authorization";
    public static String ACCESS_COOKIE_NAME = "access_token";
    public static String REFRESH_COOKIE_NAME = "refresh_token";
    public static String APPLE_BASEURL = "https://appleid.apple.com/auth";
    public static String GOOGLE_BASEURL = "https://www.googleapis.com/oauth2/v1";

    public static List<String> NO_NEED_AUTH = List.of(
            "/swagger",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api-docs",
            "/api-docs/**",
            "/v3/api-docs/**",
            "/api/healthz",
            "/api/health-check",
            "/api/auth/normal/login",
            "/api/auth/register"
    );
}

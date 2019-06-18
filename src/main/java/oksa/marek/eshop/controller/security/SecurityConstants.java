package oksa.marek.eshop.controller.security;

public class SecurityConstants {
    public static final String SECRET = "SuperSecretKeyToGenJWT";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    static final String REGISTER_URL = "/public/register";
    static final String AUTHENTICATED_URLS = "/user/**";
    static final String ADMIN_URLS = "/admin/**";

    public static final String ADMIN_AUTH = "ADMIN";
    public static final String USER_AUTH = "USER";
}

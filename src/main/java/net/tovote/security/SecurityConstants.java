package net.tovote.security;

public class SecurityConstants {
    public static final String SECRET = "QuickstepIsAwesome";
    public static final long EXPIRATION_TIME = 7200_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
}

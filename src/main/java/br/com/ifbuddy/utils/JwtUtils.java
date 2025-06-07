package br.com.ifbuddy.utils;

import io.quarkus.security.UnauthorizedException;

public class JwtUtils {
  public static String getBearerToken(String authHeader) throws UnauthorizedException {
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    throw new UnauthorizedException();
  }
}

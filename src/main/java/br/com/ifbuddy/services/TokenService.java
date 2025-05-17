package br.com.ifbuddy.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.jboss.logmanager.Logger;
import org.jose4j.jwt.JwtClaims;

import br.com.ifbuddy.enums.Roles;
import br.com.ifbuddy.utils.TokenUtils;
import jakarta.enterprise.context.RequestScoped;

import java.util.Arrays;
import java.util.UUID;

@RequestScoped
public class TokenService {
  @ConfigProperty(name = "mp.jwt.verify.issuer")
  private static String issuer;

  public final static Logger LOGGER = Logger.getLogger(TokenService.class.getSimpleName());

  public String generateUserToken(String email, String username) {
    return generateToken(email, username, Roles.USER);
  }

  public String generateToken(String subject, String name, String... roles) {
    try {
      JwtClaims jwtClaims = new JwtClaims();
      jwtClaims.setIssuer(issuer);
      jwtClaims.setJwtId(UUID.randomUUID().toString());
      jwtClaims.setSubject(subject);
      jwtClaims.setClaim(Claims.upn.name(), subject);
      jwtClaims.setClaim(Claims.preferred_username.name(), name);
      jwtClaims.setClaim(Claims.groups.name(), Arrays.asList(roles));
      jwtClaims.setAudience("using-jwt");
      jwtClaims.setExpirationTimeMinutesInTheFuture(60);

      String token = TokenUtils.generateTokenString(jwtClaims);
      LOGGER.info("TOKEN generated: " + token);
      return token;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public JwtClaims validateToken(String token) {
    try {
      return TokenUtils.validateToken(token);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}

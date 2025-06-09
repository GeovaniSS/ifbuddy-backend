package br.com.ifbuddy.utils;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@ApplicationScoped
public class TokenUtils {

    @Inject
    @ConfigProperty(name = "PRIVATE_KEY_STRING")
    String privateKeyPem;

    @Inject
    @ConfigProperty(name = "PUBLIC_KEY_STRING")
    String publicKeyPem;

    @Inject
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public String generateTokenString(JwtClaims claims) throws Exception {
        PrivateKey pk = decodePrivateKey(privateKeyPem);
        return generateTokenString(pk, "ifbuddy", claims);
    }

    private String generateTokenString(PrivateKey privateKey, String kid, JwtClaims claims) throws Exception {
        long currentTimeInSecs = currentTimeInSecs();

        claims.setIssuedAt(NumericDate.fromSeconds(currentTimeInSecs));
        claims.setClaim(Claims.auth_time.name(), NumericDate.fromSeconds(currentTimeInSecs));

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(privateKey);
        jws.setKeyIdHeaderValue(kid);
        jws.setHeader("typ", "JWT");
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        return jws.getCompactSerialization();
    }

    public JwtClaims validateToken(String token) throws Exception {
        PublicKey publicKey = decodePublicKey(publicKeyPem);

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
            .setRequireExpirationTime()
            .setAllowedClockSkewInSeconds(30)
            .setExpectedIssuer(issuer)
            .setExpectedAudience("using-jwt")
            .setVerificationKey(publicKey)
            .build();

        return jwtConsumer.processToClaims(token);
    }

    private static PrivateKey decodePrivateKey(String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private static PublicKey decodePublicKey(String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(keySpec);
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        System.out.println(normalizedPem);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private static String removeBeginEnd(String pem) {
        pem = pem.replace("\\n", "\n");

        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)-----", "");

        pem = pem.replaceAll("\\s+", "");

        return pem.trim();
    }

    public static int currentTimeInSecs() {
        return (int) (System.currentTimeMillis() / 1000);
    }
}

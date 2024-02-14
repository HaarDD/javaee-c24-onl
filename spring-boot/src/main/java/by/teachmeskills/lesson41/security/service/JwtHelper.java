package by.teachmeskills.lesson41.security.service;

import by.teachmeskills.lesson41.entity.enums.UserRoleEnum;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class JwtHelper {

    private static final String JWT_TOKEN_CLAIM = "token";

    private static final String JWT_PRIVILEGE_CLAIM = "privilege";

    private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);

    private final JWSSigner jwtSigner;
    private final JWSVerifier jwsVerifier;
    private final Duration expiration;

    public JwtHelper(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Duration expiration) throws JOSEException {
        this.jwtSigner = new MACSigner(secret);
        this.jwsVerifier = new MACVerifier(secret);
        this.expiration = expiration;
    }

    @SneakyThrows
    public String generateToken(String issuer, Authentication userInfo) {
        final Pair<Date, Date> issueAndExpirationTimes = getIssueAndExpirationTimes();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(userInfo.getName())
                .claim(JWT_TOKEN_CLAIM, UUID.randomUUID().toString())
                .claim(JWT_PRIVILEGE_CLAIM, userInfo.getAuthorities())
                .claim(JWT_PRIVILEGE_CLAIM, userInfo.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
                )
                .issueTime(issueAndExpirationTimes.getLeft())
                .expirationTime(issueAndExpirationTimes.getRight())
                .build();
        SignedJWT signedJWT = new SignedJWT(JWT_HEADER, claimsSet);

        signedJWT.sign(jwtSigner);

        return signedJWT.serialize();
    }


    @Nullable
    public UserDetails getTokenClaims(String token) throws JOSEException {
        final JWTClaimsSet jwtClaims;
        try {
            final SignedJWT decodedJWT = SignedJWT.parse(token);
            if (decodedJWT.verify(jwsVerifier) && isValid(jwtClaims = decodedJWT.getJWTClaimsSet())) {
                String userName = decodedJWT.getJWTClaimsSet().getSubject();
                final Stream<UserRoleEnum> userRights = this.<List<String>>getClaim(jwtClaims, JWT_PRIVILEGE_CLAIM)
                        .map(list -> list.stream().map(UserRoleEnum::valueOf))
                        .orElse(Stream.empty());
                return new User(userName, StringUtils.EMPTY, userRights.map(UserRoleEnum::name)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
                );
            }
        } catch (ParseException pe) {
            log.error("Невалидный token: {}", token, pe);
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    private <T> Optional<T> getClaim(JWTClaimsSet jwtClaims, String claim) {
        return Optional.ofNullable((T) jwtClaims.getClaim(claim));
    }

    private Pair<Date, Date> getIssueAndExpirationTimes() {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        Instant expirationAt = issuedAt.plus(expiration);
        return Pair.of(Date.from(issuedAt), Date.from(expirationAt));
    }

    public boolean isValid(JWTClaimsSet jwtClaims) {
        Date referenceTime = new Date();
        Date expirationTime = jwtClaims.getExpirationTime();
        Date notBeforeTime = jwtClaims.getNotBeforeTime();
        boolean expired = expirationTime != null && expirationTime.before(referenceTime);
        boolean yetValid = notBeforeTime == null || notBeforeTime.before(referenceTime);
        return !expired && yetValid;
    }


}

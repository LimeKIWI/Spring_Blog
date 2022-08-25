package com.blog.hw3.security.provider;

import com.blog.hw3.entity.RefreshToken;
import com.blog.hw3.repository.RefreshTokenRepositroy;
import com.blog.hw3.security.UserDetailServiceImp;
import com.blog.hw3.security.dto.TokenDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    private static final String SECRET = "SPARTA@1242351231231245352346#$%";

    private final Key key;

    private final UserDetailServiceImp userDetailServiceImp;

    private final RefreshTokenRepositroy refreshTokenRepositroy;

    public JwtProvider(UserDetailServiceImp userDetailServiceImp, RefreshTokenRepositroy refreshTokenRepositroy) {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
        this.userDetailServiceImp = userDetailServiceImp;
        this.refreshTokenRepositroy = refreshTokenRepositroy;
    }

    // 토큰만들기
    public TokenDto generateTokenDto(Authentication authentication) {

        long now = (new Date()).getTime();

        Date accessTokenExpIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(accessTokenExpIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new TokenDto(BEARER_TYPE, accessToken, refreshToken, accessTokenExpIn.getTime());
    }

    // 권한정보받기
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        UserDetails userDetails = userDetailServiceImp.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Wrong JWT ");
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Expired JWT  ");
        } catch (UnsupportedJwtException e) {
            throw new IllegalArgumentException("Unsupported JWT ");
        }
    }

    public void validateRefreshToken(String token, String key) {
        RefreshToken refreshToken = refreshTokenRepositroy.findByKey(key)
                .orElseThrow(() -> new IllegalArgumentException("logout user."));
        if (!refreshToken.getValue().equals(token)) {
            throw new IllegalArgumentException("Not matched Token Userinfo.");
        }
    }

    // 헤더에서 토큰을 가져와 앞 7자리 "bearer "를 때낸 뒤 토큰값을 반환
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE) && bearerToken.length()>7) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

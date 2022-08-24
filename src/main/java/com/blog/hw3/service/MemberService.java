package com.blog.hw3.service;

import com.blog.hw3.dto.SigninRequestDto;
import com.blog.hw3.dto.SignupRequestDto;
import com.blog.hw3.entity.Member;
import com.blog.hw3.entity.RefreshToken;
import com.blog.hw3.repository.MemberRepository;
import com.blog.hw3.repository.RefreshTokenRepositroy;
import com.blog.hw3.security.dto.TokenDto;
import com.blog.hw3.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepositroy refreshTokenRepositroy;
    private final JwtProvider jwtProvider;

    // 회원가입
    public String registerUser(SignupRequestDto signupRequestDto) {
        String nickName = signupRequestDto.getNickName();
        String password = signupRequestDto.getPassword();
        String passwordConfirm = signupRequestDto.getPasswordConfirm();

        Optional<Member> found = memberRepository.findByNickName(nickName);
        if(found.isPresent()) {
            throw new IllegalArgumentException("중복 닉네임 확인!");
        }
        if(!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("비밀번호가 서로 다릅니다!");
        }
        password = passwordEncoder.encode(signupRequestDto.getPassword());
        SigninRequestDto dto = SigninRequestDto.builder()
                .nickName(nickName)
                .password(password)
                .build();
        Member user = new Member(dto);
        memberRepository.save(user);
        return user.getNickName()+" 가입완료!";
    }

    //로그인
    public String login(SigninRequestDto signinRequestDto, HttpServletResponse response){
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(signinRequestDto.getNickName(),signinRequestDto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepositroy.save(refreshToken);

        response.addHeader("Access-Token", tokenDto.getGrantType()+" "+tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());

        return authentication.getName()+" 로그인 완료!";
    }
}

package com.blog.hw3.service;

import com.blog.hw3.dto.SigninRequestDto;
import com.blog.hw3.dto.SignupRequestDto;
import com.blog.hw3.entity.Users;
import com.blog.hw3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public String registerUser(SignupRequestDto signupRequestDto) throws IllegalAccessException {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String passwordConfirm = signupRequestDto.getPasswordConfirm();

        Optional<Users> found = userRepository.findByUsername(username);
        if(found.isPresent()) {
            throw new IllegalAccessException("중복 닉네임 확인!");
        }
        if(!password.equals(passwordConfirm)) {
            throw new IllegalAccessException("비밀번호가 서로 다릅니다!");
        }
        password = passwordEncoder.encode(signupRequestDto.getPassword());
        SigninRequestDto dto = SigninRequestDto.builder()
                .username(username)
                .password(password)
                .build();
        Users user = new Users(dto);
        userRepository.save(user);
        return user.getUsername();
    }
}

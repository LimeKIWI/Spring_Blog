package com.blog.hw3.security;

import com.blog.hw3.entity.Member;
import com.blog.hw3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepository.findByNickName(username).orElseThrow( ()-> new UsernameNotFoundException(username+": 닉네임을 찾을 수 없습니다."));
        return new UserDetailImp(user);
    }
}

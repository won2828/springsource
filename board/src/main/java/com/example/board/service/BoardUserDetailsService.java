package com.example.board.service;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.dto.MemberAutoDto;
import com.example.board.dto.MemberDto;
import com.example.board.entity.Member;
import com.example.board.entity.constant.MemberRole;
import com.example.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class BoardUserDetailsService implements UserDetailsService, BoardUserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("login {}", username);

        // username 으로 사용자 찾기
        Optional<Member> result = memberRepository.findById(username);

        // 없다면 UsernameNotFoundException
        if (!result.isPresent()) {
            throw new UsernameNotFoundException("이메일을 확인해주세요");
        }

        // 있다면 MemberDto + 인증된 값을 담아서 리턴
        Member member = result.get();

        MemberDto mDto = entityToDto(member);

        return new MemberAutoDto(mDto);
    }

    @Override
    public String register(MemberDto mDto) throws IllegalStateException {

        // 중복 이메일 검사
        validateDuplicationMember(mDto.getEmail());

        // 비밀번호 암호화
        mDto.setPassword(passwordEncoder.encode(mDto.getPassword()));

        // 권한 부여
        mDto.setRole(MemberRole.MEMBER);

        // 저장
        Member member = memberRepository.save(dtoToEntity(mDto));

        // 이름 변환
        return member.getName();
    }

    // 중복 이메일 검사
    private void validateDuplicationMember(String email) throws IllegalStateException {
        Optional<Member> result = memberRepository.findById(email);

        if (result.isPresent()) {
            // 강제 exception
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}

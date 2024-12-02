package com.example.movie.repository;

import com.example.movie.dto.MemberDto;
import com.example.movie.dto.PasswordDto;

public interface MemberService {

    // 닉네임 수정
    void nickNickUpdate(MemberDto memberDto);

    // 비밀번호 수정
    void passwordUpdate(PasswordDto passwordDto) throws Exception;

    // 회원 가입

    // 회원 탈퇴

}

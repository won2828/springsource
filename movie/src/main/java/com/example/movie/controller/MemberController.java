package com.example.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.movie.dto.PageRequestDto;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    @GetMapping("/login")
    public void getLogin(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("로그인 폼 요청");
    }

}

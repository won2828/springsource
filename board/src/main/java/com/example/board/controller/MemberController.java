package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.MemberDto;
import com.example.board.service.BoardUserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Log4j2
@RequestMapping("/member")
@Controller
public class MemberController {

    private final BoardUserService service;

    // @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 폼 요청");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/register")
    public void getRegister(MemberDto mDto) {
        log.info("회원가입 폼 요청");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public String postRegister(@Valid MemberDto mDto, BindingResult result, RedirectAttributes rttr) {
        log.info("회원가입 요청 {}", mDto);

        if (result.hasErrors()) {
            return "/member/register";
        }
        // 서비스 작업
        String name = "";
        try {
            name = service.register(mDto);
        } catch (Exception e) {
            log.info(e.getMessage());
            rttr.addFlashAttribute("msg", e.getMessage());
            return "redirect:/member/register";
        }

        rttr.addFlashAttribute("msg", name);
        return "redirect:/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @GetMapping("/auth")
    public Authentication getAuthentication() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }

}

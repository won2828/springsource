package com.example.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.book.dto.BookDto;
import com.example.book.dto.CategoryDto;
import com.example.book.dto.PublisherDto;
import com.example.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Log4j2
@RequestMapping("/book")
@Controller
public class BookController {

    private final BookService bookService;

    // 리스트 추출
    @GetMapping("/list")
    public void getList(Model model) {
        log.info("도서 전체 목록 요청");
        List<BookDto> list = bookService.getList();
        model.addAttribute("list", list);
    }

    // 상세조회
    @GetMapping({ "/read", "/modify" })

    public void getRead(@RequestParam Long id, Model model) {
        log.info("도서 상세 요청 {}", id);

        BookDto dto = bookService.getRow(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(BookDto dto, RedirectAttributes rttr) {
        log.info("도서 수정 요청 {}", dto);

        Long id = bookService.update(dto);

        // 상세조회로 이동
        rttr.addAttribute("id", id); // ?id=3
        return "redirect:read";
    }

    @PostMapping("/remove")
    public String postMethodName(@RequestParam Long id) {
        log.info("도서 삭제 요청 {}", id);
        bookService.delete(id);

        return "redirect:list";
    }

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("dto") BookDto dto, Model model) {
        log.info("도서 입력 폼 요청");

        List<CategoryDto> categories = bookService.getCateList();
        List<PublisherDto> publishers = bookService.getPubList();

        model.addAttribute("cDtos", categories);
        model.addAttribute("pDtos", publishers);
    }

    @PostMapping("/create")
    public String postCreate(@Valid @ModelAttribute("dto") BookDto dto, BindingResult result, Model model,
            RedirectAttributes rttr) {
        log.info("도서 입력 요청 {}", dto);

        List<CategoryDto> categories = bookService.getCateList();
        List<PublisherDto> publishers = bookService.getPubList();

        model.addAttribute("cDtos", categories);
        model.addAttribute("pDtos", publishers);

        if (result.hasErrors()) {
            return "/book/create";
        }

        // 서비스 insert 호출
        Long id = bookService.create(dto);

        rttr.addFlashAttribute("msg", id + "번 도서가 등록되었습니다.");
        return "redirect:list";
    }

}

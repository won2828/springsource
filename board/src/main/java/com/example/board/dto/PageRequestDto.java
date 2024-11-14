package com.example.board.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
@Setter
@Getter
public class PageRequestDto {
    private int page; // 페이지 번호
    private int size; // 페이지 크기

    // 검색 필터
    private String type;
    private String keyword;

    // 기본 생성자: page 1, size 10으로 초기화
    public PageRequestDto() {
        this.page = 1;
        this.size = 10;
    }

    // Pageable 객체 생성
    public Pageable getPageable(Sort sort) {
        // size가 0 이하일 경우 기본값 10으로 설정
        if (this.size <= 0) {
            this.size = 10; // 기본값 설정
        }

        // 0: 1 페이지로 처리되므로 페이지 번호는 1을 빼서 전달
        return PageRequest.of(page - 1, size, sort);
    }
}

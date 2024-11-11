package com.example.project2.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.project2.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 20)
                .forEach(i -> {
                    Board board = Board.builder()
                            .title("Title " + i)
                            .content("Content for board " + i)
                            .writer("Writer " + i)
                            .build();
                    boardRepository.save(board);
                });
    }

    @Test
    public void selectOneTest() {
        Board board = boardRepository.findById(1L).get();
        System.out.println(board);
    }

    @Test
    public void selectAllTest() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void updateTest() {
        Board board = boardRepository.findById(4L).get();
        if (board != null) {
            board.setTitle("Updated Title");
            board.setContent("Updated Content");
            boardRepository.save(board);
        }
    }

    @Test
    public void deleteTest() {
        boardRepository.deleteById(2L);
    }

    // 쿼리 메소드
    @Test
    public void testTitleList() {
        boardRepository.findByTitle("Title ").forEach(b -> System.out.println(b));
        // boardRepository.findByTitleLike("Title").forEach(b -> System.out.println(b));
        // boardRepository.findByTitleStartingWith("Title").forEach(b ->
        // System.out.println(b));
        // boardRepository.findByWriterEndingWith("1").forEach(b ->
        // System.out.println(b));
        // boardRepository.findByWriterContaining("er").forEach(b ->
        // System.out.println(b));
        // boardRepository.findByWriterContainingOrTitleContaining("it", "it").forEach(b
        // -> System.out.println(b));
        // boardRepository.findByTitleContainingAndIdGreaterThan("it", 10L).forEach(b ->
        // System.out.println(b));
        // boardRepository.findByIdGreaterThanOrderByIdDesc(0L).forEach(b ->
        // System.out.println(b));

        // 0 : 1 page 의미, pageSize : 한페이지에 보여질 게시물 개수
        // Pageable pageable = PageRequest.of(0, 10);

        // boardRepository.findByIdGreaterThanOrderByIdDesc(0L, pageable).forEach(b ->
        // System.out.println(b));

        // boardRepository
        // .findByWriterList("it")
        // .forEach(b -> System.out.println(b));
    }

}

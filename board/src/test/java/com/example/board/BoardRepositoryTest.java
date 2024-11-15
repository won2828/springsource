package com.example.board;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepository;
import com.example.board.repository.ReplyRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsertMember() {
        // 30명
        LongStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@email.com")
                    .name("name " + i)
                    .password("asdf1111")
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void testInsertBoard() {
        // 100개
        LongStream.rangeClosed(1, 100).forEach(i -> {
            int num = (int) (Math.random() * 30) + 1;
            Member member = Member.builder().email("user" + num + "@email.com").build();

            Board board = Board.builder()
                    .content("content " + i)
                    .title("title " + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    @Test
    public void testInsertReply() {
        // 100개
        LongStream.rangeClosed(1, 100).forEach(i -> {
            long bno = (int) (Math.random() * 100) + 1;
            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .board(board)
                    .replyer("replyer " + i)
                    .text("text " + i)
                    .build();

            replyRepository.save(reply);
        });
    }

    @Transactional
    @Test
    public void testReadBoard() {
        // 100번 보드
        Board board = boardRepository.findById(100L).get();
        System.out.println(board);

        // 객체 그래프 탐색 : Board, Member 관계(N:1)
        System.out.println(board.getWriter());
    }

    @Transactional
    @Test
    public void testReadReply() {
        // 100번 댓글
        Reply reply = replyRepository.findById(100L).get();
        System.out.println(reply);

        // 객체 그래프 탐색 : Reply, Board 관계(N:1)
        // 원본글 조회
        System.out.println(reply.getBoard());
    }

    @Transactional
    @Test
    public void testReadBoardReply() {
        Board board = boardRepository.findById(100L).get();
        System.out.println(board);

        System.out.println(board.getReplies());
    }

    @Test
    public void testJoin() {
        List<Object[]> result = boardRepository.list();

        for (Object[] objects : result) {
            // [Board(bno=100, content=content 100, title=title 100),
            // Member(email=user4@email.com, name=name 4, password=asdf1111)]
            System.out.println(Arrays.toString(objects));
            // Board board = (Board) objects[0];
            // Member member = (Member) objects[1];
            // Long replyCnt = (Long) objects[2];
        }
    }

    @Test
    public void testJoinList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        // Pageable pageable = PageRequest.of(0, 10,
        // Sort.by("bno").descending().and(Sort.by("title").descending()));

        Page<Object[]> result = boardRepository.list(pageable);

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }
}

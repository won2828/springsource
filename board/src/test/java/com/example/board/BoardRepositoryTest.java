package com.example.board;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepository;
import com.example.board.repository.ReplyRepository;

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

}

package com.example.memo.repository;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.memo.entity.Memo;

@SpringBootTest
public class MemoRepositoryTest {

    @Autowired
    private MemoRepository memoRepository;

    @Test
    public void testMemoInsert() {
        LongStream.rangeClosed(1, 10).forEach(i -> {

            Memo memo = Memo.builder()
                    .memoText("text " + i)
                    .build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testMemoRead() {

        // 6번 메모
        Memo memo = memoRepository.findById(26L).get();
        // 전체 메모
        List<Memo> list = memoRepository.findAll();

        // 6번 메모 가져오기
        System.out.println(memo);
        // 전체 메모 가져오기
        list.forEach(m -> System.out.println(m));
    }

    @Test
    public void testMemoUpdate() {
        Memo memo = memoRepository.findById(23L).get();
        memo.setMemoText("memo 수정");
        memoRepository.save(memo);
    }

    @Test
    public void testMemoDelete() {
        memoRepository.deleteById(29L);
    }

    @Test
    public void testQueryMethod() {
        // memoRepository.findByMnoLessThan(30L).forEach(a -> System.out.println(a));
        // memoRepository.findByMnoLessThanOrderByMnoDesc(40L).forEach(a ->
        // System.out.println(a));
        memoRepository.findByMnoBetweenOrderByMnoDesc(50L, 100L).forEach(a -> System.out.println(a));

    }
}

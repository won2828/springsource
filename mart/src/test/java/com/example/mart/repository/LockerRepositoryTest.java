package com.example.mart.repository;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entity.constant.sports.Locker;
import com.example.mart.entity.constant.sports.Member;
import com.example.mart.repository.sports.LockerRepository;
import com.example.mart.repository.sports.MemberRepository;

@SpringBootTest
public class LockerRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LockerRepository lockerRepository;

    @Test
    public void testLockerInsert() {
        // Locker 객체 4개 저장
        IntStream.rangeClosed(1, 4).forEach(i -> {
            Locker locker = Locker.builder().name("lockerName" + i).build();
            lockerRepository.save(locker);
        });

        // 저장된 Locker 객체를 사용하여 Member 객체 4개 저장
        IntStream.rangeClosed(1, 4).forEach(i -> {
            // 저장된 locker를 가져오기
            Locker locker = lockerRepository.findById((long) i).orElseThrow();

            Member member = Member.builder()
                    .name("userName" + i)
                    .locker(locker)
                    .build();
            memberRepository.save(member);
        });
    }
}
package com.example.mart.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entity.sports.Locker;
import com.example.mart.entity.sports.SportsMember;
import com.example.mart.repository.sports.LockerRepository;
import com.example.mart.repository.sports.SportsMemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LockerRepositoryTest {

    @Autowired
    private SportsMemberRepository sportsMemberRepository;

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
        LongStream.rangeClosed(1, 4).forEach(i -> {
            // 저장된 locker를 가져오기
            Locker locker = Locker.builder().id(i).build();

            SportsMember member = SportsMember.builder()
                    .name("userName" + i)
                    .locker(locker)
                    .build();
            sportsMemberRepository.save(member);
        });
    }

    @Test
    public void testMemberUpdate() {
        SportsMember sportsMember = sportsMemberRepository.findById(1L).get();
        sportsMember.setName("changeTime1");
        sportsMemberRepository.save(sportsMember);
    }

    @Transactional
    @Test
    public void testMemberRead() {
        // 회원 조회(+ Locker 조회)
        SportsMember sportsMember = sportsMemberRepository.findById(3L).get();
        System.out.println(sportsMember);

        // 객체 그래프 탐색
        System.out.println(sportsMember.getLocker());

        // 전체 회원 조회
        sportsMemberRepository.findAll().forEach(m -> {
            System.out.println(m);
            System.out.println(m.getLocker());
        });
    }

    @Test
    public void testLockerRead() {

        // 전체 Locker 조회(+ 회원 조회)
        lockerRepository.findAll().forEach(l -> {
            System.out.println(l);
            System.out.println(l.getSportsMember());
        });
    }

}
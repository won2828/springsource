package com.example.mart.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entity.constant.item.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}

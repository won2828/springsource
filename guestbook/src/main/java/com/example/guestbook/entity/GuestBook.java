package com.example.guestbook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Table
@Entity
public class GuestBook extends BaseEntity {

    @SequenceGenerator(name = "guest_seq_gen", sequenceName = "guest_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guest_seq_gen")
    @Id
    @Column
    private Long gno;

    @Column(length = 50, nullable = false)
    private String writer;

    @Column(length = 150, nullable = false)
    private String title;

    @Column(length = 1500, nullable = false)
    private String content;
}

package com.example.book.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@SequenceGenerator(name = "book_seq_gen", sequenceName = "book_seq", allocationSize = 1)
@Entity
public class Book extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_gen")
    @Id
    private Long mno;

    @Column(length = 200, nullable = false)
    private String bookText;
}
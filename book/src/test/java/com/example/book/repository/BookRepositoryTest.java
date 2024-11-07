package com.example.book.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void testCategoryList() {
        // category 목록
        categoryRepository.findAll().forEach(c -> System.out.println(c));
        // publisher 목록
        publisherRepository.findAll().forEach(p -> System.out.println(p));
    }

    @Test
    public void testCategoryInsert() {
        // 5개 Category 삽입
        categoryRepository.save(Category.builder().name("소설").build());
        categoryRepository.save(Category.builder().name("건강").build());
        categoryRepository.save(Category.builder().name("컴퓨터").build());
        categoryRepository.save(Category.builder().name("여행").build());
        categoryRepository.save(Category.builder().name("경제").build());
    }

    @Test
    public void testPublisherInsert() {
        // 5개 Publisher 삽입
        publisherRepository.save(Publisher.builder().name("미래의창").build());
        publisherRepository.save(Publisher.builder().name("웅진리빙하우스").build());
        publisherRepository.save(Publisher.builder().name("김영사").build());
        publisherRepository.save(Publisher.builder().name("길벗").build());
        publisherRepository.save(Publisher.builder().name("문학과지성사").build());
    }

    @Test
    public void testBookInsert() {

        // 10 권
        IntStream.rangeClosed(1, 10).forEach(i -> {
            // 무작위로 publisher, category 지정에 사용
            long num = (int) (Math.random() * 5) + 1;

            Book book = Book.builder()
                    .title("title " + i)
                    .writer("writer " + i)
                    .price(15000 * i)
                    .salePrice((int) (15000 * i * 0.9))
                    .category(Category.builder().id(num).build())
                    .publisher(Publisher.builder().id(num).build())
                    .build();
            bookRepository.save(book);
        });
    }

    @Transactional
    @Test
    public void testList() {
        // 도서 목록 전체 조회
        bookRepository.findAll().forEach(book -> {
            System.out.println(book);
            // category 정보
            System.out.println(book.getCategory());
            System.out.println(book.getPublisher());
        });
    }

    @Transactional
    @Test
    public void testGet() {
        // 특정 도서 조회
        Book book = bookRepository.findById(5L).get();
        System.out.println(book);
        System.out.println(book.getCategory().getName());
        System.out.println(book.getPublisher().getName());
    }

    @Test
    public void testBookUpdate() {
        // 특정 도서 수정
        Book book = bookRepository.findById(5L).get();
        book.setPrice(32000);
        book.setSalePrice(29800);
        bookRepository.save(book);
    }

    @Test
    public void testBookDelete() {
        bookRepository.deleteById(7L);
    }
}
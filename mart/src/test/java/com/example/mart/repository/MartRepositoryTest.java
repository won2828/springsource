package com.example.mart.repository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entity.constant.DeliveryStatus;
import com.example.mart.entity.constant.OrderStatus;
import com.example.mart.entity.item.Delivery;
import com.example.mart.entity.item.Item;
import com.example.mart.entity.item.Member;
import com.example.mart.entity.item.Order;
import com.example.mart.entity.item.OrderItem;
import com.example.mart.repository.item.DeliveryRepository;
import com.example.mart.repository.item.ItemRepository;
import com.example.mart.repository.item.MemberRepository;
import com.example.mart.repository.item.OrderItemRepository;
import com.example.mart.repository.item.OrderRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void memberInsertTest() {
        memberRepository.save(Member.builder().name("user1").city("서울시").street("187-12").zipcode("15100").build());
        memberRepository.save(Member.builder().name("user2").city("고양시").street("296-14").zipcode("18700").build());
        memberRepository.save(Member.builder().name("user3").city("수원시").street("489-16").zipcode("19300").build());
    }

    @Test
    public void itemInsertTest() {
        itemRepository.save(Item.builder().name("item1").price(15000).quantity(10).build());
        itemRepository.save(Item.builder().name("item2").price(25000).quantity(15).build());
        itemRepository.save(Item.builder().name("item3").price(35000).quantity(5).build());
    }

    @Test
    public void orderInsertTest() {

        Member member = memberRepository.findById(1L).get();
        Item item = itemRepository.findById(2L).get();

        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.ORDER)
                .member(member)
                .build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder()
                .price(10000)
                .count(2)
                .order(order)
                .item(item)
                .build();
        orderItemRepository.save(orderItem);

        // item 수량 감소
    }

    // R

    @Test
    public void memberAndItemAndOrderListTest() {
        // 주문 내역 조회
        // orderRepository.findAll().forEach(order -> System.out.println(order));
        // Order(id=1, orderDate=2024-11-04T13:07:44.309771, status=ORDER)

        // 주문 상세 내역 조회
        orderItemRepository.findAll().forEach(orderItem -> {
            // 상품 상세 조회
            System.out.println(orderItem.getItem());
            // 주문자 상세 조회
            System.out.println(orderItem.getOrder().getMember());
        });
    }

    @Test
    public void memberAndItemAndOrderRowTest() {

        OrderItem orderItem = orderItemRepository.findById(3L).get();

        // 주문 상세 내역 조회
        System.out.println(orderItem);
        // 상품 상세 조회
        System.out.println(orderItem.getItem());
        // 주문 상세 내역 조회
        System.out.println(orderItem.getOrder());
        // 주문자 상세 조회
        System.out.println(orderItem.getOrder().getMember());
    }

    // U
    @Test
    public void memberAndItemAndOrderUpdateTest() {

        // // 주문자의 주소 변경
        // Member member = Member.builder()
        // .id(1L)
        // .name("user1")
        // .city("서울시")
        // .street("777-77")
        // .zipcode("15100")
        // .build();

        Member member = memberRepository.findById(1L).get();
        member.setStreet("777-78");

        // save: insert or update
        // 엔티티 매니저가 있어서 현재 entity 가 new 인지 기존 entity 인지 구분이 가능함
        // new : insert 호출 / 기존 entity : update 호출
        // update 는 무조건 전체 컬럼이 수정되는 형태로 작성됨
        System.out.println(memberRepository.save(member));

        // 1번 주문상품의 아이템(2번 아이템) 가격 변경

        // 아이템 수량, 가격 변경
        Item item = itemRepository.findById(2L).get();
        item.setPrice(26000);
        itemRepository.save(item);
        OrderItem orderItem = orderItemRepository.findById(3L).get();
        orderItem.setPrice(26000);
        orderItemRepository.save(orderItem);
    }

    // D
    @Test
    public void memberAndItemAndOrderDeleteTest() {
        // 주문 취소

        // 1. 주문상품 취소
        orderItemRepository.deleteById(3L);
        // 2. 주문 취소
        orderRepository.deleteById(3L);
    }

    // 양방향
    // Order ==> OrderItem 객체 그래프 탐색
    @Transactional
    @Test
    public void testOrderItemList() {

        Order order = orderRepository.findById(1L).get();
        System.out.println(order);
        // Order ==> OrderItem 탐색 시도
        order.getOrderItemsList().forEach(orderItem -> System.out.println(orderItem));
    }

    @Transactional
    @Test
    public void testOrderList() {

        Member member = memberRepository.findById(1L).get();
        System.out.println(member);

        // Member ==> Order 탐색 시도
        member.getOrders().forEach(order -> System.out.println(order));
    }

    @Test
    public void testDeliveryInsert() {
        // 배송정보 입력
        Delivery delivery = Delivery.builder()
                .city("서울시")
                .street("종로 3가")
                .zipcode("11111")
                .deliveryStatus(DeliveryStatus.READY)
                .build();

        deliveryRepository.save(delivery);

        // order 와 배송정보 연결
        Order order = orderRepository.findById(1L).get();
        order.setDelivery(delivery);
        orderRepository.save(order);
    }

    @Test
    public void testOrderRead() {
        // order 조회 (+ 배송정보)
        Order order = orderRepository.findById(1L).get();
        System.out.println(order);

        System.out.println(order.getDelivery());

    }

    // 양방향(배송 => 주문)
    @Test
    public void testDeliveryRead() {
        // 배송정보 조회 (+ order)
        Delivery delivery = deliveryRepository.findById(1L).get();
        System.out.println(delivery);
        System.out.println(delivery.getOrder());
    }
}

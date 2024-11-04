package com.example.mart.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entity.constant.item.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}

package com.shop.alcoshopspring.repositories;

import com.shop.alcoshopspring.models.Order;
import com.shop.alcoshopspring.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT od FROM OrderDetail od WHERE od.order = ?1")
    List<OrderDetail> getOrderDetailsByOrder(Order order);
}

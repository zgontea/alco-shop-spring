package com.shop.alcoshopspring.repositories;

import com.shop.alcoshopspring.models.Order;
import com.shop.alcoshopspring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT o FROM Order o WHERE o.user = ?1 AND o.status = 'Draft'", nativeQuery = false)
    List<Order> getByOwnerUser(User user, String status);

    @Query(value = "SELECT o FROM Order o WHERE o.user = ?1 AND o.status != 'Draft'", nativeQuery = false)
    List<Order> getAllByOwnerUser(User user);
}
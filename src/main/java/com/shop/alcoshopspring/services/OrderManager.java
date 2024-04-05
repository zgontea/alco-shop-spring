package com.shop.alcoshopspring.services;

import com.shop.alcoshopspring.models.Order;
import com.shop.alcoshopspring.models.User;
import com.shop.alcoshopspring.repositories.OrderRepository;
import com.shop.alcoshopspring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderManager {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order findByUserIdWithStatusDraft(Long userId) {
        User user = userRepository.getById(userId);
        return orderRepository.getByOwnerUser(user, statusToString.get(OrderStatus.DRAFT)).get(0);
    }

    public List<Order> findByUserId(Long userId) {
        User user = userRepository.getById(userId);
        return orderRepository.getAllByOwnerUser(user);
    }

    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void sendOrder(Order order) {
        Order orderToUpdate = findByUserIdWithStatusDraft(order.getUser().getId());
        orderToUpdate.setStatus(statusToString.get(OrderStatus.NEW));
        orderToUpdate.setCreatedDate(LocalDateTime.now());
        orderToUpdate.setShipAddress(order.getShipAddress());
        orderToUpdate.setShipCity(order.getShipCity());
        orderToUpdate.setShipPostalCode(order.getShipPostalCode());
        orderToUpdate.setShipEmail(order.getShipEmail());
        orderToUpdate.setShipPhoneNo(order.getShipPhoneNo());
        orderToUpdate.setTotalPrice(order.getTotalPrice());

        orderRepository.save(orderToUpdate);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    public enum OrderStatus {
        DRAFT,
        NEW,
        CONFIRMED,
        SENT,
        DELIVERED,
        CANCELED
    }

    public static Map<OrderStatus, String> statusToString = Map.of(
            OrderStatus.DRAFT, "Draft",
            OrderStatus.NEW, "New",
            OrderStatus.CONFIRMED, "Confirmed",
            OrderStatus.SENT, "Sent",
            OrderStatus.DELIVERED, "Delivered",
            OrderStatus.CANCELED, "Canceled");
}

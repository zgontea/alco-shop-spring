package com.shop.alcoshopspring.services;

import com.shop.alcoshopspring.models.Order;
import com.shop.alcoshopspring.models.OrderDetail;
import com.shop.alcoshopspring.models.User;
import com.shop.alcoshopspring.repositories.OrderDetailRepository;
import com.shop.alcoshopspring.repositories.OrderRepository;
import com.shop.alcoshopspring.repositories.UserRepository;
import com.shop.alcoshopspring.services.OrderManager.OrderStatus;
import com.shop.alcoshopspring.services.exceptions.OnlyOneDraftOrderException;
import com.shop.alcoshopspring.wrappers.ShoppingCartItemWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailManager {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(id);
    }

    public Iterable<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    public Iterable<OrderDetail> getByOrder(Order order) {
        return orderDetailRepository.getOrderDetailsByOrder(order);
    }

    public OrderDetail save(ShoppingCartItemWrapper shoppingCartItem) {
        List<Order> orders;
        Order orderToSave;
        OrderDetail orderDetail = shoppingCartItem.getOrderDetail();
        User user = userRepository.getById(shoppingCartItem.getUserId());

        try {
            orders = orderRepository.getByOwnerUser(user, OrderManager.statusToString.get(OrderStatus.DRAFT));
            if (orders.size() > 1) {
                throw new OnlyOneDraftOrderException();
            } else {
                orderToSave = orders.get(0);
            }
        } catch (OnlyOneDraftOrderException e) {
            System.out.println(e.getMessage());
            return orderDetail;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            orderToSave = Order.builder()
                    .status(OrderManager.statusToString.get(OrderManager.OrderStatus.DRAFT))
                    .user(user)
                    .build();
        }
        orderDetail.setOrder(orderToSave);
        return orderDetailRepository.save(orderDetail);
    }

    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }
}

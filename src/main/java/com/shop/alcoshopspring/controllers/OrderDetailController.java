package com.shop.alcoshopspring.controllers;

import com.shop.alcoshopspring.models.OrderDetail;
import com.shop.alcoshopspring.services.OrderDetailManager;
import com.shop.alcoshopspring.wrappers.ShoppingCartItemWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/orderDetails")
public class OrderDetailController {
    @Autowired
    private OrderDetailManager orderDetailManager;

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/all")
    public Iterable<OrderDetail> getAll() {
        return orderDetailManager.findAll();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/id")
    public Optional<OrderDetail> getById(@RequestParam Long index) {
        return orderDetailManager.findById(index);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/{orderDetailId}")
    public Optional<OrderDetail> getId(@PathVariable("orderDetailId") Long orderDetailId) {
        return orderDetailManager.findById(orderDetailId);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/save")
    public OrderDetail add(@RequestBody ShoppingCartItemWrapper shoppingCartItem) {
        return orderDetailManager.save(shoppingCartItem);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/upd")
    public OrderDetail update(@RequestBody ShoppingCartItemWrapper shoppingCartItem) {
        return orderDetailManager.save(shoppingCartItem);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping("/del")
    public void delete(@RequestParam Long id) {
        orderDetailManager.deleteById(id);
    }
}

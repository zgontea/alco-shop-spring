package com.shop.alcoshopspring.controllers;

import com.shop.alcoshopspring.models.Order;
import com.shop.alcoshopspring.models.OrderDetail;
import com.shop.alcoshopspring.services.OrderDetailManager;
import com.shop.alcoshopspring.services.OrderManager;
import com.shop.alcoshopspring.wrappers.OrderWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	private OrderManager orderManager;

	@Autowired
	private OrderDetailManager orderDetailManager;

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/all")
	public Iterable<Order> getAll() {
		return orderManager.findAll();
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/id")
	public Iterable<OrderDetail> getByUserIdWithStatusDraft(@RequestParam Long userId) {
		Iterable<OrderDetail> orderDetails = null;
		try {
			Order order = orderManager.findByUserIdWithStatusDraft(userId);
			orderDetails = orderDetailManager.getByOrder(order);
		} catch (Exception e) {
			System.err.println("No order in status Draft for this user");
		}
		return orderDetails;
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/userOrder")
	public Iterable<Order> getAllOrdersByUserId(@RequestParam Long userId) {
		Iterable<Order> orders = null;
		try {
			orders = orderManager.findByUserId(userId);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return orders;
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping(value = "/{orderId}")
	public Optional<Order> getId(@PathVariable("orderId") Long orderId) {
		return orderManager.findById(orderId);
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/save")
	public Order add(@RequestBody Order order) {
		return orderManager.save(order);
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/sendOrder")
	public OrderWrapper sendOrder(@RequestBody OrderWrapper order) {
		System.out.println(order.toString());
		Order orderToUpdate = Order.builder()
			.shipAddress(order.getShipAddress())
			.shipCity(order.getShipCity())
			.shipPostalCode(order.getShipPostalCode())
			.shipPhoneNo(order.getShipPhoneNo())
			.shipEmail(order.getShipEmail())
			.totalPrice(order.getTotalPrice())
			.user(order.getUser())
			.build();
			
		try {
			orderManager.sendOrder(orderToUpdate);
		} catch (Exception e) {
			System.err.println("No order in status Draft for this user");
		}
		return order;
	}

	@Secured({"ROLE_ADMIN"})
	@PutMapping("/upd")
	public Order update(@RequestBody Order order) {
		return orderManager.save(order);
	}

	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/del")
	public void delete(@RequestParam Long index) {
		orderManager.deleteById(index);
	}
}

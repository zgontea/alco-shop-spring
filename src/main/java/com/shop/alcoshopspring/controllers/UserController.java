package com.shop.alcoshopspring.controllers;

import com.shop.alcoshopspring.models.User;
import com.shop.alcoshopspring.services.UserManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserManager userManager;

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/all")
    public Iterable<User> getAll() {
        return userManager.findAll();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/id")
    public Optional<User> getById(@RequestParam Long index) {
        return userManager.findById(index);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/email/{email}")
    public User getByEmail(@PathVariable String email) {
        return userManager.findByEmail(email);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/save")
    public User add(@RequestBody UserWrapper userWrapper) {
        User user = new User();
        user.setAdmin(false);
        user.setEmail(userWrapper.email);
        user.setFirstname(userWrapper.firstname);
        user.setLastname(userWrapper.lastname);
        user.setPhone(userWrapper.phone);
        user.setPassword(userWrapper.password);
        return userManager.save(user);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/upd")
    public User update(@RequestBody User user) {
        return userManager.save(user);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/del/{userId}")
	public void delete(@PathVariable("userId") Long userId) {
		userManager.deleteById(userId);
	}

    @Data
    public static class UserWrapper {
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private String phone;
    }
}

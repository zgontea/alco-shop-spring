package com.shop.alcoshopspring.controllers;

import com.shop.alcoshopspring.models.Category;
import com.shop.alcoshopspring.services.CategoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryManager categoryManager;

    @GetMapping("/all")
    public Iterable<Category> getAll() {
        return categoryManager.findAll();
    }

    @GetMapping("/id")
    public Optional<Category> getById(@RequestParam Long index) {
        return categoryManager.findById(index);
    }

    @GetMapping(value = "/{categoryId}")
    public Optional<Category> getId(@PathVariable("categoryId") Long employeeId) {
        return categoryManager.findById(employeeId);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/save")
    public Category add(@RequestBody Category category) {
        return categoryManager.save(category);
    }
}

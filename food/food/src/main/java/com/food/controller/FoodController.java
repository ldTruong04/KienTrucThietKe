package com.food.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.entity.Food;
import com.food.service.FoodService;

@RestController
@RequestMapping("/foods")
@CrossOrigin("*")
public class FoodController {

    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    // GET /foods
    @GetMapping
    public List<Food> getAll() {
        return service.getAll();
    }

    // POST /foods
    @PostMapping
    public Food create(@RequestBody Food f) {
        return service.create(f);
    }

    // PUT /foods/{id}
    @PutMapping("/{id}")
    public Food update(@PathVariable Long id, @RequestBody Food f) {
        return service.update(id, f);
    }

    // DELETE /foods/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
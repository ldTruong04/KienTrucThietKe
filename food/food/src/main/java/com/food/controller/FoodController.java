package com.food.controller;

import java.util.Collections;
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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

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
    @CircuitBreaker(name = "backendA", fallbackMethod = "getAllFallback")
    @Retry(name = "backendA")
    @RateLimiter(name = "backendA")
    public List<Food> getAll() {
        return service.getAll();
    }

    // Fallback method for getAll
    public List<Food> getAllFallback(Exception e) {
        // Return an empty list if the service or database fails
        System.out.println("Fallback triggered for getAll: " + e.getMessage());
        return Collections.emptyList();
    }

    // POST /foods
    @PostMapping
    @CircuitBreaker(name = "backendB", fallbackMethod = "createFallback")
    @RateLimiter(name = "backendB")
    public Food create(@RequestBody Food f) {
        return service.create(f);
    }

    // Fallback method for create
    public Food createFallback(Food f, Exception e) {
        // Return a dummy object if creating fails
        System.out.println("Fallback triggered for create: " + e.getMessage());
        Food fallbackFood = new Food();
        fallbackFood.setName("Service Unavailable - Fallback Data");
        fallbackFood.setPrice(0.0);
        return fallbackFood;
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
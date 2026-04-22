package com.food.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.entity.Food;
import com.food.repository.FoodRepository;

@Service
public class FoodService {

    private final FoodRepository repo;

    public FoodService(FoodRepository repo) {
        this.repo = repo;
    }

    public List<Food> getAll() {
        return repo.findAll();
    }

    public Food create(Food f) {
        return repo.save(f);
    }

    public Food update(Long id, Food f) {
        f.setId(id);
        return repo.save(f);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
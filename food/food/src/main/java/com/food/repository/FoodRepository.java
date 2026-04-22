package com.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
package com.food;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.food.entity.Food;
import com.food.repository.FoodRepository;

@SpringBootApplication
public class FoodServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FoodRepository repo) {
        return args -> {
            repo.save(new Food(null, "Cơm gà", 30000));
            repo.save(new Food(null, "Bún bò", 40000));
            repo.save(new Food(null, "Trà sữa", 25000));
                        repo.save(new Food(null, "Ca kho", 25000));

        };
    }
}
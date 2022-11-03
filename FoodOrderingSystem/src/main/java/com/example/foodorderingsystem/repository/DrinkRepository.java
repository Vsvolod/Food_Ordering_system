package com.example.foodorderingsystem.repository;

import com.example.foodorderingsystem.Entity.DrinkEntity;
import com.example.foodorderingsystem.Entity.MainDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<DrinkEntity, Long> {

    @Query("select distinct p from DrinkEntity p where p.name = :name")
    List<DrinkEntity> findByName(@Param("name") String name);

    DrinkEntity save(DrinkEntity entity);
}

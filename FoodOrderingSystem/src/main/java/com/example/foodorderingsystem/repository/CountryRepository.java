package com.example.foodorderingsystem.repository;


import com.example.foodorderingsystem.Entity.CountryEntity;
import com.example.foodorderingsystem.Entity.MainDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    CountryEntity save(CountryEntity entity);


}

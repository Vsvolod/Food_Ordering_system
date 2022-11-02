package com.example.foodorderingsystem.repository;

import com.example.foodorderingsystem.Entity.CountryEntity;
import com.example.foodorderingsystem.Entity.MainDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
@Repository
public interface MainDishRepository extends JpaRepository<MainDishEntity, Long> {
    @Query("select distinct p from MainDishEntity p left join fetch p.country where p.country.name = :country")
    List<MainDishEntity> findAllByCountry(@Param("country") String country);

    @Query("select distinct p from MainDishEntity p where p.name = :name")
    List<MainDishEntity> findByName(@Param("name") String name);

    MainDishEntity save(MainDishEntity entity);
}

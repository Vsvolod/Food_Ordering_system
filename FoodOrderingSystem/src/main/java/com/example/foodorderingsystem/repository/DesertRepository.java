package com.example.foodorderingsystem.repository;

import com.example.foodorderingsystem.Entity.DesertEntity;
import com.example.foodorderingsystem.Entity.MainDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesertRepository extends JpaRepository<DesertEntity, Long> {

    DesertEntity save(DesertEntity entity);

    @Query("select distinct p from DesertEntity p where p.name = :name")
    List<DesertEntity> findByName(@Param("name") String name);

}

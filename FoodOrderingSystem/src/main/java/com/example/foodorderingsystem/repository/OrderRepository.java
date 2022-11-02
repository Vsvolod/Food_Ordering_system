package com.example.foodorderingsystem.repository;

import com.example.foodorderingsystem.Entity.MainDishEntity;
import com.example.foodorderingsystem.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("select distinct p from OrderEntity p where p.clientId = :clientId")
    List<OrderEntity> findByClientId(@Param("clientId") Long clientId);



    OrderEntity save(OrderEntity entity);
}

package com.example.foodorderingsystem.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Setter @Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clientId;

    @ManyToOne
    @JoinColumn(name = "main_dish_id")
    private MainDishEntity mainDishId;

    @ManyToOne
    @JoinColumn(name = "desert_id")
    private DesertEntity desertId;

    @ManyToOne
    @JoinColumn(name = "drink_id")
    private DrinkEntity drinkId;

    @Column
    private Boolean iceNeed;

    @Column
    private Boolean limeNeed;

    public OrderEntity(Long id, Long clientId, MainDishEntity mainDishId, DesertEntity desertId, DrinkEntity drinkId, Boolean iceNeed, Boolean limeNeed){
        this.id = id;
        this.clientId = clientId;
        this.mainDishId = mainDishId;
        this.desertId = desertId;
        this.drinkId = drinkId;
        this.iceNeed = iceNeed;
        this.limeNeed = limeNeed;
    }

}

package com.example.foodorderingsystem.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "main_dishes")
@Getter @Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class MainDishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "country_id",referencedColumnName = "id")
    @JsonBackReference
    public CountryEntity country;




    public MainDishEntity(Long id, String name, Double price, CountryEntity country){
        this.id = id;
        this.name = name;
        this.price = price;
        this.country = country;
    }

}

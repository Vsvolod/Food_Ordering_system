package com.example.foodorderingsystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "country")
@Getter @Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;


    @OneToMany(mappedBy = "country")
    private List<MainDishEntity> mainDishes = new ArrayList<>();

    public CountryEntity(Long id,String name){
        this.id = id;
        this.name = name;
    }


}

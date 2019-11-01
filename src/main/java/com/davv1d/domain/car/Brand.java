package com.davv1d.domain.car;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "car_brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", length = 20, unique = true)
    private String name;

    @OneToMany(
            targetEntity = Model.class,
            mappedBy = "brand",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Model> carModels = new ArrayList<>();

    @OneToMany(
            targetEntity = Car.class,
            mappedBy = "brand",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Car> cars = new ArrayList<>();

    public Brand(@NotNull String name) {
        this.name = name.toUpperCase();
    }
}




package com.davv1d.domain.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "car_models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", length = 20, unique = true)
    private String name;

    @ManyToOne(targetEntity = Brand.class)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(
            targetEntity = Car.class,
            mappedBy = "model",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Car> cars = new ArrayList<>();

    public Model(@NotNull String name, Brand brand) {
        this.name = name.toUpperCase();
        this.brand = brand;
    }
}


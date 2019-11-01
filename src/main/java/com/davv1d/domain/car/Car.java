package com.davv1d.domain.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "vin_number", length = 50, unique = true)
    private String vinNumber;

    @ManyToOne(targetEntity = Brand.class)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @NotNull
    @Column(name = "availability")
    private boolean availability;

    public Car(@NotNull String vinNumber, Brand brand, Model model, @NotNull boolean availability) {
        this.vinNumber = vinNumber.toUpperCase();
        this.brand = brand;
        this.model = model;
        this.availability = availability;
    }
}

package com.davv1d.domain.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedNativeQuery(
        name = "Car.fetchAvailabilityCars",
        query = "select * from cars " +
                "left join rentals on cars.id = rentals.car_id " +
                "where (rentals.date_of_rent is null or " +
                "(:DATE_OF_RENT < rentals.date_of_rent and :DATE_OF_RETURN < rentals.date_of_rent) or " +
                "(:DATE_OF_RENT > rentals.date_of_return and :DATE_OF_RETURN > rentals.date_of_return)) and " +
                "cars.availability = true",
        resultClass = Car.class
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
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

    public Car(@NotNull String vinNumber) {
        this.vinNumber = vinNumber;
    }
}

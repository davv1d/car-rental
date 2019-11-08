package com.davv1d.domain.rental;

import com.davv1d.domain.car.Car;
import com.davv1d.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Rental.fetchRentalsByCarVinNumber",
                query = "select * from rentals " +
                        "inner join cars on cars.id = rentals.car_id " +
                        "where cars.vin_number = :VIN_NUMBER",
                resultClass = Rental.class
        ),
        @NamedNativeQuery(
                name = "Rental.fetchRentalsByUsername",
                query = "select * from rentals " +
                        "inner join users on users.id = rentals.user_id " +
                        "where users.username = :USERNAME",
                resultClass = Rental.class
        )

})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Car.class)
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "date_of_rent")
    private LocalDateTime dateOfRent;

    @Column(name = "date_of_return")
    private LocalDateTime dateOfReturn;

    public Rental(User user, Car car, LocalDateTime dateOfRent, LocalDateTime dateOfReturn) {
        this.user = user;
        this.car = car;
        this.dateOfRent = dateOfRent;
        this.dateOfReturn = dateOfReturn;
    }
}

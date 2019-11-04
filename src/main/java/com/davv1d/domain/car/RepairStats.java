package com.davv1d.domain.car;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "repairs_stats")
public class RepairStats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "changing")
    private String username;

    @NotNull
    @Column(name = "vin")
    private String vinNumber;

    @NotNull
    @Column(name = "date")
    private LocalDateTime date;

    @NotNull
    @Column(name = "availability")
    private boolean availability;

    public RepairStats(@NotNull String username, @NotNull String vinNumber, @NotNull LocalDateTime date, @NotNull boolean availability) {
        this.username = username;
        this.vinNumber = vinNumber;
        this.date = date;
        this.availability = availability;
    }
}

package com.davv1d.domain.car;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "car_brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "brand_id")
    private Long id;

    @NotNull
    @Column(name = "name", length = 20, unique = true)
    private String name;

    @OneToMany(
            targetEntity = Model.class,
            mappedBy = "brand",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Model> models = new ArrayList<>();

    public Brand(@NotNull String name) {
        this.name = name.toUpperCase();
    }
}




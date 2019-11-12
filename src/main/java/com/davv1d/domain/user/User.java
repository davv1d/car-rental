package com.davv1d.domain.user;

import com.davv1d.domain.rental.Rental;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "username", length = 20, unique = true)
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "role")
    @NotNull
    private String role;

    @OneToMany(
            targetEntity = Rental.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Rental> rentals = new ArrayList<>();

    public User(@NotNull String username) {
        this.username = username;
    }

    public User(@NotNull String username, @NotNull String email) {
        this.username = username;
        this.email = email;
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String email, @NotNull String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

}

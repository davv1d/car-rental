package com.davv1d.domain;

import com.davv1d.domain.constant.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    @NotNull
    private Role role;

    public User(@NotNull String username, @NotNull String password, @NotNull String email, @NotNull Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}

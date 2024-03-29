package com.davv1d.domain.user.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "users_logins")
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "login_date")
    private LocalDateTime loginDate;

    public UserLogin(String username, LocalDateTime loginDate) {
        this.username = username;
        this.loginDate = loginDate;
    }
}

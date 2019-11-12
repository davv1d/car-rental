package com.davv1d.domain.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NewRental {
    private long id;
    private String username;
    private String vinNumber;
    private LocalDateTime dateOfRent;
    private LocalDateTime dateOfReturn;
}

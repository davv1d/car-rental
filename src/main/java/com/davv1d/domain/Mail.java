package com.davv1d.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Mail {
    private String mailTo;
    private String subject;
    private String message;
}

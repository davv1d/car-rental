package com.davv1d.errors;

public class UsernameNotFoundException extends Exception {
    public UsernameNotFoundException(String message) {
        super(message);
    }
}

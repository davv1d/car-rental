package com.davv1d.service.validate;

import com.davv1d.functional.Result;

import java.util.Optional;
import java.util.function.Function;

public class ExistValidator {

    public static <U, T> Result<T> ifExists(U value, Function<U, Optional<T>> f) {
        Optional<T> apply = f.apply(value);
        return apply.map(Result::success).orElseGet(() -> Result.failure(value.toString() + "DOESN'T EXIST"));
    }

    public static <U, T> Result<U> ifItDoesNotExist(U value, Function<U, Optional<T>> f) {
        Optional<T> apply = f.apply(value);
        if (apply.isPresent()) {
            return Result.failure(value.toString() + " EXISTS");
        } else {
            return Result.success(value);
        }
    }
 }

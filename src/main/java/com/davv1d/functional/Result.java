package com.davv1d.functional;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Result<V> implements Serializable {
    private Result() {
    }
    public abstract String getErrorMessage();

    public abstract boolean effect(Consumer<V> c);

    public abstract <U> U effect(Function<V, U> success, Function<String, U> failure);

    public abstract V getOrElse(Supplier<V> c);

    public abstract <U> Result<U> map(Function<V, U> f);

    public abstract <U> Result<U> flatMap(Function<V, Result<U>> f);

    public abstract void forEach(Consumer<V> success, Consumer<String> failure);

    public abstract void errorEffect(Consumer<String> errorMessage);

    public static class Failure<V> extends Result<V> {
        private final String message;

        private Failure(String message) {
            this.message = message;
        }

        @Override
        public String getErrorMessage() {
            return message;
        }

        @Override
        public boolean effect(Consumer<V> c) {
            return false;
        }

        @Override
        public <U> U effect(Function<V, U> success, Function<String, U> failure) {
            return failure.apply(message);
        }

        @Override
        public V getOrElse(Supplier<V> c) {
            return c.get();
        }

        @Override
        public <U> Result<U> map(Function<V, U> f) {
            return failure(message);
        }

        @Override
        public <U> Result<U> flatMap(Function<V, Result<U>> f) {
            return failure(message);
        }

        @Override
        public void forEach(Consumer<V> success, Consumer<String> failure) {
            failure.accept(message);
        }

        @Override
        public void errorEffect(Consumer<String> c) {
            c.accept(message);
        }
    }

    public static class Success<V> extends Result<V> {
        private final V value;

        private Success(V value) {
            super();
            this.value = value;
        }


        @Override
        public String getErrorMessage() {
            return "";
        }

        @Override
        public boolean effect(Consumer<V> c) {
            c.accept(value);
            return true;
        }

        @Override
        public <U> U effect(Function<V, U> success, Function<String, U> failure) {
            return success.apply(value);
        }

        @Override
        public V getOrElse(Supplier<V> c) {
            return value;
        }

        @Override
        public <U> Result<U> map(Function<V, U> f) {
            try {
                return success(f.apply(value));
            } catch (Exception e) {
                return failure(e.getMessage());
            }
        }

        @Override
        public <U> Result<U> flatMap(Function<V, Result<U>> f) {
            try {
                return f.apply(value);
            } catch (Exception e) {
                return failure(e.getMessage());
            }
        }

        @Override
        public void forEach(Consumer<V> success, Consumer<String> failure) {
            success.accept(value);
        }

        @Override
        public void errorEffect(Consumer<String> errorMessage) {}
    }

    public static <V> Result<V> failure(String message) {
        return new Failure<>(message);
    }

    public static <V> Result<V> success(V value) {
        return new Success<>(value);
    }
}
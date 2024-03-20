package dev.gravitycode.caimito.java;

@FunctionalInterface
public interface Callback<T> {

    void call(T t);

}

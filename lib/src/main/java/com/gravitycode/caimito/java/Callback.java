package com.gravitycode.caimito.java;

@FunctionalInterface
public interface Callback<T> {

    void call(T t);

}

package com.gravitycode.caimito.java.collections;

@FunctionalInterface
public interface Filter<T> {

    boolean keep(T t);

}

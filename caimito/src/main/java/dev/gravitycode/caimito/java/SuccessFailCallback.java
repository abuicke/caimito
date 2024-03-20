package dev.gravitycode.caimito.java;

public interface SuccessFailCallback<T> {

    void onSuccess(T t);

    void onFailure(Throwable error);

}

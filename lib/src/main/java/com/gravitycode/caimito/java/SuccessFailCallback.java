package ie.moses.caimito;

public interface SuccessFailCallback<T> {

    void onSuccess(T t);

    void onFailure(Throwable error);

}

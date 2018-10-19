package ie.moses.caimito;

@FunctionalInterface
public interface Callback<T> {

    void call(T t);

}

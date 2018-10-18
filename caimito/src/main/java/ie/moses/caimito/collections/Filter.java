package ie.moses.caimito.collections;

public interface Filter<T> {

    boolean keep(T t);

}

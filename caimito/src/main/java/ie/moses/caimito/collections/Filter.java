package ie.moses.caimito.collections;

@FunctionalInterface
public interface Filter<T> {

    boolean keep(T t);

}

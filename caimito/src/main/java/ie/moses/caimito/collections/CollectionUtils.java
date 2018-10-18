package ie.moses.caimito.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class CollectionUtils {

    @SafeVarargs
    public static <T> List<T> list(T... ts) {
        List<T> list = new ArrayList<>(ts.length);
        list.addAll(Arrays.asList(ts));
        return list;
    }

    public static <T> T getRandomElement(List<T> list) {
        int randomIndex = (int) (Math.random() * list.size());
        return list.get(randomIndex);
    }

    public static <T> List<T> search(Collection<T> collection, Filter<T> filter) {
        List<T> items = new ArrayList<>();
        for (T t : collection) {
            if (filter.keep(t)) {
                items.add(t);
            }
        }

        return items;
    }

}

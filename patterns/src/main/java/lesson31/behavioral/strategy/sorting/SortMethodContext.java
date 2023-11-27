package lesson31.behavioral.strategy.sorting;

import java.util.List;

public class SortMethodContext {

    private final SortingMethod method;

    public SortMethodContext(SortingMethod method) {
        this.method = method;
    }

    public <T extends Comparable<T>> void sort(List<T> items) {
        method.sort(items);
    }

}

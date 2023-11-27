package lesson31.behavioral.strategy.sorting.sorting_methods;

import lesson31.behavioral.strategy.sorting.SortingMethod;

import java.util.List;

public class BubbleSort implements SortingMethod {
    @Override
    public <T extends Comparable<T>> void sort(List<T> items) {
        int n = items.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (items.get(j).compareTo(items.get(j + 1)) > 0) {
                    T temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                }
            }
        }
    }
}

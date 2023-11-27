package lesson31.behavioral.strategy.sorting.sorting_methods;

import lesson31.behavioral.strategy.sorting.SortingMethod;

import java.util.List;

public class TreeSort implements SortingMethod {
    @Override
    public <T extends Comparable<T>> void sort(List<T> items) {
        Tree<T> tree = new Tree<>(items.get(0));

        int n = items.size();

        for (int i = 1; i < n; i++) {
            tree.insert(new Tree<>(items.get(i)));
        }

        items.clear();
        tree.traverseAscending(new Tree.AddToSortedList<>(items));
    }
}

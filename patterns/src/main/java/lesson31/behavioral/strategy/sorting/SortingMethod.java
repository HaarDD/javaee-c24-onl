package lesson31.behavioral.strategy.sorting;

import java.util.List;

public interface SortingMethod {
    <T extends Comparable<T>> void sort(List<T> items);
}
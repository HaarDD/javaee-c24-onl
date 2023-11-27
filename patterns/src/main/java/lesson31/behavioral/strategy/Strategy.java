package lesson31.behavioral.strategy;

import lesson31.behavioral.strategy.sorting.SortMethodContext;
import lesson31.behavioral.strategy.sorting.sorting_methods.BubbleSort;
import lesson31.behavioral.strategy.sorting.sorting_methods.TreeSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Strategy {

    public static void main(String[] args) {


        //Сортировка пузырьком
        List<Integer> integerList = new ArrayList<>(List.of(-4, 2, 8, 1, 6));
        SortMethodContext bubbleSortContext = new SortMethodContext(new BubbleSort());
        bubbleSortContext.sort(integerList);
        System.out.println("Сортировка пузырьком: " + Arrays.toString(integerList.toArray()));

        System.out.println();

        //Сортировка деревом
        List<Double> doubleList = new ArrayList<>(List.of(-4.5, 2.2, 8.1, 1.0, 6.4));
        SortMethodContext treeSortContext = new SortMethodContext(new TreeSort());
        treeSortContext.sort(doubleList);
        System.out.println("Сортировка деревом: " + Arrays.toString(doubleList.toArray()));

    }
}

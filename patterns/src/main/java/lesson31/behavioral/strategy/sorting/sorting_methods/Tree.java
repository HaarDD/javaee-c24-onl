package lesson31.behavioral.strategy.sorting.sorting_methods;

import java.util.List;

public class Tree<T extends Comparable<T>> {
    T value;
    private Tree<T> leftTree = null;
    private Tree<T> rightTree = null;

    public Tree(T value) {
        this.value = value;
    }

    public void insert(Tree<T> tree) {
        if (tree.value.compareTo(value) < 0) {
            if (leftTree != null) {
                leftTree.insert(tree);
            } else leftTree = tree;
        } else {
            if (rightTree != null) {
                rightTree.insert(tree);
            } else rightTree = tree;
        }
    }

    public void traverseAscending(NodeAction<T> action) {
        if (leftTree != null) {
            leftTree.traverseAscending(action);
        }
        action.act(this);
        if (rightTree != null) {
            rightTree.traverseAscending(action);
        }
    }

    public interface NodeAction<T extends Comparable<T>> {
        void act(Tree<T> tree);
    }

    public static class AddToSortedList<T extends Comparable<T>> implements NodeAction<T> {
        List<T> list;

        public AddToSortedList(List<T> list) {
            this.list = list;
        }

        public void act(Tree<T> tree) {
            list.add(tree.value);
        }
    }
}

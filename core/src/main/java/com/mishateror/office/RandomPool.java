package com.mishateror.office;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Random pool.
 *
 * @param <T> the type parameter
 */
public class RandomPool<T> {
    private List<T> items;
    private Random random;

    /**
     * Instantiates a new Random pool.
     */
    public RandomPool() {
        this.items = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Add.
     *
     * @param item the item
     */
    public void add(T item) {
        if (item != null) {
            items.add(item);
        }
    }

    /**
     * Gets random.
     *
     * @return the random
     */
    public T getRandom() {
        if (items.isEmpty()) {
            return null;
        }
        int index = random.nextInt(items.size());
        return items.get(index);
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
        return items.size();
    }
}

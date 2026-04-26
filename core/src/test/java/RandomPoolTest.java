package com.mishateror.office;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomPoolTest {

    @Test
    public void testAddAndSize() {
        RandomPool<String> pool = new RandomPool<>();
        assertEquals(0, pool.size());

        pool.add("Item 1");
        pool.add("Item 2");

        assertEquals(2, pool.size());
    }

    @Test
    public void testGetRandomFromEmptyPool() {
        RandomPool<Integer> pool = new RandomPool<>();
        assertNull(pool.getRandom());
    }

    @Test
    public void testGetRandom() {
        RandomPool<String> pool = new RandomPool<>();
        pool.add("Only Item");

        String item = pool.getRandom();
        assertEquals("Only Item", item);
    }
}

package ch10;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class QuickSortTest {
    @Test
    public void testQuickSort() {
        QuickSort quickSort = new QuickSort();

        int[] array1 = { 7, 4, 5, 9, 1};
        quickSort.sort(array1);
        assertArrayEquals(new int[] {1, 4, 5, 7, 9}, array1);

        int[] array2 = { 12, 7, 3, 0, 4};
        quickSort.sort(array2);
        assertArrayEquals(new int[] {0, 3, 4, 7, 12}, array2);
    }
}
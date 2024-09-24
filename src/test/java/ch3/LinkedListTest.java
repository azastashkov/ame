package ch3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LinkedListTest {
    // 3.11.5
    // Find nth node from the end of a linked list
    @Test
    public void findNthNodeFromTheEndOfLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        assertEquals(10, list.size());

        assertEquals(6, (int) list.lastOf(3));
    }

    // 3.11.10
    // Check whether the given linked list either null-terminated or end in a cycle
    @Test
    public void checkWhetherLinkedListHasCycle() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            list.add(i);
        }

        assertFalse(list.hasCycle());

        list.addAndLink(7, 2); // make cycle 7 -> 2

        assertTrue(list.hasCycle());
    }

    // 3.11.12
    // Check whether the given linked list is null-terminated or not.
    // If there's a cycle, find the start node of the loop.
    @Test
    public void findStartNodeOfLoopInLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }

        assertFalse(list.hasCycle());
        assertEquals(-1, list.indexOfCycleStart());

        list.addAndLink(8, 3); // make cycle 8 -> 3

        assertTrue(list.hasCycle());
        assertEquals(3, list.indexOfCycleStart());
    }

    // 3.11.15
    // Check whether the given linked list is null-terminated or not.
    // If there's a cycle, find the length of the loop.
    @Test
    public void findLengthOfLoopInLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }

        assertFalse(list.hasCycle());
        assertEquals(-1, list.indexOfCycleStart());

        list.addAndLink(8, 3);

        assertTrue(list.hasCycle());
        assertEquals(6, list.size() - (list.indexOfCycleStart() - 1));
    }

    // 3.11.16
    // Insert a node in a sorted linked list
    @Test
    public void insertNodeInLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        for (int i = 6; i < 10; i++) {
            list.add(i);
        }

        list.insert(5, 5);

        assertEquals(5, (int) list.get(5));
        assertEquals(10, list.size());
    }

    // 3.11.17
    // Reverse a singly linked list
    @Test
    public void reverseSinglyLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
            assertEquals(i, (int) list.get(i));
        }

        list.reverse();

        for (int i = 0, j = 9; i < 10; i++, j--) {
            assertEquals(j, (int) list.get(i));
        }
    }

    // 3.11.24
    // Suppose there are two singly linked list both of which intersect at some point
    // and become a single linked list. The head or start pointers of both of the lists
    // are known, but the intersecting node is not known. Also, the number of nodes in each
    // of the lists before they intersect is unknown and mey be different in each list.
    // List1 may have n nodes before it reaches the intersection point, and List2 might
    // have m nodes before it reaches the intersection point where m and n may be m = n,
    // m < n or m > n. Give an algorithm for finding the merging point.
    @Test
    public void findMergingPointOfTwoLinkedLists() {
        LinkedList<Integer> list1 = new LinkedList<>();
        for (int i = 0; i <= 3; i++) {
            list1.add(i);
        }

        LinkedList<Integer> list2 = new LinkedList<>();
        for (int i = 4; i <= 6; i++) {
            list2.add(i);
        }
        for (int i = 2; i <= 3; i++) {
            list2.add(i);
        }

        assertEquals(2, findIntersection(list1, list2));

        LinkedList<Integer> list3 = new LinkedList<>();
        for (int i = 7; i <= 11; i++) {
            list3.add(i);
        }

        LinkedList<Integer> list4 = new LinkedList<>();
        for (int i = 12; i <= 13; i++) {
            list4.add(i);
        }
        for (int i = 10; i <= 11; i++) {
            list4.add(i);
        }

        assertEquals(10, findIntersection(list4, list3));
    }

    // 3.11.32
    // Given two sorted linked lists, merge them into the third one in sorted order
    @Test
    public void mergeTwoSortedLinkedLists() {
        LinkedList<Integer> list1 = new LinkedList<>();
        list1.add(1);
        list1.add(4);
        list1.add(5);
        list1.add(7);

        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(2);
        list2.add(3);
        list2.add(5);
        list2.add(6);
        list2.add(8);

        LinkedList<Integer> merged = new LinkedList<>();
        while (list1.size() > 0 && list2.size() > 0) {
            Integer value1 = list1.removeFirst();
            Integer value2 = list2.removeFirst();

            if (value1 < value2) {
                merged.add(value1);
                merged.add(value2);
            } else if (value2 < value1) {
                merged.add(value2);
                merged.add(value1);
            } else {
                merged.add(value1);
                merged.add(value2);
            }
        }

        while (list1.size() > 0) {
            merged.add(list1.removeFirst());
        }

        while (list2.size() > 0) {
            merged.add(list2.removeFirst());
        }

        assertEquals(9, merged.size());

        LinkedList<Integer> expected = new LinkedList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        expected.add(5);
        expected.add(6);
        expected.add(7);
        expected.add(8);

        assertEquals(merged, expected);
    }

    // 3.11.39
    // Is the linked list palindrome or not
    @Test
    public void isPalindrome() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        for (int i = 4; i >= 0; i--) {
            list.add(i);
        }

        assertTrue(list.isPalindrome());
    }

    // 3.11.41
    // For a given k value (k > 0) reverse blocks of k nodes in a linked list
    @Test
    public void reverseBlocksOfKNodesInLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }

        list.reverseInBlocks(3);

        LinkedList<Integer> expected = new LinkedList<>();
        expected.add(3);
        expected.add(2);
        expected.add(1);
        expected.add(6);
        expected.add(5);
        expected.add(4);
        expected.add(9);
        expected.add(8);
        expected.add(7);
        expected.add(10);
    }

    // 3.11.57
    // Given a list, rotate the list to the right by k places, where k is non-negative
    // E.g. given 1->2->3->4->5->NULL and k = 2, return 4->5->1->2->3->NULL
    @Test
    public void rotateLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }

        list.rotate(2);

        LinkedList<Integer> expected = new LinkedList<>();
        expected.add(4);
        expected.add(5);
        expected.add(1);
        expected.add(2);
        expected.add(3);

        assertEquals(expected, list);

        LinkedList<Integer> list2 = new LinkedList<>();
        for (int i = 1; i <= 3; i++) {
            list2.add(i);
        }

        list2.rotate(3);

        LinkedList<Integer> expected2 = new LinkedList<>();
        expected2.add(1);
        expected2.add(2);
        expected2.add(3);

        assertEquals(expected2, list2);
    }

    private int findIntersection(LinkedList<Integer> shorterList, LinkedList<Integer> longerList) {
        for (int si = 0, li = longerList.size() - shorterList.size(); si < longerList.size(); li++, si++) {
            if (longerList.get(li).equals(shorterList.get(si))) {
                return longerList.get(li);
            }
        }
        return -1;
    }
}

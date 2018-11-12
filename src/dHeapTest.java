import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class dHeapTest {

    @Test
    public void testSize() {
        dHeap<Integer> testHeap = new dHeap<>();
        for(int i = 0; i < 5; i++){
            testHeap.add(i);
        }
        assertEquals(5, testHeap.size());
        testHeap.remove();
        testHeap.remove();
        testHeap.add(7);
        assertEquals(4, testHeap.size());
        testHeap.clear();
        assertEquals(0, testHeap.size());
    }

    @Test
    public void testAdd() {
        dHeap<Integer> testHeap = new dHeap<>(5);
        for(int i = 0; i < 10; i++){
            testHeap.add(i);
        }
        assertEquals(new Integer(9) , testHeap.element());
        assertEquals(10, testHeap.size());
        testHeap.clear();
        testHeap.add(0);
        assertEquals(1,testHeap.size());

    }

    @Test
    public void testRemove() {
        dHeap<Integer> testHeap = new dHeap<>(3, 5, false);
        for(int i = 0; i < 10; i++){
            testHeap.add(i);
        }
        assertEquals(new Integer(0), testHeap.remove());
        assertEquals(9, testHeap.size());

        assertEquals(new Integer(1), testHeap.remove());
    }

    @Test
    public void testClear() {
        dHeap<Integer> testHeap = new dHeap<>(3, 5, true);
        for(int i = 0; i < 10; i++){
            testHeap.add(i);
        }
        testHeap.clear();
        assertEquals(0, testHeap.size());
        for(int i = 0; i < 20; i++){
            testHeap.add(i);
        }
        testHeap.clear();
        assertEquals(0, testHeap.size());
        for(int i = 0; i < 20; i++){
            testHeap.add(i);
        }
        for(int i = 0; i < 10; i++){
            testHeap.remove();
        }
        testHeap.clear();
        assertEquals(0, testHeap.size());
    }

    @Test
    public void testElement() {
        dHeap<Integer> testHeap = new dHeap<>(3, 5, true);
        for(int i = 0; i < 10; i++){
            testHeap.add(i);
        }
        assertEquals(new Integer(9), testHeap.element());
        for(int i = 0; i < 5; i++){
            testHeap.remove();
        }
        assertEquals(new Integer(4), testHeap.element());
        for(int i = 0; i < 10; i++){
            testHeap.add(i);
        }
        for(int i = 0; i < 5; i++){
            testHeap.remove();
        }
        assertEquals(new Integer(4), testHeap.element());
    }
}
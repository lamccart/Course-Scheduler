import java.util.*;

public class dHeap<T extends Comparable<? super T>>
        implements dHeapInterface<T> {

    private T[] heap; //heap array
    private int d; //branching factor
    private int nelems;
    private boolean isMaxHeap; //boolean to indicate whether heap is max or min

    public static final int DEFAULT_SIZE = 9;
    public static final int BINARY_BRANCHING = 2;

    /**
     * Initializes a binary max heap with capacity = 9
     */
    public dHeap() {
        this.heap = (T[]) new Comparable[DEFAULT_SIZE];
        this.d = BINARY_BRANCHING;
        this.isMaxHeap = true;
    }

    /**
     * Initializes a binary max heap with a given initial capacity.
     *
     * @param heapSize The initial capacity of the heap.
     */
    public dHeap(int heapSize) {
        this.heap = (T[]) new Comparable[heapSize];
        this.d = BINARY_BRANCHING;
        this.isMaxHeap = true;
    }

    /**
     * Initializes a d-ary heap (with a given value for d), with a given initial
     * capacity.
     *
     * @param d The number of child nodes each node in the heap should have.
     * @param heapSize The initial capacity of the heap.
     * @param isMaxHeap indicates whether the heap should be max or min
     * @throws IllegalArgumentException if d is less than one.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int d, int heapSize, boolean isMaxHeap)
            throws IllegalArgumentException {
        if( d < 1){
            throw new IllegalArgumentException();
        }else{
            this.heap = (T[]) new Comparable[heapSize];
            this.d = d;
            this.isMaxHeap = isMaxHeap;
        }
    }
    private void trickleDown(int index){
        int childIndex = child(index);
        T value = heap[index];

        while (childIndex < nelems) {
            // Find the max among the node and all the node's children
            T xtrValue = value;
            int xtrIndex = -1;
            for (int i = 0; i < 2 && i + childIndex < nelems; i++) {
                if (comparison(heap[i + childIndex], xtrValue)){
                    xtrValue = heap[i + childIndex];
                    xtrIndex = i + childIndex;
                }
            }
            int compareXtr = xtrValue.compareTo(value);
            if (compareXtr == 0) {
                break;
            }else{
                swap(index, xtrIndex);
                index = xtrIndex;
                childIndex = child(index);
            }
        }
    }

    private void bubbleUp(int index){
        while (index > 0) {

            int parentIndex = parent(index);
            if (comparison(heap[index], heap[parentIndex])){
                swap(index, parentIndex);
                index = parentIndex;
            }else{
                break;
            }
        }
    }

    private boolean comparison(T child, T parent){
        if(isMaxHeap){
            return child.compareTo(parent) > 0;
        }else{
            return child.compareTo(parent) < 0;
        }
    }

    private void resize(){
        heap = Arrays.copyOf(heap, heap.length * BINARY_BRANCHING);
    }

    private void swap(int index1, int index2){
        T tmp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = tmp;
    }

    private int parent(int index){
        return (index - 1)/ d;
    }

    private int child(int index){
        return d * index + 1;
    }


    @Override
    public int size() {
        return nelems;

    }

    @Override
    public void add(T data) throws NullPointerException {
        if(data == null){
            throw new NullPointerException();
        }else{

            if(heap.length - 1 <= nelems){
                resize();
            }
            /* Percolate up */
            heap[nelems] = data;
            nelems++;
            bubbleUp(nelems-1);
        }
    }

    @Override
    public T remove() throws NoSuchElementException {
        if(heap[0] == null ){
            throw new NoSuchElementException();
        }else{
            T keyItem = heap[0];
            if(nelems>=0){
                heap[0] = heap[nelems - 1];
                nelems--;
                trickleDown(0);
            }
            return keyItem;

        }
    }

    @Override
    public void clear() {
        heap = (T[]) new Comparable[heap.length];
        nelems = 0;
    }

    @Override
    public T element() {
        return heap[0];
    }
}

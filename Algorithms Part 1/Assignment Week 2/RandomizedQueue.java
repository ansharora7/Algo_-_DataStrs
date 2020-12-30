import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private ArrayList new_arr;
    private int size = 0;

    //ArrayList
    private class ArrayList<Type> implements Iterable<Type> {
        Type[] arr = (Type[]) new Object[10];


        //change capacity
        public void newCapacity(int i) {
            if (i == 0) {
                int newIncreasedCapacity = arr.length * 2;
                arr = Arrays.copyOf(arr, newIncreasedCapacity);
            }
            else if (i == 1) {
                int newDecreasedCapacity = arr.length / 2;
                arr = Arrays.copyOf(arr, newDecreasedCapacity);
            }

        }

        // add an item
        public void add(Type item) {
            if (size == arr.length) {
                newCapacity(0);
            }
            arr[size] = item;   //increases size after appending
            size += 1;
        }

        //remove an item
        public Type remove(int index) {
            if (size <= arr.length / 4) {
                newCapacity(1);
            }

            Type removedItem = arr[index];
            for (int i = index; i < size - 1; i++) {
                //System.out.println(arr[i] + " from Array");
                arr[i] = arr[i + 1];
            }
            size -= 1;

            return removedItem;

        }

        public int size() {
            int count = 0;
            for (int i = 0; i < size; i++) {
                count += 1;
            }
            return count;
        }


        //random queue
        @Override
        public Iterator<Type> iterator() {
            return new ArrayIterator(arr);
        }
    }


    //Iterator
    private class ArrayIterator<Type> implements Iterator<Type> {
        private Type[] arrayList;
        int position = 0;

        public ArrayIterator(Type[] newArray) {
            arrayList = newArray;

        }


        // check if next element not null
        public boolean hasNext() {
            return (position != size);
        }

        // next element
        public Type next() {
            if (arrayList[position] != null) {
                return (arrayList[position++]);

            }
            else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    //Main Method RandomizedQueue

    // construct an empty randomized queue
    public RandomizedQueue() {
        new_arr = new ArrayList();
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return (new_arr.size());
    }

    // add the item
    public void enqueue(Item item) {
        if (item != null) {
            new_arr.add(item);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (new_arr.size() != 0) {
            int index = StdRandom.uniform(0, new_arr.size());
            // System.out.println(index + " index");
            return ((Item) new_arr.remove(index));
        }
        else {
            throw new NoSuchElementException();
        }
    }


    // return a random item (but do not remove it)
    public Item sample() {
        if (new_arr.size() != 0) {
            int index = StdRandom.uniform(0, size);
            return ((Item) new_arr.arr[index]);
        }
        else {
            throw new NoSuchElementException();
        }
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        StdRandom.shuffle(new_arr.arr, 0, new_arr.size());
        return (new ArrayIterator(new_arr.arr));
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> new_queue = new RandomizedQueue<>();
        new_queue.enqueue(5);
        new_queue.enqueue(6);
        System.out.println(new_queue.isEmpty());
        System.out.println(new_queue.size());
        //System.out.println(new_queue.sample());
        //System.out.println(new_queue.dequeue() + "deque");
        //System.out.println(new_queue.dequeue() + "deque");
        System.out.println(new_queue.isEmpty());
        //for (int i : new_queue) {
        //System.out.println(i);
        //}


    }
}




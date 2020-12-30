import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private LinkedList list;

    // construct an empty deque
    public Deque() {
        list = new LinkedList();
    }

    private class Node<Item> {
        Item data;
        Node next;
    }

    private int count = 0;

    // Linked list
    private class LinkedList<Item> implements Iterable<Item> {


        public class Node<Item> {
            Item data;
            Node next;
        }

        public Node head;


        // insert at end
        public void insertLast(Item data) {
            Node node = new Node();
            node.data = data;
            node.next = null;

            if (head == null) {
                head = node;
            }
            else {
                Node n = head;
                while (n.next != null) {
                    n = n.next;
                }
                n.next = node;
            }
            count += 1;

        }

        // insert at beginning
        public void insertFirst(Item data) {
            Node node = new Node();
            node.data = data;
            node.next = null;
            node.next = head;
            head = node;
            count += 1;
        }

        // delete from beginning
        public Item deleteFirst() {
            Node copy = head;
            head = head.next;
            count -= 1;
            return ((Item) copy.data);
        }

        // delete from end
        public Item deleteLast() {
            if (head == null) {
                throw new IllegalArgumentException();
            }
            if (head.next == null) {
                Item ans = ((Item) head.data);
                head = null;
                count -= 1;
                return ans;
            }
            // Find the second last node
            Node temp = head;
            while (temp.next.next != null) {
                temp = temp.next;
            }
            // change the second last node next pointer to null
            Node ans = temp.next;
            temp.next = null;
            count -= 1;
            return ((Item) ans.data);
        }


        // size of list
        public int sizeOfList() {
            //Node n = head;
            //if (head == null) {
            //System.out.print("head");
            //  return 0;
            //}

            //while (n.next != null) {
            //System.out.println(n.data);
            //count += 1;
            //n = n.next;
            return count;
        }

        @Override
        public Iterator<Item> iterator() {
            return new ListIterator(head);
        }


        public class ListIterator<Item> implements Iterator<Item> {
            private Node current;

            public ListIterator(Node first) {
                current = first;
            }

            // check if next element not null
            public boolean hasNext() {
                return current != null;
            }

            // next node
            public Item next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                else {
                    Node temp = current;
                    current = current.next;
                    return ((Item) temp.data);

                }
            }

        }
    }


    // Main class Deque


    // is the deque empty?
    public boolean isEmpty() {
        return (list.head == null);


    }

    // return the number of items on the deque
    public int size() {
        return (list.sizeOfList());
    }


    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        else {
            list.insertFirst(item);
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        else {
            list.insertLast(item);
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (list.head == null) {
            throw new NoSuchElementException();
        }
        else {
            return ((Item) list.deleteFirst());
        }
    }


    // remove and return the item from the back
    public Item removeLast() {
        if (list.head == null) {
            throw new NoSuchElementException();
        }
        else {
            return ((Item) list.deleteLast());
        }
    }

    private void remove() {
        throw new NoSuchElementException();
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return list.new ListIterator(list.head);
    }


    // unit Dequeing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        System.out.println(deque.isEmpty());
        deque.addFirst(5);
        deque.addFirst(6);

        for (int i : deque) {
            System.out.println(i);
        }
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());


    }
}



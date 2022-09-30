package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.setPrev(prev);
            this.setNext(next);
            this.setValue(value);
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
            tail = head;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            if (head == null) {
                add(value);
            } else {
                Node<T> newNode = new Node<>(null, value, head);
                head.setPrev(newNode);
                head = newNode;
                size++;
            }
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeOnIndex = find(index);
        nodeOnIndex.getPrev().setNext(new Node<>(nodeOnIndex.getPrev(), value, nodeOnIndex));
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(this::add);
    }

    @Override
    public T get(int index) {
        return find(index).getValue();
    }

    private Node<T> find(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(String.format(
                    "Index %d out of size %d",
                    index, size)
            );
        }
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return currentNode;
            }
            currentNode = currentNode.getNext();
        }
        return currentNode;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = find(index);
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeOnIndex = find(index);
        if (nodeOnIndex == head && nodeOnIndex == tail) {
            head = tail = null;
        } else if (nodeOnIndex == head) {
            head.getNext().setPrev(null);
            head = head.getNext();
        } else if (nodeOnIndex == tail) {
            tail.getPrev().setNext(null);
            tail = tail.getPrev();
        } else {
            nodeOnIndex.getNext().setPrev(nodeOnIndex.getPrev());
            nodeOnIndex.getPrev().setNext(nodeOnIndex.getNext());
        }
        size--;
        return nodeOnIndex.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if ((currentNode.getValue() == null && object == null)
                    || (currentNode.getValue() != null && currentNode.getValue().equals(object))) {
                remove(i);
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}

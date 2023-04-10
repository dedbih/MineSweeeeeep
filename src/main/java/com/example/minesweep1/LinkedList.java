package com.example.minesweep1;

public class LinkedList<T> {
    private Node<T> head;

    public LinkedList() {
        this.head = null;
    }

    public void addNode(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
        } else {
            Node<T> currNode = head;

            while (currNode.getNext() != null) {
                currNode = currNode.getNext();
            }

            currNode.setNext(newNode);
        }
    }

    public void removeNode(T data) {
        if (head == null) {
            return;
        }

        if (head.getData().equals(data)) {
            head = head.getNext();
        } else {
            Node<T> prevNode = head;
            Node<T> currNode = head.getNext();

            while (currNode != null) {
                if (currNode.getData().equals(data)) {
                    prevNode.setNext(currNode.getNext());
                    break;
                }

                prevNode = currNode;
                currNode = currNode.getNext();
            }
        }
    }
    public void clearList() {
        head = null;
    }


    public void traverseList() {
        if (head == null) {
            return;
        }

        Node<T> currNode = head;

        while (currNode != null) {
            System.out.print(currNode.getData() + " ");
            currNode = currNode.getNext();
        }
    }
}
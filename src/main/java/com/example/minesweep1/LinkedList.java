package com.example.minesweep1;

public class LinkedList {
    private Node head;

    public LinkedList() {
        this.head = null;
    }

    public void addNode(int data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
        } else {
            Node currNode = head;

            while (currNode.getNext() != null) {
                currNode = currNode.getNext();
            }

            currNode.setNext(newNode);
        }
    }

    public void removeNode(int data) {
        if (head == null) {
            return;
        }

        if (head.getData() == data) {
            head = head.getNext();
        } else {
            Node prevNode = head;
            Node currNode = head.getNext();

            while (currNode != null) {
                if (currNode.getData() == data) {
                    prevNode.setNext(currNode.getNext());
                    break;
                }

                prevNode = currNode;
                currNode = currNode.getNext();
            }
        }
    }

    public void traverseList() {
        if (head == null) {
            return;
        }

        Node currNode = head;

        while (currNode != null) {
            System.out.print(currNode.getData() + " ");
            currNode = currNode.getNext();
        }
    }
}

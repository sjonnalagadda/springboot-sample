package org.example.test;

public class SampleLinkedList {

    private Node head;

    SampleLinkedList(Node head) {
        this.head = head;
    }

    void add(Node node) {
        node.next = this.head;
        head = node;
    }

    void print() {
        Node currentNode = this.head;
        while(currentNode != null) {
            System.out.println(currentNode.value);
            currentNode = currentNode.next;
        }
    }

    void reverse() {
        Node current = this.head.next;
        Node prev = this.head;
        prev.next = null;
        while(current != null) {
            Node temp = current.next;
            current.next = prev;
            prev = current;
            current = temp;
        }
        this.head = prev;
    }

}

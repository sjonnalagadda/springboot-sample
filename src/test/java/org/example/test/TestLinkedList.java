package org.example.test;

import org.junit.jupiter.api.Test;

public class TestLinkedList {

    @Test
    public void test() {
        Node one = new Node(1, null);
        SampleLinkedList linkedList = new SampleLinkedList(one);
        Node two = new Node(2, null);
        linkedList.add(two);
        Node three = new Node(3, null);
        linkedList.add(three);
        Node four = new Node(4, null);
        linkedList.add(four);
        Node five = new Node(5, null);
        linkedList.add(five);
        System.out.println("before......");
        linkedList.print();
        linkedList.reverse();
        System.out.println("after......");
        linkedList.print();
    }

}

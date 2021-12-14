package com.github.houbb.sandglass.core.support.struct;

import java.util.LinkedList;

/**
 * 固定大小的数组
 * @author binbin.hou
 * @since 0.0.9
 */
public class FixedLinkedList<E> extends LinkedList<E> {

    private final int size;

    public FixedLinkedList(int size) {
        this.size = size;
    }

    @Override
    public boolean add(E o) {
        super.add(o);
        if (size() > size) {
            super.remove();
        }
        return true;
    }

    public static void main(String[] args) {
        FixedLinkedList<String> list = new FixedLinkedList<>(2);
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        System.out.println(list);
    }

}

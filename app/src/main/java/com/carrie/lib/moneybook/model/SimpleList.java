package com.carrie.lib.moneybook.model;

/**
 * Created by Carrie on 2018/3/29.
 */

public class SimpleList {
    public int id;
    public String name;

    public SimpleList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "SimpleList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

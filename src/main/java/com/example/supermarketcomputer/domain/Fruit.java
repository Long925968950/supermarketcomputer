package com.example.supermarketcomputer.domain;

/**
 * 表示一种水果
 */
public class Fruit {
    // 水果名称
    private final String name;
    // 单价（元/斤）
    private final double price;

    public Fruit(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
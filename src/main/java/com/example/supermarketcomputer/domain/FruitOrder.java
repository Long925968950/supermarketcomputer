package com.example.supermarketcomputer.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物订单，记录每种水果购买的斤数
 */
public class FruitOrder {
    private final Map<Fruit, Integer> quantities = new HashMap<>();

    public void add(Fruit fruit, int quantity) {
        quantities.put(fruit, quantity);
    }

    public Map<Fruit, Integer> getQuantities() {
        return quantities;
    }
}
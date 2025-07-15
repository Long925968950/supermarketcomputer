package com.example.supermarketcomputer.domain;

import java.util.List;

/**
 * 水果的配置信息，包含价格和促销规则
 */
public class FruitConfig {
    private final String name;
    private final double price;
    private final List<PromotionRule> rules;

    public FruitConfig(String name, double price, List<PromotionRule> rules) {
        this.name = name;
        this.price = price;
        this.rules = rules;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public List<PromotionRule> getRules() {
        return rules;
    }
}
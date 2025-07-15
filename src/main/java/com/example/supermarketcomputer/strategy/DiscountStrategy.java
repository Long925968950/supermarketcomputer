package com.example.supermarketcomputer.strategy;

/**
 * 打折策略：将原价乘以折扣率
 */
public class DiscountStrategy implements PromotionStrategy {
    private final double discountRate;

    public DiscountStrategy(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public double apply(double originalPrice) {
        return originalPrice * discountRate;
    }
}
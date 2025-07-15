package com.example.supermarketcomputer.strategy;

/**
 * 满减策略：达到一定金额后减固定金额
 */
public class DeductStrategy implements PromotionStrategy {
    private final int minSpend;
    private final int deductAmount;

    public DeductStrategy(int minSpend, int deductAmount) {
        this.minSpend = minSpend;
        this.deductAmount = deductAmount;
    }

    @Override
    public double apply(double originalPrice) {
        if (originalPrice >= minSpend) {
            return originalPrice - deductAmount;
        }
        return originalPrice;
    }
}
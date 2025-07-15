package com.example.supermarketcomputer.domain;

/**
 * 促销规则对象，如打折或满减
 */
public class PromotionRule {
    // 类型：DISCOUNT 或 DEDUCT
    private final String type;
    // 折扣率（0~1）
    private final double rate;
    // 满减门槛金额
    private final int minSpend;
    // 减免金额
    private final int deductAmount;

    public PromotionRule(String type, double rate, int minSpend, int deductAmount) {
        this.type = type;
        this.rate = rate;
        this.minSpend = minSpend;
        this.deductAmount = deductAmount;
    }

    public String getType() {
        return type;
    }

    public double getRate() {
        return rate;
    }

    public int getMinSpend() {
        return minSpend;
    }

    public int getDeductAmount() {
        return deductAmount;
    }
}
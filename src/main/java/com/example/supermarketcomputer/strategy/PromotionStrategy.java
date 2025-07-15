package com.example.supermarketcomputer.strategy;

/**
 * 促销策略接口
 */
public interface PromotionStrategy {
    double apply(double originalPrice);
}
package com.example.supermarketcomputer.service;

import com.example.supermarketcomputer.domain.FruitOrder;

/**
 * 定价服务接口
 */
public interface FruitPricingService {
    double calculateTotalPrice(FruitOrder order);
}
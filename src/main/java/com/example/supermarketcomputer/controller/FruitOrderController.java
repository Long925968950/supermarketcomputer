package com.example.supermarketcomputer.controller;

import com.example.supermarketcomputer.domain.Fruit;
import com.example.supermarketcomputer.domain.FruitOrder;
import com.example.supermarketcomputer.service.FruitPricingService;

import java.util.Map;

/**
 * 控制器用于接收订单并调用计算服务
 */
public class FruitOrderController {
    private final FruitPricingService pricingService;

    public FruitOrderController(FruitPricingService pricingService) {
        this.pricingService = pricingService;
    }

    public double processOrder(Map<String, Integer> fruitQuantities, Map<String, Fruit> fruitMap) {
        FruitOrder order = new FruitOrder();
        for (Map.Entry<String, Integer> entry : fruitQuantities.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue();
            if (fruitMap.containsKey(name)) {
                order.add(fruitMap.get(name), quantity);
            }
        }
        return pricingService.calculateTotalPrice(order);
    }
}
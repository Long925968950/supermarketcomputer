package com.example.supermarketcomputer.service.impl;

import com.example.supermarketcomputer.domain.*;
import com.example.supermarketcomputer.strategy.PromotionStrategy;
import com.example.supermarketcomputer.service.FruitPricingService;

import java.util.*;

/**
 * 默认定价服务实现类，支持动态配置水果和促销策略
 */
public class DefaultFruitPricingService implements FruitPricingService {

    private Map<String, FruitConfig> fruitConfigMap = new HashMap<>();
    private List<PromotionStrategy> strategies = new ArrayList<>();

    public DefaultFruitPricingService(List<FruitConfig> configs, List<PromotionStrategy> strategies) {
        for (FruitConfig config : configs) {
            fruitConfigMap.put(config.getName(), config);
        }
        this.strategies = strategies;
    }

    @Override
    public double calculateTotalPrice(FruitOrder order) {
        double total = 0.0;

        // 处理每个水果的价格和商品级促销
        for (Map.Entry<Fruit, Integer> entry : order.getQuantities().entrySet()) {
            Fruit fruit = entry.getKey();
            int quantity = entry.getValue();

            FruitConfig config = fruitConfigMap.get(fruit.getName());
            if (config == null) continue;

            double price = config.getPrice();

            // 应用该水果的促销规则
            for (PromotionRule rule : config.getRules()) {
                if ("DISCOUNT".equals(rule.getType())) {
                    price *= rule.getRate();
                } else if ("DEDUCT".equals(rule.getType()) && price * quantity >= rule.getMinSpend()) {
                    price -= rule.getDeductAmount();
                }
            }

            total += price * quantity;
        }

        // 应用订单级促销策略（如满减）
        for (PromotionStrategy strategy : strategies) {
            total = strategy.apply(total);
        }

        return total;
    }
}
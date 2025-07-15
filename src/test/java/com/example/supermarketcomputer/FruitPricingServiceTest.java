package com.example.supermarketcomputer;

import com.example.supermarketcomputer.domain.*;
import com.example.supermarketcomputer.controller.FruitOrderController;
import com.example.supermarketcomputer.service.impl.DefaultFruitPricingService;
import com.example.supermarketcomputer.strategy.DeductStrategy;
import com.example.supermarketcomputer.strategy.DiscountStrategy;
import com.example.supermarketcomputer.strategy.PromotionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitPricingServiceTest {

    private Map<String, Fruit> fruitMap;
    private List<FruitConfig> fruitConfigs;
    private List<PromotionStrategy> strategies;

    @BeforeEach
    public void setUp() {
        // 初始化水果对象
        Fruit apple = new Fruit("苹果", 8.0);
        Fruit strawberry = new Fruit("草莓", 13.0);
        Fruit mango = new Fruit("芒果", 20.0);

        // 构建水果映射
        fruitMap = new HashMap<>();
        fruitMap.put(apple.getName(), apple);
        fruitMap.put(strawberry.getName(), strawberry);
        fruitMap.put(mango.getName(), mango);

        // 默认水果配置（草莓 8 折）
        fruitConfigs = Arrays.asList(
                new FruitConfig(apple.getName(), apple.getPrice(), Collections.emptyList()),
                new FruitConfig(strawberry.getName(), strawberry.getPrice(),
                        Collections.singletonList(new PromotionRule("DISCOUNT", 0.8, 0, 0))),
                new FruitConfig(mango.getName(), mango.getPrice(), Collections.emptyList())
        );

        // 满减规则：满 100 减 10
        strategies = Collections.singletonList(new DeductStrategy(100, 10));
    }

    @Test
    public void testNoPromotion() {
        // 苹果2斤 + 芒果1斤，无促销
        FruitPricingService service = new DefaultFruitPricingService(fruitConfigs, Collections.emptyList());
        FruitOrderController controller = new FruitOrderController(service);

        Map<String, Integer> order = new HashMap<>();
        order.put("苹果", 2);
        order.put("芒果", 1);

        double expected = 2 * 8 + 1 * 20; // 36.0
        assertEquals(expected, controller.processOrder(order, fruitMap));
    }

    @Test
    public void testDiscountOnly() {
        // 草莓3斤，应用8折
        FruitPricingService service = new DefaultFruitPricingService(fruitConfigs, Collections.emptyList());
        FruitOrderController controller = new FruitOrderController(service);

        Map<String, Integer> order = new HashMap<>();
        order.put("草莓", 3);

        double expected = 3 * 13 * 0.8; // 31.2
        assertEquals(expected, controller.processOrder(order, fruitMap));
    }

    @Test
    public void testDeductOnly() {
        // 苹果10斤 + 芒果5斤，总价180，应用满减10元
        FruitPricingService service = new DefaultFruitPricingService(fruitConfigs, strategies);
        FruitOrderController controller = new FruitOrderController(service);

        Map<String, Integer> order = new HashMap<>();
        order.put("苹果", 10);
        order.put("芒果", 5);

        double expected = 10 * 8 + 5 * 20 - 10; // 170.0
        assertEquals(expected, controller.processOrder(order, fruitMap));
    }

    @Test
    public void testCombinedDiscountAndDeduct() {
        // 苹果5斤 + 草莓4斤 + 芒果2斤，总价128.4，未满减
        FruitPricingService service = new DefaultFruitPricingService(fruitConfigs, strategies);
        FruitOrderController controller = new FruitOrderController(service);

        Map<String, Integer> order = new HashMap<>();
        order.put("苹果", 5);
        order.put("草莓", 4);
        order.put("芒果", 2);

        double expected = 5 * 8 + 4 * 13 * 0.8 + 2 * 20; // 128.4
        assertEquals(expected, controller.processOrder(order, fruitMap));
    }

    @Test
    public void testCustomFruitAndPromotion() {
        // 自定义香蕉，9折 + 满50减5
        Fruit banana = new Fruit("香蕉", 6.0);
        fruitMap.put(banana.getName(), banana);

        List<FruitConfig> customFruitConfigs = Collections.singletonList(
                new FruitConfig(banana.getName(), banana.getPrice(),
                        Collections.singletonList(new PromotionRule("DISCOUNT", 0.9, 0, 0)))
        );

        List<PromotionStrategy> customStrategies = Collections.singletonList(
                new DeductStrategy(50, 5)
        );

        FruitPricingService service = new DefaultFruitPricingService(customFruitConfigs, customStrategies);
        FruitOrderController controller = new FruitOrderController(service);

        Map<String, Integer> order = new HashMap<>();
        order.put("香蕉", 10);

        double expected = 10 * 6 * 0.9 - 5; // 49.0
        assertEquals(expected, controller.processOrder(order, fruitMap));
    }

    @Test
    public void testEmptyOrder() {
        FruitPricingService service = new DefaultFruitPricingService(fruitConfigs, strategies);
        FruitOrderController controller = new FruitOrderController(service);

        Map<String, Integer> order = new HashMap<>();
        double expected = 0.0;
        assertEquals(expected, controller.processOrder(order, fruitMap));
    }
}
package com.msilva.reactiveorders.caches;

import com.msilva.reactiveorders.domains.OrderDomain;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderCache {

    private final ConcurrentHashMap<String, OrderDomain> orderCache = new ConcurrentHashMap<>();

    public Collection<OrderDomain> getAllOrdersCache() {
        return orderCache.values();
    }

    public void putOrderCache(OrderDomain order) {
        orderCache.put(order.getSymbol(), order);
    }
}

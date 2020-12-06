package com.msilva.reactiveorders.serversentevent;

import com.msilva.reactiveorders.domains.OrderDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

    private static Set<Subscriber> subscribers = new HashSet<>();

    @Override
    public Subscriber subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
        return subscriber;
    }

    @Override
    public void notifySubscribers(OrderDomain order) {
        LOGGER.info("Try send notify to all subscribers...");
        subscribers.forEach(subscriber -> {
            try {
                subscriber.send(order);
                subscriber.onError(error -> {
                    subscriber.completeWithError(error);
                    LOGGER.info("Seems the subscriber has already dropped out. Remove it from the list");
                    subscribers.remove(subscriber);
                });
            } catch (IOException e) {
                LOGGER.warn("Failed to notify subscriber about the new Order = {}",order.toString());
            }
        });

    }

    @Override
    public int countSubscribers() {
        return subscribers.size();
    }
}


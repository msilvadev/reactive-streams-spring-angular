package com.msilva.reactiveorders.serversentevent;

import com.msilva.reactiveorders.domains.OrderDomain;

public interface ISubscriptionService {

    public Subscriber subscribe(Subscriber subscriber);
    public void notifySubscribers(OrderDomain order);
    public int countSubscribers();
}

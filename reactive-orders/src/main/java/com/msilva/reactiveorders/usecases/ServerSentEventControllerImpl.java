package com.msilva.reactiveorders.usecases;

import com.msilva.reactiveorders.caches.OrderCache;
import com.msilva.reactiveorders.domains.OrderDomain;
import com.msilva.reactiveorders.serversentevent.ISubscriptionService;
import com.msilva.reactiveorders.serversentevent.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/server-sent")
public class ServerSentEventControllerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSentEventControllerImpl.class);

    private OrderCache orderCache;

    private ISubscriptionService subscriptionService;

    public ServerSentEventControllerImpl(OrderCache orderCache,
                                         ISubscriptionService subscriptionService) {
        this.orderCache = orderCache;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public Subscriber subscriber() throws IOException {
        LOGGER.info("New subscriber created!");
        return subscriptionService.subscribe(new Subscriber());
    }

    @GetMapping(path = "/snapshot", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<OrderDomain> snapshot() {
        LOGGER.info("Send snapshot...");
        return orderCache.getAllOrdersCache();
    }


    @GetMapping("/count")
    public int activeServerSentEvents() {
        return subscriptionService.countSubscribers();
    }

    @PostMapping
    public void saveOrder(@RequestBody OrderDomain order) {
        orderCache.putOrderCache(order);
        subscriptionService.notifySubscribers(order);
    }
}

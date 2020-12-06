package com.msilva.reactiveorders.serversentevent;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class Subscriber extends SseEmitter{

    public Subscriber() {
        super(Long.MAX_VALUE);
    }
}

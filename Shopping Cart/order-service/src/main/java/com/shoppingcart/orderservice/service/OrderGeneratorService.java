package com.shoppingcart.orderservice.service;

import com.shoppingcart.base.domain.dto.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

public class OrderGeneratorService {
    private static Random RAND = new Random();
    private AtomicLong id = new AtomicLong();
    private Executor executor;
    private KafkaTemplate<Long, Order> template;

    public OrderGeneratorService(Executor executor, KafkaTemplate<Long, Order> template) {
        this.executor = executor;
        this.template = template;
    }

    @Async
    public void generate(){
       for (int i=0;i<10000;i++){
           int x=RAND.nextInt(5)+1;
           Order o = new Order(id.incrementAndGet(),RAND.nextLong(100)+1,RAND.nextLong(100)+1,"NEW");
           o.setPrice(100*x);
           o.setProductCount(x);
           template.send("Orders",o.getId(),o);
       }
    }
}

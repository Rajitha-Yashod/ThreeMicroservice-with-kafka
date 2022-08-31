package com.shoppingcart.paymentservice;

import com.shoppingcart.base.domain.dto.Order;
import com.shoppingcart.paymentservice.domain.Customer;
import com.shoppingcart.paymentservice.repository.CustomerRepository;
import com.shoppingcart.paymentservice.service.OrderManageService;
import jdk.jpackage.internal.Log;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Random;

@SpringBootApplication
@EnableKafka
public class PaymentServiceApplication {
	private static final Logger LOG =LoggerFactory.getLogger(PaymentServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

	@Autowired
	OrderManageService orderManageService;

	@KafkaListener(id="orders",topics = "orders",groupId = "payment")
	public void onEvent(Order o){
		LOG.info("Received {}",o);
		if(o.getStatus().equals("NEW"))
			orderManageService.reserve(o);
		else
			orderManageService.confirm(o);
	}

	@Autowired
	private CustomerRepository repository;

	public void generateData(){
		Random random = new Random();
		Faker faker=new Faker();
		for (int i =0;i<100;i++){
			int count = random.nextInt();
			Customer c=new Customer(null,faker.name().firstName(),count,0);
			repository.save(c);
		}
	}


}

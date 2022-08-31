package com.shoppingcart.stockservice;

import com.shoppingcart.base.domain.dto.Order;
import com.shoppingcart.stockservice.domain.Product;
import com.shoppingcart.stockservice.repository.ProductRepository;
import com.shoppingcart.stockservice.service.OrderManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import javax.annotation.PostConstruct;
import java.util.Random;

@SpringBootApplication
@EnableKafka
public class StockServiceApplication {
	private static final Logger LOG = LoggerFactory.getLogger(StockServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}

	@Autowired
	OrderManageService orderManageService;
	@KafkaListener(id="orders",topics = "orders",groupId = "stock")
	public void onEvent(Order o){
		LOG.info("Received:{}",o);
		if(o.getStatus().equals("NEW"))
			orderManageService.reserve(o);
		else
			orderManageService.confirm(o);
	}

	@Autowired
	private ProductRepository repository;

	@PostConstruct
	public void generateData(){
		Random random = new Random();
		for (int i=0;i<1000;i++){
			int count=random.nextInt(1000);
			Product product = new Product(null,"Product"+i,count,0);
			repository.save(product);
		}
	}


}

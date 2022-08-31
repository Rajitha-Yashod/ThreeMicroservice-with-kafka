package com.shoppingcart.paymentservice.repository;

import com.shoppingcart.paymentservice.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
}

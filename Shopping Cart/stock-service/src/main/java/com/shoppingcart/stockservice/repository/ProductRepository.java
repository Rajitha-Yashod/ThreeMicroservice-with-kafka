package com.shoppingcart.stockservice.repository;

import com.shoppingcart.stockservice.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {
}

package com.bikkadit.electronic.store.repositories;


import com.bikkadit.electronic.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
}

package com.example.ch12.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<ProductEntity, Integer> {

    @PostFilter("filterObject.owner == authentication.name")
    List<ProductEntity> findProductByNameContains(String text);

//    Query("""SELECT p FROM Product p WHERE
//               p.name LIKE %:text% AND
//               p.owner=?#{authentication.name}
//           """
//          List<Product> findProductByNameContains(String text);
}

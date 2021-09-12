package com.punsara.priceenginecalculator.priceenginecalculatorbackend.repository;

import com.punsara.priceenginecalculator.priceenginecalculatorbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Punsara Prathibha
 * Specified Data Repository for deal with Product Entity related data in the database
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

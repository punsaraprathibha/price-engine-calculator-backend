package com.punsara.priceenginecalculator.priceenginecalculatorbackend.entity;

import com.punsara.priceenginecalculator.priceenginecalculatorbackend.dto.ProductDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Punsara Prathibha
 * JPA ORM Model for table 'Product' in the Database
 */
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "price")
    private Double price;
    @Column(name = "units_per_carton")
    private Integer unitsPerCarton;
    @Column(name = "product_label")
    private String productLabel;

    public ProductDto toDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(this.getId());
        productDto.setProductName(this.getProductName());
        productDto.setUnitsPerCarton(this.getUnitsPerCarton());
        productDto.setPrice(this.getPrice());
        productDto.setProductLabel(this.getProductLabel());
        return productDto;
    }
}

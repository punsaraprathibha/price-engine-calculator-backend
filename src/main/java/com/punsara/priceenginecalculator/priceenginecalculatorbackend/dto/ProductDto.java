package com.punsara.priceenginecalculator.priceenginecalculatorbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Punsara Prathibha
 * Data Transfer Object used to transfer Product data between
 * service layer and controller layer of the system
 */
@Getter
@Setter
public class ProductDto implements Serializable {

    private Long id;
    private String productName;
    private Double price;
    private Integer unitsPerCarton;
    private String productLabel;
}

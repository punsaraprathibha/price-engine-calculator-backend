package com.punsara.priceenginecalculator.priceenginecalculatorbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Punsara Prathibha
 * Data Transfer Object used to transfer ProductPrice data between
 * service layer and controller layer of the system
 */
@Getter
@Setter
public class ProductPriceDto implements Serializable {

    private String productName;
    private Double price = 0.00;
    private Integer units = 0;
}

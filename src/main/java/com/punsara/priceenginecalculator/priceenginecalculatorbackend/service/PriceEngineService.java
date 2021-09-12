package com.punsara.priceenginecalculator.priceenginecalculatorbackend.service;

import com.punsara.priceenginecalculator.priceenginecalculatorbackend.dto.ProductDto;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.dto.ProductPriceDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Punsara Prathibha
 * Service to manage all the operations related to the PriceEngine
 */
public interface PriceEngineService {

    /**
     * @return : java.util.List<ProductDto>
     */
    List<ProductDto> getAllProducts();

    /**
     * @param productId : java.lang.Long
     * @param quantity  : java.lang.Integer
     * @return : java.util.List<ProductPriceDto>
     */
    List<ProductPriceDto> getProductPriceList(Long productId, Integer quantity);

    /**
     * @param productId : java.lang.Long
     * @param cartons : java.lang.Integer
     * @param units : java.lang.Integer
     * @return : ProductPriceDto
     */
    ProductPriceDto calculatePrice(Long productId, Integer cartons, Integer units);
}

package com.punsara.priceenginecalculator.priceenginecalculatorbackend.controller;

import com.punsara.priceenginecalculator.priceenginecalculatorbackend.dto.ProductDto;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.dto.ProductPriceDto;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.service.PriceEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Punsara Prathibha
 * REST Controller to manage all the REST APIS related to Price Engine Screen
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/price-engine")
public class PriceEngineController {

    private final PriceEngineService priceEngineService;

    /**
     * @return : org.springframework.http.ResponseEntity<List<ProductDto>>
     * Retrieves all the existing products from the database
     */
    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(priceEngineService.getAllProducts());
    }

    /**
     * @param productId : java.lang.Long
     * @param quantity  : java.lang.Integer
     * @return : org.springframework.http.ResponseEntity<List<ProductPriceDto>>
     * Retrieves by calculating product prices list by product and the quantity
     */
    @GetMapping(value = "/product-price-list")
    public ResponseEntity<List<ProductPriceDto>> getProductPriceList(@RequestParam("productId") Long productId,
                                                                     @RequestParam("quantity") Integer quantity) {
        return ResponseEntity.ok(priceEngineService.getProductPriceList(productId, quantity));
    }
}

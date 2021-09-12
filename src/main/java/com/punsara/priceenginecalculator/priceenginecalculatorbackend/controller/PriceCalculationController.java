package com.punsara.priceenginecalculator.priceenginecalculatorbackend.controller;

import com.punsara.priceenginecalculator.priceenginecalculatorbackend.dto.ProductPriceDto;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.service.PriceEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Punsara Prathibha
 * REST Controller to manage all the REST APIS related to Price Calculation Screen
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/price-calculator")
public class PriceCalculationController {

    private final PriceEngineService priceEngineService;

    /**
     * @param productId : java.lang.Long
     * @param cartons   : java.lang.Integer
     * @param units     : java.lang.Integer
     * @return : org.springframework.http.ResponseEntity<ProductPriceDto>
     */
    @GetMapping(value = "/calculate-price")
    public ResponseEntity<ProductPriceDto> calculateProductPrice(@RequestParam("productId") Long productId,
                                                                 @RequestParam("cartons") Integer cartons,
                                                                 @RequestParam("units") Integer units) {
        return ResponseEntity.ok(priceEngineService.calculatePrice(productId, cartons, units));
    }
}

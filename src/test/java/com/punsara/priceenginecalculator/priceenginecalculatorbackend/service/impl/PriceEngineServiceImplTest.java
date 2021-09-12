package com.punsara.priceenginecalculator.priceenginecalculatorbackend.service.impl;

import com.punsara.priceenginecalculator.priceenginecalculatorbackend.dto.ProductDto;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.dto.ProductPriceDto;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.entity.Parameter;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.entity.Product;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.exception.PriceEngineCalcSystemException;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.exception.SystemWarningException;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.repository.ParameterRepository;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.repository.ProductRepository;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.service.PriceEngineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PriceEngineServiceImplTest {

    @Autowired
    private PriceEngineService priceEngineService;

    @MockBean
    private ParameterRepository parameterRepository;
    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void parameterSetUp() {

        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(1L);
        product1.setProductName("Penguin-ears");
        product1.setUnitsPerCarton(20);
        product1.setPrice(175.00);
        product1.setProductLabel("Rare");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setProductName("Horseshoe");
        product2.setUnitsPerCarton(5);
        product2.setPrice(825.00);
        product2.setProductLabel("");

        productList.add(product1);
        productList.add(product2);

        Parameter parameter = new Parameter();
        parameter.setId(1L);
        parameter.setLaborPercentage(0.3);
        parameter.setCartonDiscount(0.1);
        parameter.setDiscountEligibleCartons(3);

        Mockito.when(productRepository.findAll()).thenReturn(productList);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        Mockito.when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        Mockito.when(productRepository.findById(3L)).thenReturn(Optional.empty());

        Mockito.when(parameterRepository.findFirstBy()).thenReturn(java.util.Optional.of(parameter));
    }

    // Parameter functionality Tests
    @Test
    @DisplayName("Get system parameters")
    void testGetParameters() {
        Optional<Parameter> parameter = parameterRepository.findFirstBy();
        parameter.ifPresent(value -> Assertions.assertAll(
                () -> Assertions.assertEquals(value.getCartonDiscount(), 0.1),
                () -> Assertions.assertEquals(value.getLaborPercentage(), 0.3),
                () -> Assertions.assertEquals(value.getDiscountEligibleCartons(), 3)
        ));
    }

    // Product functionality Tests
    @Test
    @DisplayName("Get all products")
    void testGetProducts() {
        List<ProductDto> products = priceEngineService.getAllProducts();
        if (!products.isEmpty()) {
            Assertions.assertAll(
                    () -> Assertions.assertEquals(products.get(0).getProductName(), "Penguin-ears"),
                    () -> Assertions.assertEquals(products.get(1).getProductName(), "Horseshoe")
            );
        }
    }

    // Calculator functionality Tests
    @Test
    @DisplayName("Get price for a single carton of the product")
    void testProductPriceForSingleCarton() {
        ProductPriceDto productPriceDto = priceEngineService.calculatePrice(2L, 1, 0);
        Assertions.assertEquals(productPriceDto.getPrice(), 825.00);
    }

    @Test
    @DisplayName("Get price for valid multiple cartons and of multiple units the product")
    void testProductPriceForMultipleCartonsAndUnits() {
        ProductPriceDto productPriceDto = priceEngineService.calculatePrice(2L, 2, 4);
        Assertions.assertEquals(productPriceDto.getPrice(), 2508.00);
    }

    @Test
    @DisplayName("Get price for valid multiple cartons and of multiple invalid units the product")
    void testProductPriceForMultipleCartonsAndInvalidUnits() {
        Exception exception = Assertions.assertThrows(SystemWarningException.class, () -> {
            priceEngineService.calculatePrice(2L, 2, 8);
        });

        Assertions.assertEquals(exception.getMessage(), "Invalid no of units entered for the Product!");
    }

    @Test
    @DisplayName("Get price for both invalid cartons and units of the product")
    void testProductPriceForBothInvalidCartonsAndUnits() {
        Exception exception = Assertions.assertThrows(SystemWarningException.class, () -> {
            priceEngineService.calculatePrice(2L, -1, -3);
        });

        Assertions.assertEquals(exception.getMessage(), "Invalid format of units or cartons entered!");
    }

    @Test
    @DisplayName("Get price for both null values for cartons and units of the product")
    void testProductPriceForBothNullValuesForCartonsAndUnits() {
        Exception exception = Assertions.assertThrows(SystemWarningException.class, () -> {
            priceEngineService.calculatePrice(2L, null, null);
        });

        Assertions.assertEquals(exception.getMessage(), "Invalid format of units or cartons entered!");
    }

    @Test
    @DisplayName("Get price for valid multiple cartons and of multiple units the product for invalid id")
    void testProductPriceForMultipleCartonsAndUnitsForInvalidId() {
        Exception exception = Assertions.assertThrows(PriceEngineCalcSystemException.class, () -> {
            priceEngineService.calculatePrice(3L, 2, 4);
        });

        Assertions.assertEquals(exception.getMessage(), "Unable to find the Product!");
    }

    // Price Engine functionality Tests
    @Test
    @DisplayName("Get the result size of the product price list")
    void testProductPriceListSize() {
        List<ProductPriceDto> productPriceList = priceEngineService.getProductPriceList(2L, 50);
        Assertions.assertEquals(productPriceList.size(), 50);
    }

    @Test
    @DisplayName("Get the result size of the product price list compatible with less than 50")
    void testProductPriceListNotFixed() {
        List<ProductPriceDto> productPriceList = priceEngineService.getProductPriceList(2L, 40);
        Assertions.assertEquals(productPriceList.size(), 40);
    }

    @Test
    @DisplayName("Get product price list for invalid id")
    void testProductPriceListForInvalidId() {
        Exception exception = Assertions.assertThrows(PriceEngineCalcSystemException.class, () -> {
            priceEngineService.getProductPriceList(3L, 50);
        });

        Assertions.assertEquals(exception.getMessage(), "Unable to find the Product!");
    }
}

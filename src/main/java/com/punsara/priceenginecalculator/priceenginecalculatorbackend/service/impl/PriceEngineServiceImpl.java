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
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Punsara Prathibha
 * Service implementation of PriceEngineService to manage
 * all the operations related to the PriceEngine
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PriceEngineServiceImpl implements PriceEngineService {

    private final ProductRepository productRepository;
    private final ParameterRepository parameterRepository;

    /**
     * @return java.util.List<ProductDto>
     */
    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(Product::toDto).collect(Collectors.toList());
    }

    /**
     * @param productId : java.lang.Long
     * @param quantity  : java.lang.Integer
     * @return : java.util.List<ProductPriceDto>
     */
    @Override
    public List<ProductPriceDto> getProductPriceList(Long productId, Integer quantity) {
        if (null == quantity || 0 > quantity) {
            throw new SystemWarningException(Utility.EX_INVALID_FORMAT_OF_PRODUCT_QUANTITY_REQUESTED);
        }

        List<ProductPriceDto> productPriceDtoList = new ArrayList<>();
        Optional<Parameter> parameter = parameterRepository.findFirstBy();
        if (parameter.isPresent()) {
            Optional<Product> productById = productRepository.findById(productId);
            if (productById.isPresent()) {
                for (int index = 1; index <= quantity; index++) {
                    // calculate prices
                    productPriceDtoList.add(calculateProductPrice(productById.get(), parameter.get(), index));
                }
            } else {
                log.error(Utility.EX_UNABLE_TO_FIND_THE_PRODUCT);
                throw new PriceEngineCalcSystemException(Utility.EX_UNABLE_TO_FIND_THE_PRODUCT);
            }
        } else {
            log.error(Utility.EX_PARAMETER_DETAILS_NOT_AVAILABLE);
            throw new PriceEngineCalcSystemException(Utility.EX_PARAMETER_DETAILS_NOT_AVAILABLE);
        }
        return productPriceDtoList;
    }

    /**
     * @param productId : java.lang.Long
     * @param cartons : java.lang.Integer
     * @param units : java.lang.Integer
     * @return : ProductPriceDto
     */
    @Override
    public ProductPriceDto calculatePrice(Long productId, Integer cartons, Integer units) {
        Optional<Parameter> firstByParam = parameterRepository.findFirstBy();
        Optional<Product> productById = productRepository.findById(productId);

        if (productById.isEmpty()) {
            log.error(Utility.EX_UNABLE_TO_FIND_THE_PRODUCT);
            throw new PriceEngineCalcSystemException(Utility.EX_UNABLE_TO_FIND_THE_PRODUCT);
        }

        if (firstByParam.isPresent()) {
            Parameter parameter = firstByParam.get();
            Product product = productById.get();

            if (null == cartons || null == units || 0 > cartons || 0 > units) {
                throw new SystemWarningException(Utility.EX_INVALID_FORMAT_OF_UNITS_OR_CARTONS_ENTERED);
            }
            if (units >= product.getUnitsPerCarton()) {
                throw new SystemWarningException(Utility.EX_INVALID_NO_OF_UNITS_ENTERED);
            }

            ProductPriceDto productPriceDto = new ProductPriceDto();

            // Calculate Labor Tax
            double laborTax = product.getPrice() * parameter.getLaborPercentage();
            // Calculate Unit Price per single unit of the Product
            double unitPrice = ((product.getPrice() + (laborTax)) / product.getUnitsPerCarton());

            // Calculate Carton Price
            productPriceDto.setPrice(productPriceDto.getPrice() + calculateCartonPrices(cartons, parameter, product));

            // Calculate Single Unit Price
            productPriceDto.setPrice(productPriceDto.getPrice() + (units * unitPrice));

            productPriceDto.setProductName(product.getProductName());
            return productPriceDto;
        } else {
            log.error(Utility.EX_PARAMETER_DETAILS_NOT_AVAILABLE);
            throw new PriceEngineCalcSystemException(Utility.EX_PARAMETER_DETAILS_NOT_AVAILABLE);
        }
    }

    /**
     * @param product    : Product
     * @param parameter: Parameter
     * @param quantity:  java.lang.Integer
     * @return ProductPriceDto
     */
    private ProductPriceDto calculateProductPrice(Product product, Parameter parameter, Integer quantity) {
        ProductPriceDto productPriceDto = new ProductPriceDto();

        // Calculate Labor Tax
        double laborTax = product.getPrice() * parameter.getLaborPercentage();
        // Calculate Unit Price per single unit of the Product
        double unitPrice = ((product.getPrice() + (laborTax)) / product.getUnitsPerCarton());

        // Calculate Carton Price
        int noOfCartons = quantity / product.getUnitsPerCarton();
        productPriceDto.setPrice(productPriceDto.getPrice() + calculateCartonPrices(noOfCartons, parameter, product));

        // Calculate Single Unit Price
        int singleUnits = quantity % product.getUnitsPerCarton();
        productPriceDto.setPrice(productPriceDto.getPrice() + (singleUnits * unitPrice));

        productPriceDto.setProductName(product.getProductName());
        productPriceDto.setUnits(quantity);
        return productPriceDto;
    }

    /**
     * @param noOfCartons : int
     * @param parameter   : Parameter
     * @param product     : Product
     * @return : Double
     */
    private Double calculateCartonPrices(int noOfCartons, Parameter parameter, Product product) {
        return noOfCartons < parameter.getDiscountEligibleCartons()
                ? noOfCartons * product.getPrice()
                : (noOfCartons * (product.getPrice() - (product.getPrice() * parameter.getCartonDiscount())));
    }
}

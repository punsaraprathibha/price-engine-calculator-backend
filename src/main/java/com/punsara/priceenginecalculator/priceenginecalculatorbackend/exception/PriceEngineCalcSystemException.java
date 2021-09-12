package com.punsara.priceenginecalculator.priceenginecalculatorbackend.exception;

/**
 * @author Punsara Prathibha
 * Generified Exception class to handle all the System Exceptions through specific format
 */
public class PriceEngineCalcSystemException extends RuntimeException {

    /**
     * No Args Constructor
     */
    public PriceEngineCalcSystemException() {
    }

    /**
     * @param message : java.lang.String
     */
    public PriceEngineCalcSystemException(String message) {
        super(message);
    }

    /**
     * @param message : java.lang.String
     * @param cause   : java.lang.Throwable
     */
    public PriceEngineCalcSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}

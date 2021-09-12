package com.punsara.priceenginecalculator.priceenginecalculatorbackend.exception;

/**
 * @author Punsara Prathibha
 * Generified Exception class to handle all the System Warning Exceptions through specific format
 */
public class SystemWarningException extends PriceEngineCalcSystemException {

    /**
     * No Args Constructor
     */
    public SystemWarningException() {
    }

    /**
     * @param message : java.lang.String
     */
    public SystemWarningException(String message) {
        super(message);
    }

    /**
     * @param message : java.lang.String
     * @param cause   : java.lang.Throwable
     */
    public SystemWarningException(String message, Throwable cause) {
        super(message, cause);
    }
}

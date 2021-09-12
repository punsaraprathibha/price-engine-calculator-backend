package com.punsara.priceenginecalculator.priceenginecalculatorbackend.exception;

/**
 * @author Punsara Prathibha
 * Generified Exception class to handle all the System Info Exceptions through specific format
 */
public class SystemInfoException extends PriceEngineCalcSystemException {

    /**
     * No Args Constructor
     */
    public SystemInfoException() {
    }

    /**
     * @param message : java.lang.String
     */
    public SystemInfoException(String message) {
        super(message);
    }

    /**
     * @param message : java.lang.String
     * @param cause   : java.lang.Throwable
     */
    public SystemInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}

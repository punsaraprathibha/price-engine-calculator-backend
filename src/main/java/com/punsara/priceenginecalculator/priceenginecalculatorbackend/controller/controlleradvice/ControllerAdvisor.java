package com.punsara.priceenginecalculator.priceenginecalculatorbackend.controller.controlleradvice;

import com.punsara.priceenginecalculator.priceenginecalculatorbackend.exception.PriceEngineCalcSystemException;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.exception.SystemInfoException;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.exception.SystemWarningException;
import com.punsara.priceenginecalculator.priceenginecalculatorbackend.util.Utility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Punsara Prathibha
 * Handles All the System Exceptions
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    /**
     * @param ex : java.lang.Exception
     * @return : org.springframework.http.ResponseEntity<String>
     * System Info Exception Handler
     */
    @ExceptionHandler(value = SystemInfoException.class)
    protected ResponseEntity<String> handlePriceEngineInfoException(Exception ex) {
        logger.info(ex.getMessage());
        return ResponseEntity.status(599).body(ex.getMessage());
    }

    /**
     * @param ex : java.lang.Exception
     * @return : org.springframework.http.ResponseEntity<String>
     * System Warning Exception Handler
     */
    @ExceptionHandler(value = SystemWarningException.class)
    protected ResponseEntity<String> handlePriceEngineWarningException(Exception ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(598).body(ex.getMessage());
    }

    /**
     * @param ex : java.lang.Exception
     * @return : org.springframework.http.ResponseEntity<String>
     * System Internal Exception Handler
     */
    @ExceptionHandler(value = {PriceEngineCalcSystemException.class, Exception.class})
    protected ResponseEntity<String> handlePriceEngineCalcInternalServerException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Utility.EX_ROOT);
    }
}

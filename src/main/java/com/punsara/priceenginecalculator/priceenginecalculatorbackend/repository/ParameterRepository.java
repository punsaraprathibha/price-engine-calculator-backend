package com.punsara.priceenginecalculator.priceenginecalculatorbackend.repository;

import com.punsara.priceenginecalculator.priceenginecalculatorbackend.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Punsara Prathibha
 * Specified Data Repository for deal with Parameter Entity related data in the database
 */
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer> {

    Optional<Parameter> findFirstBy();
}

package com.punsara.priceenginecalculator.priceenginecalculatorbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Punsara Prathibha
 * JPA ORM Model for table 'Parameter' in the Database
 */
@Getter
@Setter
@Entity
@Table(name = "parameter")
public class Parameter {

    @Id
    @Column(name = "parameter_id")
    private Long id;
    @Column(name = "labor_percentage")
    private Double laborPercentage;
    @Column(name = "discount_eligible_cartons")
    private Integer discountEligibleCartons;
    @Column(name = "carton_discount")
    private Double cartonDiscount;
}

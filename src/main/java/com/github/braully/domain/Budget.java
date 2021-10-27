package com.github.braully.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(schema = "financial")
public class Budget extends AbstractExpirableEntity implements Serializable {

    @Basic
    protected BigDecimal total;

    @Basic
    protected String typePeriod;

    @Transient
    protected Double percent;

    @ManyToOne(targetEntity = Account.class)
    protected Account account;

    @Basic
    protected String typePeriodConsolidation;

    public Budget() {

    }
}

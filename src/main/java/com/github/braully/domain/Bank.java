package com.github.braully.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bank", schema = "financial")
@DiscriminatorValue("0")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "type_id",
        columnDefinition = "smallint default '0'", length = 1)
public class Bank extends AbstractMigrableEntity {

    protected static final long serialVersionUID = 1L;

    /*
     * 
     */
    @Basic
    protected String name;
    @Basic
    @Column(unique = true)
    protected String number;

    @Override
    public String toString() {
        return name + " (" + number + ')';
    }
}

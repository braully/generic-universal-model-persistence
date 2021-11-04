package com.github.braully.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "sale")
@DiscriminatorValue("0")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "type_id",
        columnDefinition = "smallint default '0'", length = 1)
@Getter
@Setter

public class Inventory extends AbstractGlobalEntity implements IOrganiztionEntityDependent, Serializable {

    @ManyToOne
    protected Organization organization;

    @Basic
    protected String localization;

    @Basic
    protected String name;

    @Basic
    protected String description;

    @Basic
    protected String type;

    protected String preToString() {
        return name;
    }
}

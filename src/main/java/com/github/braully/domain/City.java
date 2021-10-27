package com.github.braully.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"country", "name", "state"}), schema = "base")
@DiscriminatorValue("0")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "type_id",
        columnDefinition = "smallint default '0'", length = 1)
public class City extends AbstractGlobalEntity implements Serializable, IFormatable {

    @Basic
    protected String country;

    @Basic
    protected String phoneticName;

    @Basic
    protected String name;

    @Basic
    protected String state;

    public City() {

    }

    public City(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public boolean isValida() {
        return true;
    }

    public String getNomeFormatado() {
        return this.toString();
    }

    public void setNomeFormatado(String nome) {

    }

    @Override
    public String toString() {
        return format();
    }

    public String format() {
        return name + " (" + state + ')';
    }
}

package com.github.braully.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "base")
@Getter
@Setter
public class LocaleSupported extends AbstractEntity {

    @Basic
    protected String language;
    @Basic
    protected String country;
    @Basic
    protected String variant;
    @Basic
    protected String currency;
}

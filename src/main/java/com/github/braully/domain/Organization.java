package com.github.braully.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(schema = "base")
public class Organization extends AbstractGlobalEntity
        implements INameComparable {

    /*
     *
     */
    @Basic
    protected String name;
    @Basic
    protected String description;
    @Basic
    protected String oficialName;
    @Basic
    protected String fiscalCode;
    @ManyToOne
    protected BinaryFile logo;
    @ManyToOne(cascade = CascadeType.ALL)
    protected Contact contact;
    @ManyToOne
    protected Organization parent;

    public Organization() {
    }

    public String getVisualDescription() {
        return "Logo";
    }

    @Override
    public String toString() {
        return name + " (" + fiscalCode + ')';
    }

    public String getAddressDescription() {
        try {
            return this.contact.getMainAddress().getDescricaoFormatada();
        } catch (Exception e) {
            return "";
        }
    }

    public Address getMainAddress() {
        try {
            return this.contact.getMainAddress();
        } catch (Exception e) {
            return null;
        }
    }

    public String getFiscalCodeClean() {
        String ret = fiscalCode;
        if (ret != null) {
            ret = ret.replaceAll("\\D", "");
        }
        return ret;
    }
}

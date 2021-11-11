/* MIT License
* 
* Copyright (c) 2021 Braully Rocha
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

package io.github.braully.domain;

import io.github.braully.util.UtilValidation;
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
import lombok.experimental.Accessors;

@Entity
@Table(schema = "base")
@DiscriminatorValue("0")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "type_id",
        columnDefinition = "smallint default '0'", length = 1)
@Getter
@Setter @Accessors(chain = true)
public class Address extends AbstractEntity implements Serializable, IFormatable {

    @Basic
    protected String zip;

    @ManyToOne(targetEntity = City.class)
    City city;

    @Basic
    protected String street;

    @Basic
    protected String district;

    @Basic
    protected String addressLine1;

    @Basic
    protected String addressLine2;

    /*@Basic
    @Enumerated
    protected AddressType type = AddressType.Main;

    public static enum AddressType {
        Main
    }*/
    @Basic
    protected String number;

    public Address() {

    }

    public City getCidade() {
        return city;
    }

    public String getBairro() {
        return this.district;
    }

    public String getComplemento() {
        return this.addressLine1;
    }

    @Override
    public String toString() {
        return format();
    }

    public void setCep(String trim) {
        this.zip = trim;
    }

    public void setDescricao(String trim) {
        this.addressLine1 = trim;
    }

    public void setBairro(String string) {
        this.district = string;
    }

    public String getDescricaoFormatada() {
        return format();
    }

    public String format() {
        StringBuilder sb = new StringBuilder();
        boolean init = false;
        if (UtilValidation.isStringValid(this.addressLine1)) {
            sb.append(this.addressLine1);
            init = true;
        }

        if (UtilValidation.isStringValid(this.addressLine2)) {
            if (init) {
                sb.append(", ");
            }
            sb.append(this.addressLine2);
            init = true;
        }

        if (UtilValidation.isStringValid(this.district)) {
            if (init) {
                sb.append(",");
            }
            sb.append(" Bairro ");
            sb.append(this.district);
            init = true;
        }
        if (UtilValidation.isStringValid(this.zip)) {
            if (init) {
                sb.append(" -");
            }
            sb.append(" CEP: ");
            sb.append(this.zip);
            init = true;
        }

        if (this.city != null && this.city.isPersisted()) {
            if (init) {
                sb.append(" - ");
            }
            sb.append(city);
            init = true;
        }
        return sb.toString();
    }

    public Address addressLine1(String string) {
        this.addressLine1 = string;
        return this;
    }

    public Address district(String string) {
        this.district = string;
        return this;
    }

    public Address city(City city) {
        this.city = city;
        return this;
    }

    public Address cep(String string) {
        this.zip = string;
        return this;
    }
}

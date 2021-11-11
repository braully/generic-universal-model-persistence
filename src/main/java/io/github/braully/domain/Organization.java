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

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter @Accessors(chain = true)
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

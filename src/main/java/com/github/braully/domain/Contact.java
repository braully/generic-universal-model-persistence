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

package com.github.braully.domain;

import com.github.braully.util.UtilValidation;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author braully
 */
@Entity
@Table(schema = "base")
@Getter
@Setter
public class Contact extends AbstractGlobalEntity implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    protected Phone mainPhone;

    @ManyToOne(cascade = CascadeType.ALL)
    protected Email mainEmail;

    @ManyToOne(cascade = CascadeType.ALL)
    protected Address mainAddress;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = "base")
    protected Set<Address> extraAddresses;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = "base")
    protected Set<Phone> extraPhones;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = "base")
    protected Set<Email> extraEmails;

    public Contact() {
    }

    public Contact mains() {
        newMains();
        return this;
    }

    public void newMains() {
        if (this.mainPhone == null) {
            this.mainPhone = new Phone();
        }
        if (this.mainEmail == null) {
            this.mainEmail = new Email();
        }
        if (this.mainAddress == null) {
            this.mainAddress = new Address();
        }
    }

    /*public Address getMainAddress() {
        Address address = null;
        if (this.addresses != null) {
            for (Address addr : this.addresses) {
                if (addr.getType() == Address.AddressType.Main) {
                    address = addr;
                    break;
                }
            }
        }
        return address;
    } */
    public void add(Email email) {
        if (this.extraEmails == null) {
            this.extraEmails = new HashSet<>();
        }
        this.extraEmails.add(email);
    }

    public void remove(Email email) {
        if (this.extraEmails == null) {
            return;
        }
        this.extraEmails.remove(email);
    }

    public void add(Address address) {
        if (this.mainAddress == null) {
            this.mainAddress = address;
        } else {
            if (this.extraAddresses == null) {
                this.extraAddresses = new HashSet<>();
            }
            this.extraAddresses.add(address);
        }
    }

    public void remove(Address address) {
        if (this.extraAddresses == null) {
            return;
        }
        this.extraAddresses.remove(address);
    }

    public void add(Iterable<Phone> telefones) {
        if (telefones != null) {
            telefones.forEach(t -> add(t));
        }
    }

    public void add(Phone telefone) {
        if (this.mainPhone == null) {
            this.mainPhone = telefone;
        } else {
            if (this.extraPhones == null) {
                this.extraPhones = new HashSet<>();
            }
            this.extraPhones.add(telefone);
        }
    }

    public void remove(Phone telefone) {
        if (this.extraPhones == null) {
            return;
        }
        this.extraPhones.remove(telefone);
    }

    public Set<Phone> getPhones() {
        Set<Phone> hashSet = new TreeSet<>();
        if (mainPhone != null) {
            hashSet.add(mainPhone);
        }

        if (extraPhones != null) {
            hashSet.addAll(extraPhones);
        }
        return hashSet;
    }

    public boolean hasPhone() {
        if (UtilValidation.isPersisted(mainPhone) && UtilValidation.isStringValid(mainPhone.number)) {
            return true;
        }
        if (this.extraPhones != null && !this.extraPhones.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean hasAddress() {
        if (UtilValidation.isPersisted(mainAddress)) {
            return true;
        }
        return false;
    }

    public String getTelefonesFormatado() {
        String ret = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(this.mainPhone.format());
            for (Phone tel : this.extraPhones) {
                sb.append(", ").append(tel.format());
            }
        } catch (RuntimeException e) {

        }
        ret = sb.toString();
        return ret;
    }
}

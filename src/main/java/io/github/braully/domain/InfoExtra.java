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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author braully
 */
@Getter
@Setter
@Entity
@Table(schema = "base")
public class InfoExtra extends AbstractGlobalEntity {

    // Complex extra properties
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(schema = "base")
    private Set<GenericValue> genericValues;

    // Simple extra properties
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @CollectionTable(schema = "base",
            joinColumns = @JoinColumn(name = "fk_info_extra"))
    private Map<String, String> simpleStringValues = new HashMap<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @CollectionTable(schema = "base",
            joinColumns = @JoinColumn(name = "fk_info_extra"))
    private Map<String, Long> simpleNumberValues = new HashMap<>();

    public InfoExtra() {
    }

    public Map<String, String> getExtraStringValues() {
        return simpleStringValues;
    }

    public Map<String, Long> getExtraNumberValues() {
        return simpleNumberValues;
    }

    public Map<String, String> getExtraValues() {
        return simpleStringValues;
    }

    public void setExtraValues(Map<String, String> extraValues) {
        this.simpleStringValues = extraValues;
    }

    public Map<String, String> getSimpleValues() {
        return this.simpleStringValues;
    }

    public Map<String, Object> getMapAllProps() {
        Map<String, Object> props = new HashMap<>();
        if (simpleStringValues != null) {
            props.putAll(simpleStringValues);
        }
        if (simpleNumberValues != null) {
            props.putAll(simpleNumberValues);
        }
        if (genericValues != null) {
            for (GenericValue g : genericValues) {
                props.put(g.getGenericType().attribute,
                        g.getValue());
            }
        }
        return props;
    }
    
    public <T extends Enum<T>> T getEnumValue(Class<T> aClass, String attrb) {
        T ret = null;
        Long l = this.getLong(attrb);
        if (l != null) {
            T[] enumConstants = aClass.getEnumConstants();
            ret = enumConstants[l.intValue()];
        }
        return ret;
    }

    public void setEnumValue(String attr, Enum enm) {
        if (enm != null) {
            this.setLong(attr, enm.ordinal());
        } else {
            this.setLong(attr, null);
        }
    }

    public void setLong(String attrb, Long value) {
        this.getSimpleNumberValues().put(attrb, value);
    }

    public void setLong(String attrb, long value) {
        this.getSimpleNumberValues().put(attrb, value);
    }

    public Long getLong(String attrb) {
        Long ret = null;
        ret = this.getSimpleNumberValues().get(attrb);
        return ret;
    }

    public void setString(String attrb, String value) {
        this.getSimpleStringValues().put(attrb, value);
    }

    public String getString(String attrb) {
        String ret = null;
        ret = this.getSimpleStringValues().get(attrb);
        return ret;
    }

    public void setDate(String att, Date dt) {
        Long datlg = null;
        if (dt != null) {
            datlg = dt.getTime();
        }
        this.setLong(att, datlg);
    }

    public Date getDate(String att) {
        Date ret = null;
        Long datlg = this.getLong(att);
        if (datlg != null) {
            ret = new Date(datlg);
        }
        return ret;
    }
}

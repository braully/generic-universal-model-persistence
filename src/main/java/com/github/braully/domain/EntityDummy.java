/*
Copyright 2109 Braully Rocha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.github.braully.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.github.braully.constant.Attr;
import com.github.braully.constant.Attrs;
import org.hibernate.loader.entity.CacheEntityLoaderHelper.EntityStatus;

/**
 * Class for test only
 *
 * @author braully rocha
 */
@Getter
@Setter
@Entity
@Table(schema = "legacy")
//public class EntityDummy extends AbstractGlobalEntity {
public class EntityDummy extends AbstractEntity {

    @Basic
    protected String name;
    @Basic
    protected Integer code;
    @Basic
    protected Double fraction;
    @Basic
    protected Boolean checked;
    @Enumerated
    protected EntityStatus statusType;
    @Basic
    protected Date date;
    @Basic
    @Attrs({
        @Attr({"converter", "monetaryAmountBigDecimal"}),
        @Attr({"pattern", "0,00"})
    })
    protected BigDecimal decimal;
    @ManyToOne
    protected EntityDummy parent;
    @OneToMany(mappedBy = "parent")
    protected Set<EntityDummy> childrens;

    @Override
    public String toString() {
        return name;
    }
}

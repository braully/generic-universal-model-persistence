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

import com.github.braully.constant.Attr;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author braully
 */
@Entity
@Table(schema = "sale")
@DiscriminatorValue("0")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "type_id",
        columnDefinition = "smallint default '0'", length = 1)
@Getter
@Setter
public class InventoryItemTransaction extends AbstractEntity {

    @ManyToOne
    protected Inventory inventory;
    @ManyToOne
    protected InventoryItem inventoryItem;

    @Basic
    protected Long quantity;

    @Attr("hidden")
    @Basic
    protected Long scaleQuantity;

    @Basic
    @Attr({"converter", "converterMonetaryBigDecimal"})
    protected BigDecimal actualBalance;

}

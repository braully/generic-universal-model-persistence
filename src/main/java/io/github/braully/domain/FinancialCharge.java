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
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author braully
 */
@Entity
@Table(schema = "financial")
@DiscriminatorValue("0")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "type_id",
        columnDefinition = "smallint default '0'", length = 1)

public class FinancialCharge extends AbstractStatusEntity {

    @Basic
    protected String description;
    @Basic
    protected String instructions;
    @Enumerated(EnumType.ORDINAL)
    @Basic
    protected FactorType typeFactorTrafficTicket;
    @Basic
    protected Long factorTrafficTicket;
    @Enumerated(EnumType.ORDINAL)
    @Basic
    protected TypePeriod typePeriodInterest;
    @Enumerated(EnumType.ORDINAL)
    @Basic
    protected FactorType typeFactorInterestRating;
    @Basic
    protected Long factorInterestRating;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Long getFactorTrafficTicket() {
        return factorTrafficTicket;
    }

    public void setFactorTrafficTicket(Long factorTrafficTicket) {
        this.factorTrafficTicket = factorTrafficTicket;
    }

    public FactorType getTypeFactorTrafficTicket() {
        return typeFactorTrafficTicket;
    }

    public void setTypeFactorTrafficTicket(FactorType typeFactorTrafficTicket) {
        this.typeFactorTrafficTicket = typeFactorTrafficTicket;
    }

    public Long getFactorInterestRating() {
        return factorInterestRating;
    }

    public void setFactorInterestRating(Long factorInterestRating) {
        this.factorInterestRating = factorInterestRating;
    }

    public FactorType getTypeFactorInterestRating() {
        return typeFactorInterestRating;
    }

    public void setTypeFactorInterestRating(FactorType typeFactorInterestRating) {
        this.typeFactorInterestRating = typeFactorInterestRating;
    }

    public TypePeriod getTypePeriodInterest() {
        return typePeriodInterest;
    }

    public void setTypePeriodInterest(TypePeriod typePeriodInterest) {
        this.typePeriodInterest = typePeriodInterest;
    }

    @Override
    public String toString() {
        return description + " (" + typeFactorTrafficTicket + "/" + factorTrafficTicket + " e " + typePeriodInterest + " " + typeFactorInterestRating + " " + factorInterestRating + ')';
    }

    public Long getFatorMulta() {
        return this.factorTrafficTicket;
    }

    public FactorType getTipoFatorMulta() {
        return this.typeFactorTrafficTicket;
    }

    public Long getFatorJuros() {
        return this.factorInterestRating;
    }

    public FactorType getTipoFatorJuros() {
        return this.typeFactorInterestRating;
    }

    public TypePeriod getTipoPeriodoJuros() {
        return this.typePeriodInterest;
    }
}

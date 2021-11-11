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

import java.util.Calendar;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author braully
 */
@Getter
@Setter @Accessors(chain = true)
public class FinancialPeriod implements Comparable<FinancialPeriod> {

    protected int month;

    protected int year;

    protected int index;

    public FinancialPeriod(Date data) {
        if (data != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);
        }
    }

    public FinancialPeriod(int mes, int ano) {
        this.month = mes;
        this.year = ano;
    }

    public int getMes() {
        return month;
    }

    public void setMes(int mes) {
        this.month = mes;
    }

    public int getAno() {
        return year;
    }

    public void setAno(int ano) {
        this.year = ano;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.month;
        hash = 53 * hash + this.year;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        final FinancialPeriod other = (FinancialPeriod) obj;
        if (this.month != other.month) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return month + "/" + year;
    }

    @Override
    public int compareTo(FinancialPeriod o) {
        int ret = 0;
        if (o != null) {
            ret = this.year - o.year;
            if (ret == 0) {
                ret = this.month - o.month;
            }
        }
        return ret;
    }

    public String getDescricaoFormatada() {
        StringBuilder sb = new StringBuilder();
        sb.append(month);
        sb.append("/");
        sb.append(year);
        return sb.toString();
    }

    public int getIndice() {
        return index;
    }

    public void setIndice(int indice) {
        this.index = indice;
    }
}

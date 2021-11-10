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

import io.github.braully.util.UtilDate;
import java.util.Date;

public enum TypePeriod {
    HOURLY, DAILY, MONTHLY, BIMONTHLY,
    QUARTERLY, SEMESTER, YEARLY;

    String name;
    String label;

    TypePeriod() {
        this.name = name();
        this.label = name();
    }

    TypePeriod(String descricao, String periodo) {
        this.name = descricao;
        this.label = periodo;
    }

    public String getDescricao() {
        return name;
    }

    public String getPeriodo() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getNome() {
        return name;
    }

    public long calcularDiferencaPeriodo(Date dateFim, Date dataIni) {
        long ret = 0;
        switch (this) {
            case YEARLY:
                ret = UtilDate.diferencaAno(dateFim, dataIni);
                break;
            case MONTHLY:
                ret = UtilDate.diferencaMes(dateFim, dataIni);
                break;
            case DAILY:
                ret = UtilDate.diferencaDia(dateFim, dataIni);
                break;
        }
        return ret;
    }
}

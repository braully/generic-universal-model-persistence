package com.github.braully.domain;

import com.github.braully.util.UtilDate;
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

package com.github.braully.domain;

import com.github.braully.util.UtilDate;
import java.util.Date;

/**
 *
 * @author braully
 */
public enum TypePeriodInterest {
//TODO: Translate and unify and Merge in TypePeriod
    DIARIO("Dia", "por Dia"), MENSAL("Mês", "por Mês"), ANUAL("Ano", "por Ano");

    protected final String name;
    protected final String label;

    TypePeriodInterest(String nome, String label) {
        this.label = label;
        this.name = nome;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getDescricao() {
        return label;
    }

    public String getNome() {
        return name;
    }

    public long calcularDiferencaPeriodo(Date dateFim, Date dataIni) {
        long ret = 0;
        switch (this) {
            case ANUAL:
                ret = UtilDate.diferencaAno(dateFim, dataIni);
                break;
            case MENSAL:
                ret = UtilDate.diferencaMes(dateFim, dataIni);
                break;
            case DIARIO:
                ret = UtilDate.diferencaDia(dateFim, dataIni);
                break;
        }
        return ret;
    }
}

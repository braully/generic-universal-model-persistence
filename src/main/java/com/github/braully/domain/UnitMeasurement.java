package com.github.braully.domain;

/**
 *
 * @author braully
 */
public enum UnitMeasurement {

    UNITY("Unity"), KG("Kg"), MT("Meter"), HR("Hour");

    UnitMeasurement(String descricao) {
        this.descricao = descricao;
    }

    protected final String descricao;

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}

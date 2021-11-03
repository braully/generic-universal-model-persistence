package com.github.braully.domain;

/**
 *
 * @author braully
 */
public enum UnitMeasurement {

    UNITY("Unity"),
    HR("Hour"),
    KG("Kg"), MT("Meter"),
    LB, POL;

    private UnitMeasurement() {
        this.descricao = name();
    }

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

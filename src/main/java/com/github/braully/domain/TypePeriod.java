package com.github.braully.domain;

public enum TypePeriod {
    HORA("Hora", "por Hora"), DIARIO("Diário", "por Dia"), MENSAL("Mensal", "por Mês"),
    ANUAL("Anual", "por Ano"), SEMESTRAL("Semestral", "por Semestre"),
    TRIMESTRAL("Trimestral", "por Trimestre"), BIMESTRAL("Trimestral", "por Bimestre");

    String descricao;
    String periodo;

    private TypePeriod(String descricao, String periodo) {
        this.descricao = descricao;
        this.periodo = periodo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPeriodo() {
        return periodo;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}

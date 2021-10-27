package com.github.braully.domain;

/**
 *
 * @author braully
 */
public enum TransactionSituation {
    PREVISTO("Previsto"), EXECUTADO("Executado"), VENCIDO("Vencido"), PAGO("Pago"), RECEBIDO("Recebido");
    String descricao;

    private TransactionSituation(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

}

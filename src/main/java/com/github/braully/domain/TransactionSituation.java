package com.github.braully.domain;

/**
 *
 * @author braully
 */
public enum TransactionSituation {
    PENDING, EXPIRED, 
    EXECUTED, CANCELED,
    PAIDOUT("Paid Out"), RECEIVED;
    String descricao;

    TransactionSituation() {
        this.descricao = name();
    }

    TransactionSituation(String descricao) {
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

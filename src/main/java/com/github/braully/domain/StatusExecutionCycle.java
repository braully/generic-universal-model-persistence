package com.github.braully.domain;

/**
 *
 * @author braullyrocha
 */
public enum StatusExecutionCycle {

    READY, RUNNING,
    DONE, BLOCKED,
    CANCELED;

    String label;

    StatusExecutionCycle() {
        this.label = name();
    }

    StatusExecutionCycle(String label) {
        this.label = label;
    }

    public String getDescricao() {
        return name();
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}

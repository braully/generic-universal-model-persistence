/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.domain;

/**
 *
 * @author braully
 */
public interface INameComparable extends Comparable<INameComparable> {

    public abstract String getName();

    default public int compareTo(INameComparable o) {
        try {
            return getName().compareToIgnoreCase(o.getName());
        } catch (Exception e) {
            return 0;
        }
    }
}

package com.github.braully.constant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Extra attribute list, for generic information
 *
 * @author braully
 */
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Attrs {

    /**
     * Array of extra attribuites
     *
     * @return
     */
    public Attr[] value();
}

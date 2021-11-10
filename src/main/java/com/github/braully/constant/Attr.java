package com.github.braully.constant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Individual attribute
 *
 * @author braully
 */
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Attr {

    /**
     * array of string {name, val}
     *
     * @return
     */
    public String[] value() default {"", ""};

    /**
     * Name of attribute
     *
     * @return
     */
    public String name() default "";

    /**
     * Value of atribute
     *
     * @return
     */
    public String val() default "";

}

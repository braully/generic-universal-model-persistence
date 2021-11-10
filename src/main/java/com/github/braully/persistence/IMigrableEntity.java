package com.github.braully.persistence;

import com.github.braully.persistence.IEntity;

/**
 *
 * @author braully
 */
public interface IMigrableEntity extends IEntity {

    String getUniqueCode();

    void setUniqueCode(String uniqueCode);

}

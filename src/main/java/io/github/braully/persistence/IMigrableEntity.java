package io.github.braully.persistence;

import io.github.braully.persistence.IEntity;

/**
 *
 * @author braully
 */
public interface IMigrableEntity extends IEntity {

    String getUniqueCode();

    void setUniqueCode(String uniqueCode);

}

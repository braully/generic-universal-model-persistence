package com.github.braully.domain;

import com.github.braully.persistence.IEntity;

/**
 *
 * @author braully
 */
public interface ISystemEntity extends IEntity {

    public Boolean getSystemLock();
}

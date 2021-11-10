package io.github.braully.persistence;

import io.github.braully.persistence.IEntity;
import java.util.Date;

/**
 *
 * @author braully
 */
public interface IAuditableEntity extends IEntity {

    void setLastModified(Date lastModified);

    void setUserIdModified(Long userIdModified);

}
